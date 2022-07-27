package com.dguitarclassic.intermediate.Activities.Auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dguitarclassic.intermediate.*
import com.dguitarclassic.intermediate.Activities.MainActivity
import com.dguitarclassic.intermediate.Model.LoginViewModel
import com.dguitarclassic.intermediate.Model.User
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel : LoginViewModel

    private fun startAnimating() {

        val animTv1 = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(900)
        val animTv2 = ObjectAnimator.ofFloat(binding.textView1, View.ALPHA, 1f).setDuration(1000)
        val animTv3 = ObjectAnimator.ofFloat(binding.textView5, View.ALPHA, 1f).setDuration(700)
        val animTv4 = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(700)
        val animImg1 = ObjectAnimator.ofFloat(binding.imageView3, View.ALPHA, 1f).setDuration(500)
        val animImg2 = ObjectAnimator.ofFloat(binding.imageView4, View.ALPHA, 1f).setDuration(500)
        val animInput1 = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(500)
        val animInput2 = ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val animBtn1 = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)

        val assembling = AnimatorSet().apply {
            playTogether(animTv1, animTv2, animTv3, animTv4)
        }
        val form = AnimatorSet().apply {
            playTogether(animInput1, animInput2, animBtn1)
        }

        AnimatorSet().apply {
            playSequentially(assembling, animImg1, animImg2, form)
            start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPref.getInstance(dataStore))
        )[LoginViewModel::class.java]

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startAnimating()

            with(binding){
                binding.btnLogin.setOnClickListener {
                    loginViewModel.LoginAccounts(
                        inputEmail.text.toString(),
                        inputPassword.text.toString()
                    )
                    loginViewModel.login()
                    loginViewModel.loginResponse.observe(this@LoginActivity){ login->

                        saveSession(
                            User(
                                "${login.loginResult?.name}",
                                "Bearer ${login.loginResult?.token}",
                                true
                            )
                        )

                        val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(loginIntent)

                    }
                }

        }

    }
    fun saveSession(user: User){
        loginViewModel.saveUser(user)
    }
}