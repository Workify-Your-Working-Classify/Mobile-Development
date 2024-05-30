package com.capbatu.workify.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityWelcomeBinding
import com.capbatu.workify.ui.login.LoginActivity
import com.capbatu.workify.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private val binding: ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                Intent(this@WelcomeActivity, RegisterActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }

            btnLogin.setOnClickListener {
                Intent(this@WelcomeActivity, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieMeditation, View.ALPHA, 1f).setDuration(3000)
            val tvTitle = ObjectAnimator.ofFloat(tvAppTitle, View.ALPHA, 1f).setDuration(2000)
            val tvDesc = ObjectAnimator.ofFloat(tvAppDesc, View.ALPHA, 1f).setDuration(2000)
            val llAction = ObjectAnimator.ofFloat(llAppAction, View.ALPHA, 1f).setDuration(1000)

            AnimatorSet().apply {
                playSequentially(lottie, tvTitle, tvDesc, llAction)
                start()
            }
        }
    }
}
