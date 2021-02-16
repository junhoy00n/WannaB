package com.example.wannab.makeup.fragment

import com.example.wannab.R
import com.example.wannab.makeup.activity.ServerMakeUpActivity
import com.example.wannab.singleton.RoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkMakeUpFragment : MakeUpFragment() {
    override val tabTitle: Int
        get() = R.string.bookmark

    fun update() {
        context?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val list = RoomDB.getInstance(it).makeup().findAll()
                CoroutineScope(Dispatchers.Main).launch {
                    makeUpModel.adapter?.list = list
                }
            }
        }
    }
}