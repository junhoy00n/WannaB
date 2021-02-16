package com.example.wannab.singleton

import android.content.Context
import androidx.room.*
import com.example.wannab.base.BaseDao
import com.example.wannab.makeup.MakeUp
import java.util.*

@Database(entities = [MakeUp::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    companion object {
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, RoomDB::class.java, "Room").build().also {
                    instance = it
                }
            }
        }
    }

    abstract fun makeup(): MakeUpDao

    @Dao
    interface MakeUpDao : BaseDao<MakeUp> {
        @Query("select * from makeup")
        fun findAll(): List<MakeUp>

        @Query("select * from makeup where id = :id")
        fun findById(id: Long): Optional<MakeUp>
    }
}