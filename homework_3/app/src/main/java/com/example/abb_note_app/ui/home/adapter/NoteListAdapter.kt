package com.example.abb_note_app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.databinding.NoteListItemBinding

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {

    val diffUtil = object: DiffUtil.ItemCallback<NoteEntity>(){
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    class MyViewHolder(val binding:NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(NoteListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val noteListItem = differ.currentList[position]
        holder.binding.apply {
            textTitle.text = noteListItem.title
            textBody.text = noteListItem.body
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}