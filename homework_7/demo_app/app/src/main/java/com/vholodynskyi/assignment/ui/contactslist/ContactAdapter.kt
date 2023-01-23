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

class ContactAdapter (
    private val context: Activity,
    private val onItemClicked: ItemClick
) : RecyclerView.Adapter<ViewHolder>() {

    val diffUtil = object : DiffUtil.ItemCallback<ApiContact>() {
        override fun areItemsTheSame(oldItem: ApiContact, newItem: ApiContact): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: ApiContact, newItem: ApiContact): Boolean {
            return true
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
            if(item.name !=null && item.email !=null) {
                txtFullName.text = "${item.name.firstName} ${item.name.lastName}"
                txtEmail.text = item.email
            }
            root.setOnClickListener {
                onItemClicked(holder.absoluteAdapterPosition)
            }
            if(item.picture != null) {
                Glide.with(context).load(item.picture.thumbnail)
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