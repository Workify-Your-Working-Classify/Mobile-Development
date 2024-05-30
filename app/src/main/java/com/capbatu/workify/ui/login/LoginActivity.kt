package com.capbatu.workify.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityLoginBinding
import com.capbatu.workify.ui.forgot.ForgotPassActivity
import com.capbatu.workify.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }

            tvForgotPass.setOnClickListener {
                Intent(this@LoginActivity, ForgotPassActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieWorry, View.ALPHA, 1f).setDuration(3000)
            val llForm = ObjectAnimator.ofFloat(llForm, View.ALPHA, 1f).setDuration(2000)
            val btnLogin = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(2000)

            AnimatorSet().apply {
                playSequentially(lottie, llForm, btnLogin)
                start()
            }
        }
    }
}
