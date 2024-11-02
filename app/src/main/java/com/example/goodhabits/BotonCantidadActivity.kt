package com.example.goodhabits

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BotonCantidadActivity : AppCompatActivity() {

    private var cantidad: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boton_cantidad)

        val btnMenos: Button = findViewById(R.id.btn_menos)
        val btnMas: Button = findViewById(R.id.btn_mas)
        val cantidadTextView: TextView = findViewById(R.id.tv_cantidad)

        // Configurar el botón "menos" para disminuir la cantidad
        btnMenos.setOnClickListener {
            if (cantidad > 0) {
                cantidad--
                cantidadTextView.text = cantidad.toString()
            }
        }

        // Configurar el botón "más" para aumentar la cantidad
        btnMas.setOnClickListener {
            cantidad++
            cantidadTextView.text = cantidad.toString()
        }
    }
}
