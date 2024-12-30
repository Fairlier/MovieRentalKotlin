package com.example.movierentalkotlin.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.movierentalkotlin.database.entity.Employee

class EmployeeDiffItemCallback : DiffUtil.ItemCallback<Employee>() {

    override fun areItemsTheSame(oldItem: Employee, newItem: Employee)
            = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee)
            = (oldItem == newItem)
}