package com.capbatu.workify.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capbatu.workify.R
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.databinding.FragmentSettingBinding
import com.capbatu.workify.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by viewModels()

    private var logoutJob: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnChangeLanguage.setOnClickListener {
                Intent(Settings.ACTION_LOCALE_SETTINGS).also { intent ->
                    startActivity(intent)
                }
            }

            btnLogout.setOnClickListener {
                handleLogout()
            }
        }
    }

    private fun handleLogout() {
        lifecycleScope.launchWhenResumed {
            if (logoutJob.isActive) logoutJob.cancel()

            logoutJob =
                launch {
                    viewModel.logout().collect { result ->
                        result.onSuccess { _ ->
                            Intent(requireContext(), WelcomeActivity::class.java).also { intent ->
                                startActivity(intent)
                            }
                            showToast(requireContext(), getString(R.string.toast_logout_succesfull))
                        }
                        result.onFailure { response ->
                            showToast(requireContext(), response.message.toString())
                        }
                    }
                }
        }
    }
}
