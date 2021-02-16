package com.example.wannab.makeup.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wannab.R
import com.example.wannab.base.BaseActivity
import com.example.wannab.databinding.ActivityServerMakeUpBinding
import com.example.wannab.makeup.fragment.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerMakeUpActivity : BaseActivity<ActivityServerMakeUpBinding>(R.layout.activity_server_make_up) {
    private val model by lazy { ViewModelProvider(this).get(ServerMakeUpViewModel::class.java) }
    private var selectedImage: Uri? = null

    companion object {
        const val SELECT_IMAGE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        with(binding) {
            viewPager.adapter = object : FragmentStateAdapter(this@ServerMakeUpActivity) {
                override fun getItemCount(): Int {
                    return model.fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return model.fragments[position]
                }
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(model.fragments[position].tabTitle)
            }.attach()

            nestedScrollView.isFillViewport = true
        }

        model.selectedImage.observe(this, Observer {
            if (it != selectedImage) {
                with(binding.selectedImage) {
                    setImageURI(it)
                    visibility = View.VISIBLE
                }

                selectedImage = it
            }
        })

        if (!getSharedPreferences("s", Context.MODE_PRIVATE).contains("ServerMakeUpActivity")) {
            startActivity(Intent(this, ServerMakeUpPractice::class.java))
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when(fragment) {
            is RequestMakeUpFragment -> {
                fragment.bind(model.selectedImage.value, model.id)
            }
            is BookmarkMakeUpFragment -> {
                fragment.update()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SELECT_IMAGE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "We Need Permission", Toast.LENGTH_SHORT).show()
                        return
                    }

                    onSelectImage(null)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            model.selectedImage.value = data?.data
            model.id = System.currentTimeMillis().toString()

            for (fragment in model.fragments) {
                if (fragment.isAdded && fragment is RequestMakeUpFragment) {
                    fragment.bind(data?.data, model.id)
                }
            }
        }
    }

    fun onSelectImage(view: View?) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_IMAGE)
    }

    fun updateBookmark() {
        for (fragment in model.fragments) {
            if (fragment is BookmarkMakeUpFragment) {
                if (fragment.isAdded) {
                    fragment.update()
                }

                break
            }
        }
    }

    class ServerMakeUpViewModel : ViewModel() {
        val selectedImage by lazy { MutableLiveData<Uri?>(null) }
        val fragments = arrayOf(
            BookmarkMakeUpFragment(),
            DateFragment(), FreshmanFragment(), GguFragment(),
            OfficeFragment(), ProfileFragment(), GuestFragment()
        )

        var id = "0"
    }
}