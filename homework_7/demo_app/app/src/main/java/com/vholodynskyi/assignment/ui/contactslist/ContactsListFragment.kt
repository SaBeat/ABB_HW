package com.vholodynskyi.assignment.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory

open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }
    private val contactsListViewModel by lazy { ContactsListViewModel() }
    private var dbContactList = mutableListOf<DbContact>()
    private var afterDeletionContactList = mutableListOf<ApiContact>()

    private fun onContactClicked(id: Int) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                rvContactList.layoutManager = LinearLayoutManager(context)
                rvContactList.adapter = contactAdapter
                getAllContactList()
                swipeDeleteContactList(afterDeletionContactList as MutableList<ApiContact>)
                swipeRefreshList()
            }
            .also {
                binding = it
            }
            .root
    }

    private fun swipeRefreshList(){
        binding?.swipeRefresh?.setOnRefreshListener {
            contactAdapter.differ.submitList(GlobalFactory.apiContactSingletonList)
            binding?.swipeRefresh!!.isRefreshing = false
        }
    }

    private fun getAllContactList() {
        contactsListViewModel.contactList.observe(viewLifecycleOwner) { appContactList ->
            contactAdapter.differ.submitList(appContactList)
            GlobalFactory.apiContactSingletonList.addAll(appContactList)
            afterDeletionContactList = appContactList as MutableList<ApiContact>
            insertContactListToDb(appContactList)
        }
    }

    private fun swipeDeleteContactList(contactList:MutableList<ApiContact>){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedContact: ApiContact =
                    contactList[viewHolder.absoluteAdapterPosition]

                val position = viewHolder.absoluteAdapterPosition

                contactList.removeAt(viewHolder.absoluteAdapterPosition)

                Snackbar.make(requireView(), "Deleted " + deletedContact.name?.firstName, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            contactList.add(position, deletedContact)

                        }).show()
            }
        }).attachToRecyclerView(binding?.rvContactList)

    }

    private fun insertContactListToDb(appContactList:List<ApiContact>){
        for (appContact in appContactList){
            val dbContact = DbContact(0,appContact.name?.firstName,appContact.name?.lastName,appContact.email,appContact.picture?.thumbnail)
            dbContactList.add(dbContact)
        }
        contactsListViewModel.insertContactList(dbContactList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}