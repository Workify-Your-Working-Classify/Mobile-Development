package com.capbatu.workify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capbatu.workify.adapter.ListActivityAdapter.ViewHolder
import com.capbatu.workify.data.remote.response.ActivityResponse
import com.capbatu.workify.databinding.LayoutActivityItemBinding

class ListActivityAdapter(private val activities: List<ActivityResponse>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListActivityAdapter.ViewHolder {
        return ViewHolder(
            LayoutActivityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = activities.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(activities[position])
    }

    inner class ViewHolder(private val binding: LayoutActivityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityResponse) {
            binding.apply {
                tvTitle.text = item.namaKegiatan
                tvDate.text = item.tanggal
                tvDescription.text = item.deskripsi
                if (item.hasilPrediksi.toInt() == 1) {
                    tvImportant.visibility = View.VISIBLE
                } else {
                    tvImportant.visibility = View.GONE
                }
            }
        }
    }
}
