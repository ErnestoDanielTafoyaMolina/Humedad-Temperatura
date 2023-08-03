package com.example.a02tafoyaernestoidgs911ama23.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedViewModel.getRealTem().observe(viewLifecycleOwner,object : Observer<Int?> {
            override fun onChanged(value: Int?) {
                if (value != null) {
                    binding.textHome.text=value.toString()
                }
            }

        })


        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}