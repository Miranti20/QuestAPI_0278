package com.example.tugas9

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.tugas9.ui.theme.Tugas9Theme
import com.example.tugas9.uicontroller.DataSiswaApp

class MainActivity : ComponentActivity() {

    private var izinJaringanLokal by mutableStateOf(false)

    private val pemintaIzin = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { diizinkan ->
        izinJaringanLokal = diizinkan
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        izinJaringanLokal = if (Build.VERSION.SDK_INT >= 37) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_LOCAL_NETWORK
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        setContent {
            Tugas9Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    if (izinJaringanLokal) {
                        DataSiswaApp(
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Izinkan akses jaringan lokal " +
                                        "untuk memuat data siswa dari server."
                            )

                            Button(
                                onClick = {
                                    mintaIzinJaringanLokal()
                                },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Izinkan akses")
                            }
                        }
                    }
                }
            }
        }

        if (!izinJaringanLokal) {
            mintaIzinJaringanLokal()
        }
    }

    private fun mintaIzinJaringanLokal() {
        if (Build.VERSION.SDK_INT >= 37) {
            pemintaIzin.launch(
                Manifest.permission.ACCESS_LOCAL_NETWORK
            )
        }
    }
}