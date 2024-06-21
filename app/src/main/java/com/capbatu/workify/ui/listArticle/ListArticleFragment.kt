package com.capbatu.workify.ui.listArticle

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capbatu.workify.R
import com.capbatu.workify.Utils
import com.capbatu.workify.adapter.ListArticleAdapter
import com.capbatu.workify.databinding.FragmentListArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListArticleFragment : Fragment() {
    private lateinit var binding: FragmentListArticleBinding
    private val viewModel: ListArticleViewModel by viewModels()

    private lateinit var articleAdapter: ListArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyleView()
        observeData()
    }

    private fun setupRecyleView() {
        binding.apply {
            rvArticles.layoutManager = LinearLayoutManager(requireContext())
            rvArticles.addItemDecoration(
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
        viewModel.getArticle().observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                articleAdapter = ListArticleAdapter(response)
                binding.rvArticles.adapter = articleAdapter
            }
            result.onFailure { response ->
                Utils.showToast(
                    requireContext(),
                    "Error fetching article data: ${response.message}",
                )
            }
        }
    }
}
