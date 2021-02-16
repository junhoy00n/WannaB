package com.example.wannab.makeup

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wannab.R
import com.example.wannab.base.BaseHolder
import com.example.wannab.databinding.HolderCosmeticBinding
import kotlin.properties.Delegates

class CosmeticAdapter : RecyclerView.Adapter<CosmeticAdapter.CosmeticHolder>() {
    var list by Delegates.observable(listOf<Cosmetic>()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosmeticHolder {
        return CosmeticHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CosmeticHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_cosmetic
    }

    class CosmeticHolder(binding: HolderCosmeticBinding) : BaseHolder<HolderCosmeticBinding, Cosmetic>(binding) {
        init {
            binding.holder = this
            binding.root.setOnClickListener {
                binding.root.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
            }
        }
    }
}