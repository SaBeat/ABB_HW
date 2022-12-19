package com.example.abb_note_app.ui.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.abb_note_app.R
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.databinding.FragmentAddNoteBinding
import com.example.abb_note_app.ui.home.NoteListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private val viewmodel : NoteListViewModel by viewModels()

    private var addNoteBinding : FragmentAddNoteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater)

        return addNoteBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoteBinding?.btnAdd?.setOnClickListener {
            if(checkEtIsEmptyOrNull()){
                addNote()
                Snackbar.make(view,"Note successfully added",Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
            }else{
                Snackbar.make(view,"Title or Body is empty",Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun addNote(){

        val title = addNoteBinding?.etTitle?.text.toString()
        val body = addNoteBinding?.etBody?.text.toString()
        val noteEntity = NoteEntity(0,title,body)
        viewmodel.insertNote(noteEntity)
    }

    private fun checkEtIsEmptyOrNull():Boolean{
        return !(addNoteBinding?.etTitle?.text.isNullOrEmpty() || addNoteBinding?.etBody?.text.isNullOrEmpty())
    }

    override fun onDestroy() {
        addNoteBinding = null
        super.onDestroy()
    }
}