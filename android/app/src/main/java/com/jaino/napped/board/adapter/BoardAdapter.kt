package com.jaino.napped.board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaino.domain.model.Board
import com.jaino.napped.databinding.ItemBoardBinding

class BoardAdapter: ListAdapter<Board, BoardAdapter.BoardDataViewHolder>(callback) {

    companion object{
        val callback = object : DiffUtil.ItemCallback<Board>(){
            override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardDataViewHolder {
        return BoardDataViewHolder(
            ItemBoardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BoardDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class BoardDataViewHolder(private val binding: ItemBoardBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Board){
            binding.model = item
        }
    }
}