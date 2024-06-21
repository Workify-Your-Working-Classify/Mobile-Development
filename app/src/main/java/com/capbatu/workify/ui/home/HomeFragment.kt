package com.capbatu.workify.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
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
import com.capbatu.workify.Utils.getDate
import com.capbatu.workify.Utils.getGreeting
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.adapter.ListActivityAdapter
import com.capbatu.workify.adapter.ListArticleAdapter
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.databinding.FragmentHomeBinding
import com.capbatu.workify.ui.describe.DescribeActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var callback: SwipeViewPagerHome

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private var user: AuthModel? = null

    private lateinit var activityAdapter: ListActivityAdapter
    private lateinit var articleAdapter: ListArticleAdapter

    interface SwipeViewPagerHome {
        fun swipeToPage(page: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SwipeViewPagerHome) {
            callback = context
        } else {
            throw RuntimeException("$context must implement SwipeViewPagerHome")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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

                updateUI()
                setupRecyleView()
                observeData()
                observePoint()
                setupAction()
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            btnActivity.setOnClickListener {
                callback.swipeToPage(1)
            }

            btnArticle.setOnClickListener {
                callback.swipeToPage(3)
            }

            btnLearn.setOnClickListener {
                Intent(requireContext(), DescribeActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    private fun observePoint() {
        binding.apply {
            viewModel.stressPoint.observe(viewLifecycleOwner) { stressPoint ->
                tvStressPoint.text = stressPoint.toString()

                when (stressPoint) {
                    in 0..24 -> {
                        tvStressPoint.setBackgroundTintList(
                            ColorStateList.valueOf(
                                Color.parseColor(
                                    "#4CAF50",
                                ),
                            ),
                        )
                        tvStressPoint.setBackgroundResource(R.drawable.bg_circle)
                        tvStressLevel.setTextColor(Color.parseColor("#4CAF50"))
                    }

                    in 25..49 -> {
                        tvStressPoint.setBackgroundTintList(
                            ColorStateList.valueOf(
                                Color.parseColor(
                                    "#FFEB3B",
                                ),
                            ),
                        )
                        tvStressPoint.setBackgroundResource(R.drawable.bg_circle)
                        tvStressLevel.setTextColor(Color.parseColor("#FFEB3B"))
                    }

                    in 50..74 -> {
                        tvStressPoint.setBackgroundTintList(
                            ColorStateList.valueOf(
                                Color.parseColor(
                                    "#FF9800",
                                ),
                            ),
                        )
                        tvStressPoint.setBackgroundResource(R.drawable.bg_circle)
                        tvStressLevel.setTextColor(Color.parseColor("#FF9800"))
                    }

                    else -> {
                        tvStressPoint.setBackgroundTintList(
                            ColorStateList.valueOf(
                                Color.parseColor(
                                    "#F44336",
                                ),
                            ),
                        )
                        tvStressPoint.setBackgroundResource(R.drawable.bg_circle)
                        tvStressLevel.setTextColor(Color.parseColor("#F44336"))
                    }
                }
            }

            viewModel.stressLevel.observe(viewLifecycleOwner) { stressLevel ->
                tvStressLevel.text = stressLevel.toString()
            }
        }
    }

    private fun updateUI() {
        binding.apply {
            tvGreetings.text = getGreeting(requireContext())
            tvDisplayName.text = user?.displayName?.toUpperCase(Locale.ROOT) ?: ""
            tvDateNow.text = getDate()
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

            rvArticles.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvArticles.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State,
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val vSpacing = resources.getDimensionPixelSize(R.dimen.rv_hSpacing)
                        val hSpacing = resources.getDimensionPixelSize(R.dimen.rv_vSpacing)

                        outRect.set(hSpacing, vSpacing, hSpacing, vSpacing)
                    }
                },
            )
        }
    }

    private fun observeData() {
        binding.apply {
            user?.let {
                viewModel.getActivity(it.userId).observe(viewLifecycleOwner) { result ->
                    result.onSuccess { response ->
                        activityAdapter = ListActivityAdapter(response)

                        tvEmptyActivity.visibility = View.GONE
                        rvUpcomingActivities.visibility = View.VISIBLE
                        rvUpcomingActivities.adapter = activityAdapter
                    }
                    result.onFailure { response ->
                        tvEmptyActivity.visibility = View.VISIBLE
                        rvUpcomingActivities.visibility = View.GONE

                        showToast(
                            requireContext(),
                            "Error fetching activity data: ${response.message}",
                        )
                    }
                }

                viewModel.getArticle().observe(viewLifecycleOwner) { result ->
                    result.onSuccess { response ->
                        articleAdapter = ListArticleAdapter(response)
                        rvArticles.adapter = articleAdapter
                    }
                    result.onFailure { response ->
                        showToast(
                            requireContext(),
                            "Error fetching article data: ${response.message}",
                        )
                    }
                }
            }
        }
    }
}
