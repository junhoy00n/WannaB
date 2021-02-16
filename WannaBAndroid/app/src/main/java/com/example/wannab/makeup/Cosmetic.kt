package com.example.wannab.makeup

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Cosmetic(
    var link: String,
    var code: ByteArray
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}