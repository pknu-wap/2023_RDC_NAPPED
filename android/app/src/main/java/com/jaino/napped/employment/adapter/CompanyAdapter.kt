package com.jaino.napped.employment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaino.domain.model.Company
import com.jaino.napped.databinding.ItemCompanyBinding

class CompanyAdapter: ListAdapter<Company, CompanyAdapter.CompanyDataViewHolder>(callback) {

    companion object{
        val callback = object : DiffUtil.ItemCallback<Company>(){
            override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem.company == newItem.company
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyDataViewHolder {
        return CompanyDataViewHolder(ItemCompanyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CompanyDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CompanyDataViewHolder(private val binding: ItemCompanyBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Company){
            binding.company = item
        }
    }
}