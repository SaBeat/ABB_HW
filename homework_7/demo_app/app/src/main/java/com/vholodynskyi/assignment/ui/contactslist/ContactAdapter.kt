package com.vholodynskyi.assignment.ui.contactslist

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.databinding.ItemContactListBinding
import com.vholodynskyi.assignment.db.contacts.DbContact

class ContactAdapter (
    private val context: Activity,
    private val onItemClicked: ItemClick
) : RecyclerView.Adapter<ViewHolder>() {

    val diffUtil = object : DiffUtil.ItemCallback<DbContact>() {
        override fun areItemsTheSame(oldItem: DbContact, newItem: DbContact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DbContact, newItem: DbContact): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            ItemContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder.binding) {
            if(item.firstName !=null && item.email !=null) {
                txtFullName.text = "${item.firstName} ${item.lastName}"
                txtEmail.text = item.email
            }
            root.setOnClickListener {
                onItemClicked(holder.absoluteAdapterPosition)
            }
            if(item.photo != null) {
                Glide.with(context).load(item.photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(contactImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

class ViewHolder (val binding: ItemContactListBinding) : RecyclerView.ViewHolder(binding.root)

typealias ItemClick = (Int) -> Unit