package com.dicoding.githubgram.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubgram.data.response.ItemsItem
import com.dicoding.githubgram.databinding.ItemLayoutBinding

class DetailUserAdapter : ListAdapter<ItemsItem, DetailUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(user: ItemsItem) {
            fun ImageView.loadUserAvatar(url: String) {
                Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(this)
            }

            itemBinding.apply {
                tvUsername.text = user.login
                user.avatarUrl?.let {
                    imUserPicture.loadUserAvatar(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
