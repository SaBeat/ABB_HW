package com.example.abb_note_app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.databinding.FragmentNoteListBinding
import com.example.abb_note_app.ui.home.adapter.NoteListAdapter
import com.example.abb_note_app.ui.viewmodel.NoteListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {
    private var noteListBinding : FragmentNoteListBinding?=null

    private val viewmodel : NoteListViewModel by viewModels()
    lateinit var noteListAdapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        noteListBinding = FragmentNoteListBinding.inflate(inflater)

        return noteListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        deleteNoteListById(view)
        deleteAllList()
        setupSearchView()
        getAllList()
        goToAddNoteFragment()

    }

    private fun setupRecyclerView(){
        noteListAdapter = NoteListAdapter()
        noteListBinding?.rvNoteList?.apply {
            adapter = noteListAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun getAllList(){
        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.getAllNotes().observe(viewLifecycleOwner){ noteList ->
                    setupRecyclerView()
                    noteListAdapter.differ.submitList(noteList)
                }
            }
        }
    }

    private fun deleteNoteListById(view: View){
        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.getAllNotes().observe(viewLifecycleOwner){
                    deleteNoteListItem(view)
                }
            }
        }
    }

    private fun deleteNoteListItem(view:View){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val noteModel = noteListAdapter.differ.currentList[position]
                viewmodel.deleteNote(noteModel)

                noteListAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(view, "Deleted " + noteModel.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            viewmodel.insertNote(noteModel)
                        }).show()
            }
        }).attachToRecyclerView(noteListBinding?.rvNoteList)
    }

    private fun setupSearchView(){
        noteListBinding?.searcView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {keyword ->
                    viewmodel.getNoteByKeyword(keyword).observe(viewLifecycleOwner){list ->
                        setupRecyclerView()
                        noteListAdapter.differ.submitList(list)
                    }
                }

                return true
            }

        })
    }

    private fun deleteAllList(){

        noteListBinding?.imageDeleteAll?.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alert")
            builder.setMessage("Do you want to delete all list?")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                viewmodel.deleteAllList()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private fun goToAddNoteFragment(){
        noteListBinding?.fab?.setOnClickListener {
            val note = NoteEntity(0,"","")
            val action = NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment(note)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        noteListBinding = null
        super.onDestroy()
    }
}