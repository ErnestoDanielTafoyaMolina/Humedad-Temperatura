package com.example.a02tafoyaernestoidgs911ama23

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.a02tafoyaernestoidgs911ama23.databinding.ActivityMainBinding
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.DateFormat
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    public var bluetoothIn: Handler? = null
    public val handlerState = 0
    private var btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private val DataStringIN = StringBuilder()
    private var MyConexionBT: ConnectedThread? = null
    var datos: String? = null

    private val BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val sharedViewModel:SharedViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bluetoothIn = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == handlerState) {
                    val readMessage = msg.obj as String
                    DataStringIN.append(readMessage)
                    val endOfLineIndex = DataStringIN.indexOf("#")
                    if (endOfLineIndex > 0) {
                        val dataInPrint = DataStringIN.substring(0, endOfLineIndex)
                        Log.i(TAG,dataInPrint)
                        sharedViewModel!!.setmDataInPrint(dataInPrint);
                        val dataSub=dataInPrint.split("$")
                        sharedViewModel!!.setTempReal(dataSub[1].toInt())
                        sharedViewModel!!.setHumReal(dataSub[2].toInt())
                        sharedViewModel!!.setCurrentDayTime(
                            DateFormat.getDateTimeInstance().format(
                                Date()
                            ))
                        //tv_datos_consultados.setText(dataInPrint);
                        DataStringIN.delete(0, DataStringIN.length)
                    }
                }
            }
        }
        btAdapter = BluetoothAdapter.getDefaultAdapter()
        VerificaEstadoBT()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_temperature, R.id.nav_humidity, R.id.nav_historialtemp, R.id.nav_dispositivos_vincuados_fragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    @SuppressLint("MissingPermission")
    private fun createBluetoothSocket(device: BluetoothDevice): BluetoothSocket? {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID)
    }


    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        val device: BluetoothDevice? =btAdapter?.getRemoteDevice(sharedViewModel?.getAddress()?.value)
        try {
            btSocket = device?.let { createBluetoothSocket(it) }
            Toast.makeText(baseContext,"\"onResume\" creación del socket exitosa",Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(baseContext, "\"onResume\"La creación del socket falló", Toast.LENGTH_SHORT).show()
        }
        try {
            btSocket!!.connect()
        } catch (e: IOException) {
            Toast.makeText(baseContext, "\"onResume\"La conexion al socket falló", Toast.LENGTH_SHORT).show();
            try {
                btSocket!!.close()
            } catch (e1: IOException) {
                Toast.makeText(baseContext, "\"onResume\"El cierre del socket falló", Toast.LENGTH_SHORT).show();
            }
        }
        MyConexionBT = ConnectedThread(btSocket!!)
        MyConexionBT!!.start()
    }



    override fun onPause() {
        super.onPause()
        try {
            btSocket!!.close()
            Toast.makeText(this, "\"onPause\", se cerró BT", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(getBaseContext(), "El cierre del socket falló", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private fun VerificaEstadoBT() {
        if (btAdapter == null) {
            Toast.makeText(baseContext, "El dispositivo no soporta bluetooth", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (btAdapter!!.isEnabled) {
                //Toast.makeText(getBaseContext(), "Bluetooth habilitado", Toast.LENGTH_SHORT).show();
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, 1)
            }
        }
    }
    private inner class ConnectedThread(socket: BluetoothSocket) : Thread() {
        private val mmInStream: InputStream?
        private val mmOutStream: OutputStream?

        init {
            var tmpIn: InputStream? = null
            var tmpOut: OutputStream? = null
            try {
                tmpIn = socket.inputStream
                tmpOut = socket.outputStream
            } catch (e: IOException) {
                Toast.makeText(baseContext, "No se pudo obtener información", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "No se pudo obtener información")
            }
            mmInStream = tmpIn
            mmOutStream = tmpOut
        }

        override fun run() {
            //byte[] byte_in= new byte[1];
            val buffer = ByteArray(256)
            var bytes: Int
            while (true) {
                try {
                    bytes = mmInStream!!.read(buffer)
                    val readMessage = String(buffer, 0, bytes)
                    bluetoothIn?.obtainMessage(handlerState, bytes, -1, readMessage)?.sendToTarget()
                    //mmInStream.read(byte_in);
                    //char ch=(char) byte_in[0];
                    //bluetoothIn.obtainMessage(handlerState,ch).sendToTarget();
                } catch (e: IOException) {
                    break
                }
            }
        }

        fun write(input: String) {
            try {
                mmOutStream!!.write(input.toByteArray())
            } catch (e: IOException) {
                Toast.makeText(baseContext, "El envío de datos falló", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "El envío de datos falló")
                //finish()
            }
        }
    }
}