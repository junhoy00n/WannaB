package com.example.wannab.singleton

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.Volley

class VolleyHttp private constructor(context: Context) {
    private val queue by lazy { Volley.newRequestQueue(context) }

    companion object {
        private const val PROTOCOL = "http"
        private const val IP = "192.9.45.191"
//        private const val IP = "ec2-3-35-133-121.ap-northeast-2.compute.amazonaws.com"
        private const val PORT = "8000"

        const val URL_REQUEST_MAKEUP_IMAGE = "$PROTOCOL://$IP:$PORT/makeup/"
        const val URL_REQUEST_MAKEUP_INFORMATION = "$PROTOCOL://$IP:$PORT/information/"
        const val URL_REQUEST_MAKEUP_SUM = "$PROTOCOL://$IP:$PORT/sum/"

        private var instance: VolleyHttp? = null

        fun getInstance(context: Context): VolleyHttp {
            return instance ?: synchronized(this) {
                VolleyHttp(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    fun <T> request(request: Request<T>) {
        queue.add(request)
    }
}