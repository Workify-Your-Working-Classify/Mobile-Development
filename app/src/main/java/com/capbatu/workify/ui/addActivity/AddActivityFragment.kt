package com.capbatu.workify.ui.addActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capbatu.workify.R
import com.capbatu.workify.Utils.getInputValue
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.remote.request.ActivityRequest
import com.capbatu.workify.databinding.FragmentAddActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddActivityFragment : Fragment() {
    private lateinit var callback: SwipeViewPagerAddActivity

    private lateinit var binding: FragmentAddActivityBinding
    private val viewModel: AddActivityViewModel by viewModels()

    private var user: AuthModel? = null
    private var postJob: Job = Job()

    interface SwipeViewPagerAddActivity {
        fun swipeToPage(page: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        postJob.cancel()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getAuthSession().observe(viewLifecycleOwner) { session ->
                user = session

                binding.btnAddActivity.setOnClickListener { handlePost() }
            }
        }
    }

    private fun handlePost() {
        showLoading(true)
        lifecycleScope.launchWhenResumed {
            if (postJob.isActive) postJob.cancel()

            postJob =
                launch {
                    user?.let {
                        getFormValue()?.let { it1 ->
                            viewModel.postActivity(it.userId, it1).collect { result ->
                                result.onSuccess { _ ->
                                    showToast(
                                        requireContext(),
                                        getString(R.string.toast_activities_have_been_added),
                                    )
                                    callback.swipeToPage(1)
                                }
                                result.onFailure { response ->
                                    showLoading(false)
                                    showToast(
                                        requireContext(),
                                        response.message.toString(),
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun getFormValue(): ActivityRequest? {
        var activity: ActivityRequest? = null
        binding.apply {
            val namaKegiatan = etActivityName.getInputValue()
            val pelaksana = etAssignor.getInputValue()
            val tanggal = etDate.getInputValue()
            val jam = etTime.getInputValue()
            val lama = etEstimatedTime.getInputValue()
            val deskripsi = etDescription.getInputValue()
            val kepentinganKegiatan = sldrActivityImportance.value.toInt()
            val kepentinganPelaksana = sldrAssignorImportance.value.toInt()

            if (namaKegiatan.isNotEmpty() ||
                pelaksana.isNotEmpty() ||
                tanggal.isNotEmpty() ||
                jam.isNotEmpty() ||
                lama.isNotEmpty() ||
                deskripsi.isNotEmpty()
            ) {
                activity =
                    ActivityRequest(
                        namaKegiatan = namaKegiatan,
                        pelaksana = pelaksana,
                        tanggal = tanggal,
                        jam = jam,
                        lama = lama,
                        Deskripsi = deskripsi,
                        kepentinganKegiatan = kepentinganKegiatan,
                        kepentinganPelaksana = kepentinganPelaksana,
                    )
            } else {
                showToast(requireContext(), getString(R.string.toast_complete_the_form_first))
            }
        }
        return activity
    }

    private fun showLoading(state: Boolean) {
        binding.pbCircular.visibility = if (state) View.VISIBLE else View.GONE
    }
}
