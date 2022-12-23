package com.example.abb_note_app.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.abb_note_app.R
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.databinding.NoteListItemBinding
import com.example.abb_note_app.ui.add_note.AddNoteFragmentDirections
import com.example.abb_note_app.ui.home.NoteListFragmentDirections
import javax.inject.Inject

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {

    var onItemAddNoteClickListener: ((NoteEntity) -> Unit)? = null

    val diffUtil = object : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    class MyViewHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            NoteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val noteListItem = differ.currentList[position]
        holder.binding.apply {
            textTitle.text = noteListItem.title
            textBody.text = noteListItem.body

        }
        holder.itemView.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment(noteListItem)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}