package temperature

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.a02tafoyaernestoidgs911ama23.R
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentTemperatureBinding

class TemperatureFragment : Fragment() {

    private var _binding: FragmentTemperatureBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    companion object {
//        fun newInstance() = TemperatureFragment()
//    }

    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var viewModel: TemperatureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedViewModel.setRealTem(binding.temperatureProgressBar.progress)
        sharedViewModel.getRealTem().observe(viewLifecycleOwner,object :Observer<Int?>{
            override fun onChanged(value: Int?) {
                if (value != null) {
                    updateProgressBar(value)
                }
            }

        })
        binding.btnIncTemp.setOnClickListener {
            //sharedViewModel.setRealTem(binding.temperatureTextView.text.toString)

            var prog = binding.temperatureProgressBar.progress
            if(prog<=49){
                prog++
                //updateProgressBar(prog)
                sharedViewModel.setRealTem(prog)

            }
        }
        binding.btnDecrTemp.setOnClickListener {
            //sharedViewModel.setRealTem(binding.temperatureTextView.text.toString)
            var prog = binding.temperatureProgressBar.progress
            if(prog>=1){
                prog--
                //updateProgressBar(prog)
                sharedViewModel.setRealTem(prog)

            }
        }


        return root
    }

    fun updateProgressBar(value:Int){
        binding.temperatureProgressBar.progress = value
        binding.temperatureTextView.text = "$value °C"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}