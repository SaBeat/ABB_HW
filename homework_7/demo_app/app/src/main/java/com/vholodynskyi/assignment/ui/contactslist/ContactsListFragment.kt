package com.vholodynskyi.assignment.ui.contactslist

import android.os.Bundle
import android.util.Log
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
import com.vholodynskyi.assignment.api.contacts.toDbContact
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
//    private var afterDeletionContactList = mutableListOf<ApiContact>()

    private fun onContactClicked(id: Int) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding?.rvContactList?.layoutManager = LinearLayoutManager(context)
//        binding?.rvContactList?.adapter = contactAdapter
        getAllContactListForFirstTime()
    }

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
                getAllContactListFromDB()
                swipeToDelete(rvContactList)
            }
            .also {
                binding = it
            }
            .root
    }

    private fun swipeToDelete(rvContactList: RecyclerView) {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val deletedContact: DbContact =
                        contactsListViewModel.getAllContactListFromDB()[viewHolder.absoluteAdapterPosition]

                    val position = viewHolder.absoluteAdapterPosition

                    contactsListViewModel.deleteContactById(position)

                    Snackbar.make(
                        requireView(),
                        "Deleted " + deletedContact.firstName,
                        Snackbar.LENGTH_LONG
                    ).show()

//                    contactAdapter.differ.submitList(contactsListViewModel.getAllContactListFromDB())
                    contactAdapter.notifyItemChanged(position)
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvContactList)
    }

//    private fun swipeRefreshList(){
//        binding?.swipeRefresh?.setOnRefreshListener {
//            contactAdapter.differ.submitList(GlobalFactory.apiContactSingletonList)
//            binding?.swipeRefresh!!.isRefreshing = false
//        }
//    }

    private fun getAllContactListForFirstTime() {
        contactsListViewModel.contactList.observe(requireActivity()) { appContactList ->
            contactAdapter.differ.submitList(appContactList.map { it.toDbContact() })
            contactAdapter.notifyDataSetChanged()
            insertContactListToDb(appContactList)
        }
    }

    private fun getAllContactListFromDB() {
        val dbContactList = contactsListViewModel.getAllContactListFromDB()
        contactAdapter.differ.submitList(dbContactList)
        contactAdapter.notifyDataSetChanged()
    }

    private fun insertContactListToDb(appContactList: List<ApiContact>) {
        for (appContact in appContactList) {
            val dbContact = DbContact(
                0,
                appContact.name?.firstName,
                appContact.name?.lastName,
                appContact.email,
                appContact.picture?.thumbnail
            )
            dbContactList.add(dbContact)
        }
        contactsListViewModel.insertContactList(dbContactList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        GlobalFactory.apiContactSingletonList.clear()
        contactsListViewModel.deleteAllContactList()
    }
}