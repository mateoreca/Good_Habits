package com.example.goodhabits

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BotonCronometroActivity : AppCompatActivity() {

    private var tiempo: Int = 0
    private var corriendo: Boolean = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boton_cronometro)

        val btnIniciar: Button = findViewById(R.id.btn_iniciar)
        val btnFinalizar: Button = findViewById(R.id.btn_finalizar)
        val tiempoTextView: TextView = findViewById(R.id.tv_tiempo)

        val actualizarTiempo = object : Runnable {
            override fun run() {
                if (corriendo) {
                    tiempo++
                    val minutos = tiempo / 60
                    val segundos = tiempo % 60
                    tiempoTextView.text = String.format("%02d:%02d", minutos, segundos)
                    handler.postDelayed(this, 1000)
                }
            }
        }

        // Configurar el botón "Iniciar" para iniciar el cronómetro
        btnIniciar.setOnClickListener {
            if (!corriendo) {
                corriendo = true
                handler.post(actualizarTiempo)
            }
        }

        // Configurar el botón "Finalizar" para detener el cronómetro
        btnFinalizar.setOnClickListener {
            corriendo = false
        }
    }
}
