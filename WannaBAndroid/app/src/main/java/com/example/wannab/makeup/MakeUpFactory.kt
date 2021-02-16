package com.example.wannab.makeup

import android.util.Base64
import org.json.JSONObject

object MakeUpFactory {
    fun createFromJson(jsonObject: JSONObject): MakeUp {
        val id = jsonObject["id"] as Int
        val code = jsonObject["code"] as String

        return MakeUp(id.toLong(), Base64.decode(code, Base64.DEFAULT))
    }
}