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
        private var sharedViewModel: SharedViewModel? = null
        private var _binding: FragmentTemperatureBinding? = null // This property is only valid between onCreateView and onDestroyView.
        private val binding get() = _binding!!
        override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View {
            _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
            sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            val root: View = binding.root
            sharedViewModel!!.getTempReal()!!.observe(
                /* owner = */ viewLifecycleOwner,
                /* observer = */
                object : Observer<Int?> {
                    override fun onChanged(value: Int?) {
                        if (value != null) {
                            updateProgressBar(value)
                        }
                    }
                },
            )
            binding.btnIncTemp.setOnClickListener {
                //var prog = binding.temperatureProgressBar.progress
                var prog = sharedViewModel!!.getTempReal().value
                if (prog != null) {
                    if (prog <= 90) {
                        prog++
                        //updateProgressBar(prog)
                        sharedViewModel!!.setTempReal(prog)
                    }
                }
            }
            binding.btnDecrTemp.setOnClickListener {
                //var prog=binding.temperatureProgressBar.progress
                var prog= sharedViewModel!!.getTempReal().value
                if (prog != null) {
                    if (prog >= 1) {
                        prog--
                        //updateProgressBar(prog)
                        sharedViewModel!!.setTempReal(prog)
                    }
                }
            }
            return root
        }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
        private fun updateProgressBar(value: Int) {
            binding.temperatureProgressBar.progress = value
            binding.temperatureTextView.text = "$value °C"
        }
    }