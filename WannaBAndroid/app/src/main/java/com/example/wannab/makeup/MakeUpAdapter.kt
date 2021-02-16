package com.example.wannab.makeup

import android.content.Intent
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wannab.R
import com.example.wannab.base.BaseHolder
import com.example.wannab.databinding.HolderMakeupBinding
import com.example.wannab.makeup.activity.MakeUpInformationActivity
import com.example.wannab.makeup.activity.ServerMakeUpActivity
import com.example.wannab.makeup.fragment.BookmarkMakeUpFragment
import com.example.wannab.makeup.fragment.MakeUpFragment
import com.example.wannab.singleton.RoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class MakeUpAdapter(private val fragment: MakeUpFragment) : RecyclerView.Adapter<MakeUpAdapter.MakeUpHolder>() {
    var list by Delegates.observable(listOf<MakeUp>()) { _, _, _ ->
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeUpHolder {
        return MakeUpHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false), fragment)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MakeUpHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_makeup
    }

    class MakeUpHolder(binding: HolderMakeupBinding, fragment: MakeUpFragment) : BaseHolder<HolderMakeupBinding, MakeUp>(binding) {
        init {
            binding.holder = this

            itemView.setOnClickListener {
                fragment.context?.let {
                    it.startActivity(Intent(it, MakeUpInformationActivity::class.java).putExtra("makeup", item))
                }
            }

            itemView.setOnCreateContextMenuListener { menu, _, _ ->
                if (fragment is BookmarkMakeUpFragment) {
                    menu.add(0, 0, 0, "Delete").setOnMenuItemClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            RoomDB.getInstance(itemView.context).makeup().delete(item)
                            val activity = fragment.activity
                            if (activity is ServerMakeUpActivity) {
                                activity.updateBookmark()
                            }
                        }
                        true
                    }
                } else {
                    menu.add(0, 0, 0, "Bookmark").setOnMenuItemClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            RoomDB.getInstance(itemView.context).makeup().insert(item)
                            val activity = fragment.activity
                            if (activity is ServerMakeUpActivity) {
                                activity.updateBookmark()
                            }
                        }
                        true
                    }
                }
            }
        }
    }
}