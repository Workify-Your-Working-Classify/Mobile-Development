package com.capbatu.workify.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private val binding: ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
    }

    private fun playAnimation() {
        binding.apply {
            val lottie = ObjectAnimator.ofFloat(lottieMeditation, View.ALPHA, 1f).setDuration(3000)
            val tvTitle = ObjectAnimator.ofFloat(tvAppTitle, View.ALPHA, 1f).setDuration(2000)
            val tvDesc = ObjectAnimator.ofFloat(tvAppDesc, View.ALPHA, 1f).setDuration(2000)
            val btnLogin = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(1000)
            val btnRegister = ObjectAnimator.ofFloat(btnRegister, View.ALPHA, 1f).setDuration(1000)

            AnimatorSet().apply {
                playSequentially(lottie, tvTitle, tvDesc, btnLogin, btnRegister)
                start()
            }
        }
    }
}
