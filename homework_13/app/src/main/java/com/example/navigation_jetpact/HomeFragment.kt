package com.example.navigation_jetpact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation_jetpact.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeBinding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeBinding.btnGoDetail.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecondFragment("Sent from Home Fragment"))
        }

        homeBinding.btnGoGlobal.setOnClickListener {
            val bundle = bundleOf("global_args" to "Come from Main Fragment")
            findNavController().navigate(R.id.go_to_global_fragment, bundle)
        }
    }


}