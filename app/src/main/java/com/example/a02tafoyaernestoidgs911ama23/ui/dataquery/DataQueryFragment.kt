package com.example.a02tafoyaernestoidgs911ama23.ui.dataquery

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.a02tafoyaernestoidgs911ama23.AdminSQLiteOpenHelper
import com.example.a02tafoyaernestoidgs911ama23.R
import com.example.a02tafoyaernestoidgs911ama23.databinding.FragmentDataQueryBinding

class DataQueryFragment : Fragment() {

    private var _binding: FragmentDataQueryBinding? = null
    private val binding get() = _binding!!
    private var datos: String? = null
    private lateinit var viewModel: DataQueryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DataQueryViewModel::class.java)
        _binding = FragmentDataQueryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnConsultarFragment.setOnClickListener(View.OnClickListener { Buscar() })
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun Buscar() {
        try {
            val admin = AdminSQLiteOpenHelper(context, "administracion", null, 1)
            val BaseDeDatos: SQLiteDatabase = admin.getWritableDatabase()
            datos =                "._________________________________________________________.\n"
            datos =datos + "|_________________________________________________________|\n"
            datos =datos + "|   Lectura       temp           hum      fecha_hora      |\n"
            val fila = BaseDeDatos.rawQuery("Select * From sensores", null)
            if (fila.moveToFirst()) {
                do {
                    val dato1 = fila.getString(0)
                    val dato2 = fila.getString(1)
                    val dato3 = fila.getString(2)
                    val dato4 = fila.getString(3)
                    datos =datos + "|   " + dato1 + " | " + dato2 + "  |  " + dato3 + "  |  " + dato4 + "  |\n"
                } while (fila.moveToNext())
            }
            fila.close()
            BaseDeDatos.close()
            //tv_datos_consultados.setText(datos);
            binding.tvDatosConsultados.setText(datos)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}