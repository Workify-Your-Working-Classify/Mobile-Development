package com.capbatu.workify.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.capbatu.workify.Utils.getInputValue
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.databinding.ActivityRegisterBinding
import com.capbatu.workify.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels()
    private var registerJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        registerJob.cancel()
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

    private fun setupAction() {
        binding.apply {
            etName.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                setButtonEnabled(
                    isFormFilled(),
                )
            })

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

            btnRegister.setOnClickListener {
                handleRegister()
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun isFormFilled(): Boolean {
        return binding.etEmail.getInputValue().isNotEmpty() &&
            binding.etPassword.getInputValue().isNotEmpty() &&
            binding.etName.getInputValue().isNotEmpty()
    }

    private fun handleRegister() {
        binding.apply {
            val name: String = etName.getInputValue()
            val email: String = etEmail.getInputValue()
            val password: String = etPassword.getInputValue()

            showLoading(true)

            lifecycleScope.launchWhenResumed {
                if (registerJob.isActive) registerJob.cancel()

                registerJob =
                    launch {
                        viewModel.register(displayName = name, email = email, password = password)
                            .collect { result ->
                                result.onSuccess { _ ->
                                    Intent(
                                        this@RegisterActivity,
                                        LoginActivity::class.java,
                                    ).also { intent ->
                                        startActivity(intent)
                                    }
                                }
                                result.onFailure { response ->
                                    showToast(this@RegisterActivity, response.message.toString())
                                    showLoading(
                                        false,
                                    )
                                }
                            }
                    }
            }
        }
    }

    private fun setButtonEnabled(state: Boolean) {
        binding.btnRegister.isEnabled = state
    }

    private fun setFormDisabled(state: Boolean) {
        binding.apply {
            etName.isEnabled = !state
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
