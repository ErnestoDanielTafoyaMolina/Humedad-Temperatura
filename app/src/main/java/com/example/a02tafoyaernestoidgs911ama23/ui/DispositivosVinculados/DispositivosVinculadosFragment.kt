package com.example.a02tafoyaernestoidgs911ama23.ui.DispositivosVinculados

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.nfc.Tag
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.a02tafoyaernestoidgs911ama23.R
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentDispositivosVinculadosBinding

class DispositivosVinculadosFragment : Fragment() {
    private val TAG = "DispositivosVinculados"
    var IdLista: ListView? = null
    private var mBtAdapter: BluetoothAdapter? = null
    private var mPairedDevicesArrayAdapter: ArrayAdapter<*>? = null


    private var _binding:FragmentDispositivosVinculadosBinding?=null
    private val binding get() = _binding!!



    private var sharedViewModel: SharedViewModel? = null
    private lateinit var viewModel: DispositivosVinculadosViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel = ViewModelProvider(this).get(DispositivosVinculadosViewModel::class.java)
        _binding = FragmentDispositivosVinculadosBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VerificarEstadoBT()
        mPairedDevicesArrayAdapter = context?.let { ArrayAdapter<Any?>(it, R.layout.dispositivos_encontrados) }
        IdLista=binding.IdLista
        IdLista!!.setAdapter(mPairedDevicesArrayAdapter)
        IdLista!!.setOnItemClickListener(mDeviceClickListener)
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices: Set<BluetoothDevice?> = mBtAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                (mPairedDevicesArrayAdapter as ArrayAdapter<Any?>?)?.add( (device?.name) + "\n" + device?.address )
            }
        }

    }
    private val mDeviceClickListener =
        AdapterView.OnItemClickListener { av: AdapterView<*>?, v: View, arg2: Int, arg3: Long ->
            val info = (v as TextView).text.toString()
            sharedViewModel?.setAddress(info.substring(info.length - 17))
            Toast.makeText(context, "Dispositivo Seleccionado ${sharedViewModel?.getAddress()?.value} ${info.substring(info.length - 17)}", Toast.LENGTH_SHORT).show()
            Log.d(TAG,sharedViewModel?.getAddress()?.value.toString())
        }

    private fun VerificarEstadoBT() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBtAdapter == null) {
            Toast.makeText(context, "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            if (mBtAdapter!!.isEnabled()) {
                Log.d(TAG,"...Bluetooth Activado....")
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 1)
            }
        }

    }
}
