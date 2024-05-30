package com.capbatu.workify.ui.reset

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityResetBinding
import com.capbatu.workify.ui.login.LoginActivity

class ResetActivity : AppCompatActivity() {
    private val binding: ActivityResetBinding by lazy {
        ActivityResetBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnReset.setOnClickListener {
                Intent(this@ResetActivity, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieRelax, View.ALPHA, 1f).setDuration(3000)
            val llForm = ObjectAnimator.ofFloat(llForm, View.ALPHA, 1f).setDuration(2000)
            val btnReset = ObjectAnimator.ofFloat(btnReset, View.ALPHA, 1f).setDuration(2000)

            AnimatorSet().apply {
                playSequentially(lottie, llForm, btnReset)
                start()
            }
        }
    }
}
