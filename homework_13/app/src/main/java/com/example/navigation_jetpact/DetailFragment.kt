package com.example.navigation_jetpact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigation_jetpact.databinding.FragmentDetailBinding
import com.example.navigation_jetpact.databinding.FragmentHomeBinding

class DetailFragment : Fragment() {
    private lateinit var detailBinding : FragmentDetailBinding
    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(layoutInflater,container,false)

        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailBinding.tvShowMessage.text = args.detailArgs

        detailBinding.tbDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        detailBinding.btnGoEdit.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionSecondFragmentToThirdFragment("Sent from Detail Fragment"))
        }

        detailBinding.btnGoGlobal.setOnClickListener {
            val bundle = bundleOf("global_args" to "Come from Detail Fragment")
            findNavController().navigate(R.id.go_to_global_fragment, bundle)
        }
    }

}