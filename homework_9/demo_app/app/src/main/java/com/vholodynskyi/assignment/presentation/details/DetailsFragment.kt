package com.vholodynskyi.assignment.presentation.details

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.common.Constants.apiContactSingletonList
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                getList(args.id)
                setHasOptionsMenu(true)
                (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            .root
    }

    private fun getList(contactId: Int) {
        val apiContact = apiContactSingletonList[contactId]

        binding?.txtDetailFullName?.text =if(apiContact.name?.firstName !=null)
            "${apiContact.name.firstName} ${apiContact.name.lastName}" else "Sabit Sadigli"

        binding?.txtDetailEmail?.text = apiContact.email

        binding?.imageDetail?.let {
            Glide.with(requireContext()).load(apiContact.picture?.thumbnail).centerCrop()
                .placeholder(R.drawable.ic_launcher_background).into(
                it
            )
        }
    }

    private fun deleteDetailList(contactId: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title_delete))
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            apiContactSingletonList.removeAt(contactId)
            findNavController()
                .navigateUp()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            R.id.delete_contact -> {
                deleteDetailList(args.id)
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