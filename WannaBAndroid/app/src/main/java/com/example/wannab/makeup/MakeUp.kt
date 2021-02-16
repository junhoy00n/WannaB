package com.example.wannab.makeup

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject
import java.io.Serializable

@Entity
data class MakeUp(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var code: ByteArray
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (other is MakeUp) {
            return id == other.id
        }

        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

