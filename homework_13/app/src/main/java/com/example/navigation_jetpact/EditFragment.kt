package com.example.navigation_jetpact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation_jetpact.databinding.FragmentEditBinding

class EditFragment : Fragment() {
    private lateinit var editBinding : FragmentEditBinding
    private val args : EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editBinding = FragmentEditBinding.inflate(layoutInflater,container,false)

        return editBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editBinding.tvShowMessage.text = args.editArgs
        editBinding.tbEdit.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        editBinding.btnGoGlobal.setOnClickListener {
            val bundle = bundleOf("global_args" to "Come from Edit Fragment")
            findNavController().navigate(R.id.go_to_global_fragment, bundle)
        }
    }
}