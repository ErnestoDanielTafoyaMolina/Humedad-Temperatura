package com.example.a02tafoyaernestoidgs911ama23.ui.historialtemperature

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a02tafoyaernestoidgs911ama23.R
import com.example.a02tafoyaernestoidgs911ama23.SharedViewModel
import com.example.a02tafoyaernestoidgs911ama23.databinding.HisrotialtemperatureFragmentBinding
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class hisrotialtemperature_fragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var lineChart:LineChart
    private val months = arrayOf("Enero","Febrero","Marzo","Abril")
    private val sale= intArrayOf(25,20,38,10,15)
    private val colors = intArrayOf(Color.BLACK,Color.RED,Color.GREEN, Color.BLUE, Color.LTGRAY)
    private var _binding: HisrotialtemperatureFragmentBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: HisrotialtemperatureFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel = ViewModelProvider(this).get(HisrotialtemperatureFragmentViewModel::class.java)
        _binding = HisrotialtemperatureFragmentBinding.inflate(inflater,container,false)
        val root:View=binding.root
        lineChart=binding.lineChart
        createCharts()
        return inflater.inflate(R.layout.hisrotialtemperature_fragment, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun getSameChart(
        chart: Chart<*>,
        description: String,
        textColor: Int,
        background: Int,
        animateY: Int,
        leyenda: Boolean
    ): Chart<*>? {
        chart.description.text = description
        chart.description.textColor = textColor
        chart.description.textSize = 15f
        chart.setBackgroundColor(background)
        chart.animateY(animateY)
        //Validar porque la grafica de radar y dispersion tiene dos datos especificos y la leyenda se crea de acuerdo a esos datos.
        if (leyenda) legend(chart)
        return chart
    }
    private fun legend(chart: Chart<*>) {
        val legend = chart.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        val entries = ArrayList<LegendEntry>()
        for (i in months.indices) {
            val entry = LegendEntry()
            entry.formColor = colors[i]
            entry.label = months[i]
            entries.add(entry)
        }
        legend.setCustom(entries)
    }
    private fun getLineEntries(): java.util.ArrayList<Entry>? {
        val entries = java.util.ArrayList<Entry>()
        for (i in sale.indices) entries.add(Entry(i.toFloat(), sale[i].toFloat()))
        return entries
    }
    //Eje horizontal o eje X
    private fun axisX(axis: XAxis) {
        axis.isGranularityEnabled = true
        axis.position = XAxis.XAxisPosition.BOTTOM
        axis.valueFormatter = IndexAxisValueFormatter(months)
    }
    //Eje Vertical o eje Y lado izquierdo
    private fun axisLeft(axis: YAxis) {
        axis.spaceTop = 30f
        axis.axisMinimum = 0f
        axis.granularity = 20f
    }
    //Eje Vertical o eje Y lado Derecho
    private fun axisRight(axis: YAxis) {
        axis.isEnabled = false
    }
    //Crear graficas
    fun createCharts() {
        //LineChart
        lineChart = (getSameChart(lineChart!!, "Ventas", Color.BLUE, Color.YELLOW, 3000, true) as LineChart?)!!
        lineChart!!.data = getLineData()
        lineChart!!.invalidate()
        axisX(lineChart!!.xAxis)
        axisLeft(lineChart!!.axisLeft)
        axisRight(lineChart!!.axisRight)
    }
    //Carasteristicas comunes en dataset
    private fun getDataSame(dataSet: DataSet<*>): DataSet<*>? {
        dataSet.setColors(*colors)
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 10f
        return dataSet
    }
    private fun getLineData(): LineData? {
        val lineDataSet = getDataSame(LineDataSet(getLineEntries(), "")) as LineDataSet?
        lineDataSet!!.lineWidth = 2.5f
        //Color de los circulos de la grafica
        lineDataSet.setCircleColors(*colors)
        //Tamaño de los circulos de la grafica
        lineDataSet.circleRadius = 5f
        //Sombra grafica
        lineDataSet.setDrawFilled(true)
        //Estilo de la linea picos(linear) o curveada(cubic) cuadrada(Stepped)
        lineDataSet.mode = LineDataSet.Mode.LINEAR
        return LineData(lineDataSet)
    }

}