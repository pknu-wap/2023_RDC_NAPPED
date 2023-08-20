package com.jaino.napped.employment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaino.domain.model.Employment
import com.jaino.napped.databinding.ItemEmploymentBinding

class EmploymentAdapter(
    private val onClick: () -> Unit
): ListAdapter<Employment, EmploymentAdapter.EmploymentDataViewHolder>(callback) {

    companion object{
        val callback = object : DiffUtil.ItemCallback<Employment>(){
            override fun areItemsTheSame(oldItem: Employment, newItem: Employment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Employment, newItem: Employment): Boolean {
                return oldItem.company == newItem.company
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmploymentDataViewHolder {
        return EmploymentDataViewHolder(
            ItemEmploymentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EmploymentDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class EmploymentDataViewHolder(private val binding: ItemEmploymentBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Employment){
            binding.model = item
            binding.cdItemEmployment.setOnClickListener {
                onClick()
            }
        }
    }
}