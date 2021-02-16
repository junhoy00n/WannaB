package com.example.wannab.makeup.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wannab.R
import com.example.wannab.TabInterface
import com.example.wannab.base.BaseFragment
import com.example.wannab.databinding.FragmentMakeupBinding
import com.example.wannab.makeup.MakeUpAdapter
import com.example.wannab.makeup.activity.ServerMakeUpActivity

abstract class MakeUpFragment : BaseFragment<FragmentMakeupBinding>(R.layout.fragment_makeup), TabInterface {
    protected val makeUpModel by lazy { ViewModelProvider(this).get(MakeUpFragmentViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (makeUpModel.adapter == null) {
            makeUpModel.adapter = MakeUpAdapter(this)
        }

        binding.fragment = this
        with(binding.recyclerView) {
            adapter = makeUpModel.adapter
            layoutManager = GridLayoutManager(this@MakeUpFragment.context, 2)
        }

        return view
    }

    class MakeUpFragmentViewModel : ViewModel() {
        var adapter: MakeUpAdapter? = null
    }
}