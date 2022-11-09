package com.example.mygithubuserapp3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuserapp3.data.local.entity.User
import com.example.mygithubuserapp3.databinding.ItemUserRowBinding

class ListUserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : ListViewHolder =
        ListViewHolder(ItemUserRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        val (username, avatar) = listUser[position]
        viewHolder.binding.tvItemAccount.text = username
        Glide.with(viewHolder.itemView.context)
            .load(avatar)
            .circleCrop()
            .into(viewHolder.binding.imgItemPhoto)
        viewHolder.itemView.setOnClickListener {onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])}
    }
    override fun getItemCount() = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}