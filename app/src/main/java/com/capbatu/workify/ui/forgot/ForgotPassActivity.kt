package com.capbatu.workify.ui.forgot

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityForgotPassBinding
import com.capbatu.workify.ui.reset.ResetActivity

class ForgotPassActivity : AppCompatActivity() {
    private val binding: ActivityForgotPassBinding by lazy {
        ActivityForgotPassBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnForgot.setOnClickListener {
                Intent(this@ForgotPassActivity, ResetActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieAnnoy, View.ALPHA, 1f).setDuration(3000)
            val llForm = ObjectAnimator.ofFloat(llForm, View.ALPHA, 1f).setDuration(2000)
            val btnForgot = ObjectAnimator.ofFloat(btnForgot, View.ALPHA, 1f).setDuration(2000)

            AnimatorSet().apply {
                playSequentially(lottie, llForm, btnForgot)
                start()
            }
        }
    }
}
