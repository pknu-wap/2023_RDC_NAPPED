package com.jaino.napped.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaino.domain.model.Favorite
import com.jaino.napped.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val onRemoveButtonClicked: (Favorite) -> Unit,
): ListAdapter<Favorite, FavoriteAdapter.FavoriteDataViewHolder>(callback) {

    companion object{
        val callback = object : DiffUtil.ItemCallback<Favorite>(){
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.company == newItem.company
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDataViewHolder {
        return FavoriteDataViewHolder(ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FavoriteDataViewHolder(private val binding: ItemFavoriteBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Favorite){
            binding.model = item

            binding.ivFavoriteItemRemove.setOnClickListener {
                onRemoveButtonClicked(item)
            }
        }
    }
}