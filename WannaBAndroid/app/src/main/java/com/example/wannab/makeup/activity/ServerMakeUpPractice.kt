package com.example.wannab.makeup.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.example.wannab.R
import com.example.wannab.base.BaseActivity
import com.example.wannab.databinding.ActivityServerMakeUpPracticeBinding

class ServerMakeUpPractice : BaseActivity<ActivityServerMakeUpPracticeBinding>(R.layout.activity_server_make_up_practice) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editor = getSharedPreferences("s", Context.MODE_PRIVATE).edit()

        editor.putBoolean("ServerMakeUpActivity", true)
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