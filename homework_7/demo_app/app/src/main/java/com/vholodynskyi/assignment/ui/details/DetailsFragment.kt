package com.vholodynskyi.assignment.ui.details

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import com.vholodynskyi.assignment.di.GlobalFactory
import com.vholodynskyi.assignment.ui.contactslist.ContactsListFragmentDirections
import com.vholodynskyi.assignment.ui.contactslist.ContactsListViewModel
import kotlinx.coroutines.launch


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null
    val args: DetailsFragmentArgs by navArgs()

    private val contactsListViewModel by lazy { ContactsListViewModel() }
    private val detailsViewModel by viewModels<DetailsViewModel> { GlobalFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                getList(args.id)
                setHasOptionsMenu(true);
            }
            .root
    }

    private fun getList(contactId: Int) {
//        val apiContact = GlobalFactory.apiContactSingletonList[contactId]
        val dbContact = contactsListViewModel.getContactById(contactId+1)


        binding?.txtDetailFullName?.text =
            "${dbContact.firstName} ${dbContact.lastName}"

        binding?.txtDetailEmail?.text = dbContact.email

        binding?.imageDetail?.let {
            Glide.with(requireContext()).load(dbContact.photo).centerCrop()
                .placeholder(R.drawable.ic_launcher_background).into(
                it
            )
        }
    }

    private fun deleteDetailList(contactId: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure to delete contact?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            contactsListViewModel.deleteContactById(contactId)
            findNavController()
                .navigate(R.id.action_details_to_contactList)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete_contact -> {
                deleteDetailList(args.id+1)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}