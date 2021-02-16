package com.example.wannab.makeup.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.wannab.R
import com.example.wannab.base.BaseActivity
import com.example.wannab.databinding.ActivityMakeUpInformationBinding
import com.example.wannab.makeup.Cosmetic
import com.example.wannab.makeup.CosmeticAdapter
import com.example.wannab.makeup.CosmeticFactory
import com.example.wannab.makeup.MakeUp
import com.example.wannab.singleton.VolleyHttp
import org.json.JSONArray
import org.json.JSONObject

class MakeUpInformationActivity : BaseActivity<ActivityMakeUpInformationBinding>(R.layout.activity_make_up_information) {
    val model by lazy { ViewModelProvider(this).get(MakeUpInformationViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        if (model.makeup == null) {
            model.makeup = intent.getSerializableExtra("makeup") as MakeUp
            val dialog = ProgressDialog.show(this, "WannaB", "Wait Please")
            val request = object : StringRequest(
                Method.POST, VolleyHttp.URL_REQUEST_MAKEUP_INFORMATION,
                Response.Listener {
                    val jsonObject = JSONObject(it)
                    val jsonArray = jsonObject.getJSONArray("cosmetics")
                    val list = ArrayList<Cosmetic>()
                    for (i in 0 until jsonArray.length()) {
                        list.add(CosmeticFactory.createFromJson(jsonArray.getJSONObject(i)))
                    }

                    model.link = jsonObject.getString("link")
                    model.adapter = CosmeticAdapter()
                    model.adapter?.list = list

                    binding.recyclerView.adapter = model.adapter
                    dialog.cancel()
                },
                Response.ErrorListener {
                    dialog.cancel()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    return hashMapOf(
                        Pair("id", model.makeup?.id.toString())
                    )
                }
            }

            VolleyHttp.getInstance(this).request(request)
        } else {
            binding.recyclerView.adapter = model.adapter
        }
    }

    fun onClickMakeUpImage(view: View?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(model.link)))
    }

    class MakeUpInformationViewModel : ViewModel() {
        var makeup: MakeUp? = null
        var link: String? = null
        var adapter: CosmeticAdapter? = null
    }
}