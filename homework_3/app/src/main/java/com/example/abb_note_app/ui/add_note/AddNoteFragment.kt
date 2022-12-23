package com.example.abb_note_app.ui.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.abb_note_app.R
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.databinding.FragmentAddNoteBinding
import com.example.abb_note_app.ui.viewmodel.NoteListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {
    private val viewmodel: NoteListViewModel by viewModels()
    private val args: AddNoteFragmentArgs by navArgs()
    lateinit var noteEntity:NoteEntity

    private var addNoteBinding: FragmentAddNoteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater)
        noteEntity = args.noteEntity
        return addNoteBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoteBinding?.etTitle?.setText(noteEntity.title)
        addNoteBinding?.etBody?.setText(noteEntity.body)

        if (args.noteEntity.title != "") {
            updateNote()
            addNoteBinding?.btnAdd?.setOnClickListener {
                if (checkEtIsEmptyOrNull()) {
                    updateNote()
                    Snackbar.make(view, "Note successfully updated", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
                } else {
                    Snackbar.make(view, "Title or Body is empty", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {

            addNoteBinding?.btnAdd?.setOnClickListener {
                if (checkEtIsEmptyOrNull()) {
                    addNote()
                    Snackbar.make(view, "Note successfully added", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
                } else {
                    Snackbar.make(view, "Title or Body is empty", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateNote() {
        addNoteBinding?.btnAdd?.text = getString(R.string.update)

        addNoteBinding?.apply {
            val title = etTitle.text.toString()
            val body = etBody.text.toString()
            val note = NoteEntity(noteEntity.id, title, body)
            viewmodel.updateNote(note)
        }
    }

    private fun addNote() {

        val title = addNoteBinding?.etTitle?.text.toString()
        val body = addNoteBinding?.etBody?.text.toString()
        val noteEntity = NoteEntity(0, title, body)
        viewmodel.insertNote(noteEntity)
    }

    private fun checkEtIsEmptyOrNull(): Boolean {
        return !(addNoteBinding?.etTitle?.text.isNullOrEmpty() || addNoteBinding?.etBody?.text.isNullOrEmpty())
    }

    override fun onDestroy() {
        addNoteBinding = null
        super.onDestroy()
    }
}