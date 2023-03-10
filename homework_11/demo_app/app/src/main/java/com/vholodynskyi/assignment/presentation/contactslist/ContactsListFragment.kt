package com.vholodynskyi.assignment.presentation.contactslist

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.vholodynskyi.assignment.common.Constants.TAG
import com.vholodynskyi.assignment.data.api.contacts.ApiContact
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }

    private val contactsListViewModel by viewModel<ContactsListViewModel>()

    private var dbContactList = mutableListOf<DbContact>()

    private fun onContactClicked(id: Int) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate start")
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

                Log.v(TAG, "onCreateView start")
//                contactsListViewModel.handleEvent(ContactListEvent.DeleteAllContactListFromDatabase)
                rvContactList.layoutManager = LinearLayoutManager(context)
                rvContactList.adapter = contactAdapter

                getAllContactListFromDB()

                swipeToDelete(rvContactList) //TODO
                swipeRefreshList(swipeRefresh) //TODO
                (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
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

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    if (recyclerView.itemAnimator!!.isRunning) {

                        var top = 0;
                        var bottom = 0;

                        var childCount = recyclerView.layoutManager!!.childCount;
                        for (i in 0 until childCount) {
                            var child = recyclerView.layoutManager!!.getChildAt(i);
                            if (child!!.translationY.toInt() != 0) {
                                top = child.top;
                                bottom = top + child.translationY.toInt()
                                break;
                            }
                        }


                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.absoluteAdapterPosition
                    contactsListViewModel.handleEvent(ContactListEvent.DeleteContact(dbContactList[position]))

//                    apiContactSingletonList.removeAt(position)

                    rvContactList.adapter?.notifyItemRemoved(position)

                    Snackbar.make(
                        requireView(),
                        "Deleted",
                        Snackbar.LENGTH_LONG
                    ).show()

                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvContactList)
    }

    private fun swipeRefreshList(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener {
            getAllContactListFromDB()
//            apiContactSingletonList.shuffle(Random(System.currentTimeMillis()))
            binding?.swipeRefresh!!.isRefreshing = false
        }
    }

    private fun getAllContactListForFirstTime() {
        contactsListViewModel.handleEvent(ContactListEvent.GetAllContactList)
        lifecycleScope.launch {
            contactsListViewModel.contactList.collect { state ->
                state.contactList.let { appContactList ->
                    contactAdapter.differ.submitList(appContactList)
                    insertContactListToDb(appContactList)
                }

            }
        }

    }

    private fun getAllContactListFromDB() {
        contactsListViewModel.handleEvent(ContactListEvent.GetAllContactListFromDB)
        lifecycleScope.launch {
            contactsListViewModel.dbCOntactList.collect { state ->
                state.contactList.let { appContactList ->
                    contactAdapter.differ.submitList(appContactList)
                    contactAdapter.notifyDataSetChanged()
                }
            }
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
        contactsListViewModel.handleEvent(
            ContactListEvent.InsertAllContactListToDatabase(
                dbContactList
            )
        )
    }

    override fun onDestroyView() {
        Log.v(TAG, "onDestroyView start")
        super.onDestroyView()
        binding = null
    }

}