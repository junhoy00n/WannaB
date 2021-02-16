package com.example.wannab

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.wannab.base.BaseActivity
import com.example.wannab.base.BaseHolder
import com.example.wannab.databinding.ActivityMainBinding
import com.example.wannab.databinding.HolderMainViewpagerBinding
import com.example.wannab.makeup.activity.LocalMakeUpActivity
import com.example.wannab.makeup.activity.ServerMakeUpActivity
import com.example.wannab.singleton.RoomDB
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val model by lazy { ViewModelProvider(this).get(MainActivityModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!getSharedPreferences("s", Context.MODE_PRIVATE).getBoolean("MainActivity", false)) {
            startActivity(Intent(this, MainPracticeActivity::class.java))
        }


        with(binding) {
            activity = this@MainActivity
            viewPager.adapter = model.adapter
        }
    }



    fun onStartButton(view: View?) {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.app_name)

            val items = arrayOf("Local", "Server")
            setItems(items) { _, which ->
                when(items[which]) {
                    "Local" -> {
                        startActivity(
                            Intent(this@MainActivity,
                                LocalMakeUpActivity::class.java)
                        )
                    }
                    "Server" -> {
                        startActivity(
                            Intent(this@MainActivity,
                                ServerMakeUpActivity::class.java)
                        )
                    }
                }
            }
        }.show()
    }

    class MainActivityModel : ViewModel() {
        val adapter by lazy { MainViewPagerAdapter() }
    }

    class ViewPagerHolder(binding: HolderMainViewpagerBinding) : BaseHolder<HolderMainViewpagerBinding, Int>(binding) {
        init {
            binding.holder = this
        }
    }

    class MainViewPagerAdapter : RecyclerView.Adapter<ViewPagerHolder>() {
        var currentItem = 0
        var timer: Timer? = null

        private val list = arrayOf(
            R.drawable.iu, R.drawable.joy,
            R.drawable.sunmi, R.drawable.yejin
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
            return ViewPagerHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemViewType(position: Int): Int {
            return R.layout.holder_main_viewpager
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager.startAutoScroll(0L, 2300L)
    }
    override fun onPause() {
        super.onPause()
        binding.viewPager.stopAutoScroll()
    }
    private fun ViewPager2.startAutoScroll(delay: Long, period: Long) {
        adapter?.let { adapter ->
            if (adapter is MainViewPagerAdapter) {
                currentItem = adapter.currentItem
                adapter.timer = Timer().apply {
                    schedule(object : TimerTask() {
                        override fun run() {
                            adapter.currentItem++
                            adapter.currentItem %= adapter.itemCount
                            setCurrentItem(adapter.currentItem, true)
                        }
                    }, delay, period)
                }
            }
        }
    }
    private fun ViewPager2.stopAutoScroll() {
        adapter?.let {
            if (it is MainViewPagerAdapter) {
                it.timer?.cancel()
                it.timer = null
            }
        }
    }

}