package com.dguitarclassic.intermediate.Activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dguitarclassic.intermediate.Activities.Auth.LoginActivity
import com.dguitarclassic.intermediate.Activities.Auth.RegisterActivity
import com.dguitarclassic.intermediate.Model.MainViewModel
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.ViewModelFactory
import com.dguitarclassic.intermediate.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var mainViewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPref.getInstance(dataStore))
        )[MainViewModel::class.java]

        startAnimating()

        mainViewModel.getUser().observe(this){
            if(it.token.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        with(binding){

            btnLogin.setOnClickListener {
                startActivity(Intent(this@AuthActivity, LoginActivity::class.java))
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@AuthActivity, RegisterActivity::class.java))
            }
        }

    }

    private fun startAnimating() {

        val animTv1 = ObjectAnimator.ofFloat(binding.welcome, View.ALPHA, 1f).setDuration(1000)
        val animBtn1 = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val animBtn2 = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(animTv1, animBtn1, animBtn2)
            start()
        }
    }
}