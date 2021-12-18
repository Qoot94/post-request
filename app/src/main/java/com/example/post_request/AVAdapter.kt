package com.example.post_request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.post_request.databinding.ItemRowBinding


class RVAdapter(private val container: ArrayList<RecipeItem>) :
    RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = container[position]
        holder.binding.apply {
            tvName.text = item.name
            tvLocation.text = item.location
            tvPK.text = item.pk.toString()
        }
    }

    override fun getItemCount() = container.size
}