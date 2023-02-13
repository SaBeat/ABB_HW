package com.example.navigation_jetpact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation_jetpact.databinding.FragmentGlobalBinding

class GlobalFragment : Fragment() {

    private lateinit var globalBinding: FragmentGlobalBinding
    private val args : GlobalFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        globalBinding = FragmentGlobalBinding.inflate(layoutInflater, container, false)
        return globalBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        globalBinding.tbGlobal.setNavigationOnClickListener { findNavController().popBackStack() }
        globalBinding.tvShowMessage.text = args.globalArgs
    }

}