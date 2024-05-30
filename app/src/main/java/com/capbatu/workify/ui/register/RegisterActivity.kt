package com.capbatu.workify.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityRegisterBinding
import com.capbatu.workify.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
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
                Intent(this@RegisterActivity, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieConfuse, View.ALPHA, 1f).setDuration(3000)
            val llForm = ObjectAnimator.ofFloat(llForm, View.ALPHA, 1f).setDuration(2000)
            val btnRegister = ObjectAnimator.ofFloat(btnRegister, View.ALPHA, 1f).setDuration(2000)

            AnimatorSet().apply {
                playSequentially(lottie, llForm, btnRegister)
                start()
            }
        }
    }
}
