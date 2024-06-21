package com.capbatu.workify.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.capbatu.workify.R
import com.capbatu.workify.Utils.getInputValue
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.databinding.ActivityLoginBinding
import com.capbatu.workify.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()
    private var loginJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginJob.cancel()
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

    private fun setupAction() {
        binding.apply {
            etEmail.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                setButtonEnabled(
                    isFormFilled(),
                )
            })

            etPassword.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                setButtonEnabled(
                    isFormFilled(),
                )
            })

            btnLogin.setOnClickListener {
                handleLogin()
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun isFormFilled(): Boolean {
        return binding.etEmail.getInputValue().isNotEmpty() &&
            binding.etPassword.getInputValue().isNotEmpty()
    }

    private fun handleLogin() {
        binding.apply {
            val email: String = etEmail.getInputValue()
            val password: String = etPassword.getInputValue()

            showLoading(true)

            lifecycleScope.launchWhenResumed {
                if (loginJob.isActive) loginJob.cancel()

                loginJob =
                    launch {
                        viewModel.login(email, password)
                            .collect { result ->
                                result.onSuccess { response ->

                                    viewModel.saveAuthSession(
                                        userId = response.user.userId,
                                        displayName = response.user.displayName,
                                        email = response.user.email,
                                        token = response.token,
                                    )

                                    showToast(
                                        this@LoginActivity,
                                        getString(R.string.toast_login_successful),
                                    )
                                    Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java,
                                    ).also { intent ->
                                        startActivity(intent)
                                    }
                                }

                                result.onFailure { response ->
                                    showToast(this@LoginActivity, response.message.toString())
                                    showLoading(false)
                                }
                            }
                    }
            }
        }
    }

    private fun setButtonEnabled(state: Boolean) {
        binding.btnLogin.isEnabled = state
    }

    private fun setFormDisabled(state: Boolean) {
        binding.apply {
            etEmail.isEnabled = !state
            etPassword.isEnabled = !state
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbCircular.visibility = View.VISIBLE
            setButtonEnabled(false)
            setFormDisabled(true)
        } else {
            binding.pbCircular.visibility = View.GONE
            setButtonEnabled(true)
            setFormDisabled(false)
        }
    }
}
