package com.hana.codetest.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hana.codetest.databinding.UserItemsBinding
import com.hana.codetest.models.user.User

class UserListAdapter() :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var onItemClick: ((User) -> Unit)? = null
    var userList: List<User> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            UserItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    fun setData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userList[position])
        }
    }

    class ViewHolder(val binding: UserItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            with(binding) {
                tvName.text = data.name
                tvEmail.text = data.email

            }

        }
    }

}