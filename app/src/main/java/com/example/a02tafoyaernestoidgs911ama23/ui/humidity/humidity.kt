package com.example.a02tafoyaernestoidgs911ama23.ui.humidity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a02tafoyaernestoidgs911ama23.R
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentHumidityBinding
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentTemperatureBinding

class humidity : Fragment() {


    private var _binding: FragmentHumidityBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var viewModel: HumidityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        _binding = FragmentHumidityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnIncHum.setOnClickListener {
            //sharedViewModel.setRealTem(binding.temperatureTextView.text.toString)

            var prog = binding.humidityProgressBar.progress
            if(prog<=99){
                prog++
                updateProgressBar(prog)

            }
        }
        binding.btnDecrHum.setOnClickListener {
            //sharedViewModel.setRealTem(binding.temperatureTextView.text.toString)

            var prog = binding.humidityProgressBar.progress
            if(prog>=1){
                prog--
                updateProgressBar(prog)

            }
        }
        return root
    }
    fun updateProgressBar(value:Int){
        binding.humidityProgressBar.progress = value
        binding.humidityTextView.text = "$value %"
    }

}