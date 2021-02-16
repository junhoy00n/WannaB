package com.example.wannab.makeup.fragment

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ContextMenu
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.wannab.makeup.MakeUp
import com.example.wannab.makeup.MakeUpAdapter
import com.example.wannab.makeup.MakeUpFactory
import com.example.wannab.makeup.activity.ServerMakeUpActivity
import com.example.wannab.singleton.FileManager
import com.example.wannab.singleton.RoomDB
import com.example.wannab.singleton.VolleyHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileInputStream
import java.lang.Exception

abstract class RequestMakeUpFragment : MakeUpFragment() {
    private val requestMakeUpModel by lazy { ViewModelProvider(this).get(RequestMakeUpViewModel::class.java) }
    private var selectedImage: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMakeUpModel.selectedImage.observe(this, Observer {
            if (it != selectedImage) {
                selectedImage = it
                it?.let { requestMakeUpResultImage(it) }
            }
        })
    }

    fun bind(image: Uri?, id: String) {
        if (requestMakeUpModel.selectedImage.value != image) {
            requestMakeUpModel.selectedImage.value = image
            requestMakeUpModel.id = id
        }
    }

    private fun requestMakeUpResultImage(uri: Uri) {
        if (context == null) {
            return
        }
        val dialog = ProgressDialog.show(context!!, "WannaB", "Wait Please")
        val request = object : StringRequest(
            Method.POST, VolleyHttp.URL_REQUEST_MAKEUP_IMAGE,
            Response.Listener {
                val list = arrayListOf<MakeUp>().apply {
                    val jsonArray = JSONArray(it)
                    for (i in 0 until jsonArray.length()) {
                        add(MakeUpFactory.createFromJson(jsonArray.getJSONObject(i)))
                    }
                }

                makeUpModel.adapter?.list = list
                dialog.cancel()
            },
            Response.ErrorListener {
                Log.d("PASS", it.toString())
                dialog.cancel()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    Pair("theme", getString(tabTitle)),
                    Pair("id", requestMakeUpModel.id)
                ).apply {
                    try {
                        val file = FileManager.getInstance(context!!).uriToFile(uri, "r")
                        put("code", Base64.encodeToString(FileInputStream(file).readBytes(), Base64.DEFAULT))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        request.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        VolleyHttp.getInstance(context!!).request(request)
    }

    class RequestMakeUpViewModel : ViewModel() {
        var id = "0"
        val selectedImage by lazy { MutableLiveData<Uri?>(null) }
    }
}