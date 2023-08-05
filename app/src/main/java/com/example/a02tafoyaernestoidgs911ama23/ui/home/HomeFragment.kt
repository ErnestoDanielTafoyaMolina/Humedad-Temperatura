package com.example.a02tafoyaernestoidgs911ama23.ui.home

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a02tafoyaernestoidgs911ama23.AdminSQLiteOpenHelper
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentHomeBinding
import java.text.DateFormat
import java.util.Date

class HomeFragment : Fragment() {
    private var sharedViewModel: SharedViewModel? = null
    private val homeViewModel: HomeViewModel? = null
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
        sharedViewModel!!.getTempReal().observe(viewLifecycleOwner,object : Observer<Int?>{
            override fun onChanged(value: Int?) {
                if (value != null) {
                    binding.tvValorTemp.text=value.toString()
                }
                if (sharedViewModel!!.getHumReal().value != null) {
                    binding.tvValorHumedad.text=sharedViewModel!!.getHumReal().value.toString()
                }
                if (sharedViewModel!!.getCurrentDayTime().value != null) {
                    binding.tvFechaHoraActual.text=sharedViewModel!!.getCurrentDayTime().value
                }
            }
        })
        sharedViewModel!!.getHumReal().observe(viewLifecycleOwner,object : Observer<Int?>{
            override fun onChanged(value: Int?) {
                if (value != null) {
                    binding.tvValorHumedad.text=value.toString()
                }
                if (sharedViewModel!!.getTempReal().value != null) {
                    binding.tvValorTemp.text=sharedViewModel!!.getTempReal().value.toString()
                }
                if (sharedViewModel!!.getCurrentDayTime().value != null) {
                    binding.tvFechaHoraActual.text=sharedViewModel!!.getCurrentDayTime().value.toString()
                }
            }
        })
        sharedViewModel!!.getCurrentDayTime().observe(viewLifecycleOwner,object : Observer<String?>{
            override fun onChanged(value: String?) {
                if (value != null) {
                    binding.tvFechaHoraActual.text=value
                }
                if (sharedViewModel!!.getTempReal().value != null) {
                    binding.tvValorTemp.text=sharedViewModel!!.getTempReal().value.toString()
                }
                if (sharedViewModel!!.getHumReal().value != null) {
                    binding.tvValorHumedad.text=sharedViewModel!!.getHumReal().value.toString()
                }
            }
        })
        binding.btnGenerar.setOnClickListener { Generar() }
        binding.btnRegistrar.setOnClickListener { Registrar() }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun Registrar() =
        //sensores(lectura int primary key autoincrement not null,temperatura real,humedad real,fecha_hora date_time
        try {
            val admin = AdminSQLiteOpenHelper(context, "administracion", null, 1)
            val BaseDeDatos: SQLiteDatabase = admin.getWritableDatabase()
            //val temperatura = binding.tvValorTemp.text.toString()
            //val humedad = binding.tvValorHumedad.text.toString()
            //val fecha_hora = binding.tvFechaHoraActual.text.toString()
            val temperatura = sharedViewModel?.getTempReal()?.value.toString()
            val humedad = sharedViewModel?.getHumReal()?.value.toString()
            val fecha_hora =sharedViewModel?.getCurrentDayTime()?.value
            if (fecha_hora != null) {
                if (temperatura.isNotEmpty() && humedad.isNotEmpty() && fecha_hora.isNotEmpty()) {
                    val registro = ContentValues()
                    registro.put("temperatura", temperatura)
                    registro.put("humedad", humedad)
                    registro.put("fecha_hora", fecha_hora)
                    BaseDeDatos.insert("sensores", null, registro)
                    BaseDeDatos.close()
                    /*homeViewModel?.setTempAleatoria(0)
                    homeViewModel?.setHumAleatoria(0)
                    homeViewModel?.setCurrentDateTime("")*/
                    Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "algunos campos son nulos", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    fun Generar() {
        sharedViewModel!!.setTempReal((0..50).random())
        sharedViewModel!!.setHumReal((0..100).random())
        sharedViewModel!!.setCurrentDayTime(DateFormat.getDateTimeInstance().format(Date()))
    }
}