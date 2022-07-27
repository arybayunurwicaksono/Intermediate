package com.dguitarclassic.intermediate.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.dguitarclassic.intermediate.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        determineUserDirection()
    }

    private fun determineUserDirection() {
        val time: Long = 2000

        Handler().postDelayed({
            Intent(this@SplashActivity, AuthActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }, time)
    }
}