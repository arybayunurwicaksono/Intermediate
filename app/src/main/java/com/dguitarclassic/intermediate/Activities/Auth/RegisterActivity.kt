package com.dguitarclassic.intermediate.Activities.Auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dguitarclassic.intermediate.R
import com.dguitarclassic.intermediate.Model.RegisterViewModel
import com.dguitarclassic.intermediate.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel by viewModels<RegisterViewModel>()

    private fun startAnimating() {

        val animTv1 = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(900)
        val animTv2 = ObjectAnimator.ofFloat(binding.textView1, View.ALPHA, 1f).setDuration(1000)
        val animTv3 = ObjectAnimator.ofFloat(binding.textView5, View.ALPHA, 1f).setDuration(700)
        val animTv4 = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(700)
        val animImg1 = ObjectAnimator.ofFloat(binding.imageView3, View.ALPHA, 1f).setDuration(500)
        val animImg2 = ObjectAnimator.ofFloat(binding.imageView4, View.ALPHA, 1f).setDuration(500)
        val animInput1 = ObjectAnimator.ofFloat(binding.inputFullname, View.ALPHA, 1f).setDuration(500)
        val animInput2 = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(500)
        val animInput3 = ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val animBtn2 = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val assembling = AnimatorSet().apply {
            playTogether(animTv1, animTv2, animTv3, animTv4)
        }
        val form = AnimatorSet().apply {
            playTogether(animInput1, animInput2, animInput3)
        }

        AnimatorSet().apply {
            playSequentially(assembling, animImg1, animImg2, form, animBtn2)
            start()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startAnimating()
        with(binding){
            btnRegister.setOnClickListener {

                registerViewModel.registerAccount(
                    inputFullname.text.toString(),
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                )
                registerViewModel.registerResponse.observe(this@RegisterActivity){response->

                    if (response.error){
                        Snackbar.make(
                            binding.root,
                            "failed, ${response.message}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }else{
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}