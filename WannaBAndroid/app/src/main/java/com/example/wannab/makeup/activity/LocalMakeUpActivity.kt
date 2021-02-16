package com.example.wannab.makeup.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaDataSource
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.wannab.R
import com.example.wannab.base.BaseActivity
import com.example.wannab.databinding.ActivityLocalMakeUpBinding
import com.example.wannab.singleton.FileManager
import com.example.wannab.singleton.VolleyHttp
import kotlinx.android.synthetic.main.activity_local_make_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileInputStream

class LocalMakeUpActivity : BaseActivity<ActivityLocalMakeUpBinding>(R.layout.activity_local_make_up) {
    private val model by lazy { ViewModelProvider(this).get(LocalMakeUpViewModel::class.java) }

    companion object {
        private const val YOUR_IMAGE = 1
        private const val MAKEUP_IMAGE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this

        model.yourImage.observe(this, Observer {
            if (it != null) {
                binding.yourImageView.setImageURI(it)
                binding.yourImageView.visibility = View.VISIBLE
                requestResultImage()
            }
        })

        model.makeupImage.observe(this, Observer {
            if (it != null) {
                binding.makeupImageView.setImageURI(it)
                binding.makeupImageView.visibility = View.VISIBLE
                requestResultImage()
            }
        })

        model.resultImage.observe(this, Observer {
            if (it != null) {
                binding.resultImageView.setImageBitmap(it)
                binding.resultImageView.visibility = View.VISIBLE
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                YOUR_IMAGE -> {
                    model.yourImage.value = data?.data
                }

                MAKEUP_IMAGE -> {
                    model.makeupImage.value = data?.data
                }
            }
        }
    }

    private fun requestResultImage() {
        if (model.yourImage.value == null || model.makeupImage.value == null) {
            return
        }
        val dialog = ProgressDialog.show(this, "WannaB", "Wait Please")

        val request = object : StringRequest(
            Method.POST, VolleyHttp.URL_REQUEST_MAKEUP_SUM,
            Response.Listener {
                val byteArray = Base64.decode(it, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                CoroutineScope(Dispatchers.Main).launch {
                    model.resultImage.value = bitmap
                    dialog.cancel()
                }
            },
            Response.ErrorListener {
                dialog.cancel()
                Log.d("PASS", it.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf<String, String>().apply {
                    val yourImage = FileManager.getInstance(this@LocalMakeUpActivity).uriToFile(model.yourImage.value!!, "r")
                    val makeupImage = FileManager.getInstance(this@LocalMakeUpActivity).uriToFile(model.makeupImage.value!!, "r")

                    put("no_makeup", Base64.encodeToString(FileInputStream(yourImage).readBytes(), Base64.DEFAULT))
                    put("makeup", Base64.encodeToString(FileInputStream(makeupImage).readBytes(), Base64.DEFAULT))
                }
            }
        }

        request.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        VolleyHttp.getInstance(this).request(request)
    }

    fun onSelectImage(view: View?) {
        when (view?.id) {
            R.id.yourImageView, R.id.yourImageTextView -> {
                startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), YOUR_IMAGE)
            }

            R.id.makeupImageView, R.id.makeupImageTextView -> {
                startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), MAKEUP_IMAGE)
            }
        }
    }

    class LocalMakeUpViewModel : ViewModel() {
        val yourImage by lazy { MutableLiveData<Uri?>(null) }
        val makeupImage by lazy { MutableLiveData<Uri?>(null) }
        val resultImage by lazy { MutableLiveData<Bitmap?>(null) }
    }
}