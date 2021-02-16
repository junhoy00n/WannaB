package com.example.wannab

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.wannab.base.BaseActivity
import com.example.wannab.databinding.ActivityMainPracticeBinding
import java.lang.Exception

class MainPracticeActivity : BaseActivity<ActivityMainPracticeBinding>(R.layout.activity_main_practice) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


            val editor = getSharedPreferences("s", Context.MODE_PRIVATE).edit()
            editor.putBoolean("MainActivity", true)
            editor.apply()
            editor.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}