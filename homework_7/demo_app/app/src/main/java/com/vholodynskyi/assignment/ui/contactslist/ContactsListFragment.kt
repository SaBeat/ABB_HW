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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory
import java.util.*


open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }
    private val contactsListViewModel by lazy { ContactsListViewModel() }
    private var dbContactList = mutableListOf<DbContact>()
    val TAG = "DEBUG"

    private fun onContactClicked(id: Int) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG,"onCreate start")
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
                Log.v(TAG,"onCreateView start")
                contactsListViewModel.deleteAllContactList()
                rvContactList.layoutManager = LinearLayoutManager(context)
                rvContactList.adapter = contactAdapter
                contactAdapter.differ.submitList(GlobalFactory.apiContactSingletonList)
                contactAdapter.notifyDataSetChanged()
                swipeToDelete(rvContactList)
                swipeRefreshList(swipeRefresh)
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

                    val deletedContact: ApiContact =
                        GlobalFactory.apiContactSingletonList[viewHolder.absoluteAdapterPosition]

                    val position = viewHolder.absoluteAdapterPosition

                    GlobalFactory.apiContactSingletonList.removeAt(position)
                    rvContactList.adapter?.notifyItemRemoved(position)

                    Snackbar.make(
                        requireView(),
                        "Deleted " + deletedContact.name?.firstName,
                        Snackbar.LENGTH_LONG
                    ).show()

                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvContactList)
    }

    private fun swipeRefreshList(swipeRefreshLayout: SwipeRefreshLayout){
        swipeRefreshLayout.setOnRefreshListener {
            contactAdapter.differ.submitList(GlobalFactory.apiContactSingletonList)
            GlobalFactory.apiContactSingletonList.shuffle(Random(System.currentTimeMillis()))
            binding?.swipeRefresh!!.isRefreshing = false
        }
    }

    private fun getAllContactListForFirstTime() {
        contactsListViewModel.contactList.observe(requireActivity()) { appContactList ->
            GlobalFactory.apiContactSingletonList.addAll(appContactList)
            contactAdapter.differ.submitList(appContactList)
            contactAdapter.notifyDataSetChanged()
            insertContactListToDb(appContactList)
        }
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
        Log.v(TAG,"onDestroyView start")
        super.onDestroyView()
        binding = null
    }

}