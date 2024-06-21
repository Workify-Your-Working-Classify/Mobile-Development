package com.capbatu.workify.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capbatu.workify.R
import com.capbatu.workify.Utils.isValidUrl
import com.capbatu.workify.Utils.showToast
import com.capbatu.workify.data.remote.response.ArticleResponse
import com.capbatu.workify.databinding.LayoutArticleItemBinding

class ListArticleAdapter(private val articles: List<ArticleResponse>) :
    RecyclerView.Adapter<ListArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListArticleAdapter.ViewHolder {
        return ViewHolder(
            LayoutArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(articles[position])
    }

    inner class ViewHolder(private val binding: LayoutArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleResponse) {
            binding.apply {
                if (item.picture1.isValidUrl()) {
                    Glide.with(itemView.context)
                        .load(item.picture1.toUri())
                        .into(ivPicture)
                } else {
                    ivPicture.setImageResource(R.drawable.img_broken)
                }
                tvTitle.text = item.title
                tvAuthor.text = item.author
            }

            binding.root.setOnClickListener {
                if (item.source.isValidUrl()) {
                    val intent = Intent(Intent.ACTION_VIEW, item.source.toUri())
                    itemView.context.startActivity(intent)
                } else {
                    showToast(itemView.context, "Invalid URL")
                }
            }
        }
    }
}
