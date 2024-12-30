package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.entity.Client

class ClientDiffItemCallback : DiffUtil.ItemCallback<Client>() {

    override fun areItemsTheSame(oldItem: Client, newItem: Client)
            = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Client, newItem: Client)
            = (oldItem == newItem)
}