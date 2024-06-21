package com.capbatu.workify.ui.listActivity

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capbatu.workify.R
import com.capbatu.workify.Utils
import com.capbatu.workify.adapter.ListActivityAdapter
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.databinding.FragmentListActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivityFragment : Fragment() {
    private lateinit var binding: FragmentListActivityBinding
    private val viewModel: ListActivityViewModel by viewModels()

    private var user: AuthModel? = null
    private lateinit var activityAdapter: ListActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getAuthSession().observe(viewLifecycleOwner) { session ->
                user = session

                setupRecyleView()
                observeData()
            }
        }
    }

    private fun setupRecyleView() {
        binding.apply {
            rvUpcomingActivities.layoutManager = LinearLayoutManager(requireContext())
            rvUpcomingActivities.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State,
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val vSpacing = resources.getDimensionPixelSize(R.dimen.rv_vSpacing)
                        val hSpacing = resources.getDimensionPixelSize(R.dimen.rv_hSpacing)

                        outRect.set(hSpacing, vSpacing, hSpacing, vSpacing)
                    }
                },
            )
        }
    }

    private fun observeData() {
        user?.let {
            viewModel.getActivity(it.userId).observe(viewLifecycleOwner) { result ->
                result.onSuccess { response ->
                    activityAdapter = ListActivityAdapter(response)
                    binding.rvUpcomingActivities.adapter = activityAdapter

                    binding.tvEmptyActivity.visibility = View.GONE
                    binding.rvUpcomingActivities.visibility = View.VISIBLE
                }
                result.onFailure { response ->
                    binding.tvEmptyActivity.visibility = View.VISIBLE
                    binding.rvUpcomingActivities.visibility = View.GONE

                    Utils.showToast(
                        requireContext(),
                        "Error fetching article data: ${response.message}",
                    )
                }
            }
        }
    }
}
