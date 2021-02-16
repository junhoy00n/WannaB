package com.example.wannab.makeup

import android.util.Base64
import org.json.JSONObject

object CosmeticFactory {
    fun createFromJson(jsonObject: JSONObject): Cosmetic {
        val link = jsonObject["link"] as String
        val code = jsonObject["code"] as String

        return Cosmetic(link, Base64.decode(code, Base64.DEFAULT))
    }
}