package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalendarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendario)

        val anteriorButton: Button = findViewById(R.id.anterior_calendario)
        anteriorButton.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalActivity::class.java)
            startActivity(intent)
        }

        // Recuperar la lista de hábitos seleccionados desde SharedPreferences
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val habitos = sharedPreferences.getStringSet("lista_habitos", setOf())?.toList() ?: listOf()

        // Recuperar los estados de los botones desde SharedPreferences
        val metodosEvaluacion = sharedPreferences.getStringSet("metodos_evaluacion", setOf())?.toList() ?: listOf()

        val habito1: TextView = findViewById(R.id.habito1)
        val habito2: TextView = findViewById(R.id.habito2)
        val habito3: TextView = findViewById(R.id.habito3)
        val habito4: TextView = findViewById(R.id.habito4)
        val textViews = listOf(habito1, habito2, habito3, habito4)

        // Referencias a las imágenes de las rachas
        val rachaImg1: ImageView = findViewById(R.id.rachaimg1)
        val rachaImg2: ImageView = findViewById(R.id.rachaimg2)
        val rachaImg3: ImageView = findViewById(R.id.rachaimg3)
        val rachaImg4: ImageView = findViewById(R.id.rachaimg4)
        val imageViews = listOf(rachaImg1, rachaImg2, rachaImg3, rachaImg4)

        // Hacer invisibles todos los TextViews e ImageViews al inicio
        textViews.forEach { it.visibility = View.GONE }
        imageViews.forEach { it.visibility = View.GONE }

        // Revisar el estado de cada botón para determinar si el hábito ha sido realizado
        for ((index, habito) in habitos.withIndex()) {
            if (index >= textViews.size) break

            // Revisar si el botón de "Si/No" tiene el valor de "Hecho" en true
            val isCheckBoxChecked = sharedPreferences.getBoolean("${habito}_checkbox", false)
            if (isCheckBoxChecked) {
                textViews[index].text = "Hoy haz realizado $habito"
                textViews[index].visibility = View.VISIBLE
                imageViews[index].visibility = View.VISIBLE
                continue
            }

            // Revisar si el botón de "Cantidad" tiene un valor mayor a 0
            val cantidad = sharedPreferences.getInt("${habito}_cantidad", 0)
            if (cantidad > 0) {
                textViews[index].text = "Hoy haz realizado $habito $cantidad veces"
                textViews[index].visibility = View.VISIBLE
                imageViews[index].visibility = View.VISIBLE
                continue
            }

            // Revisar si el cronómetro ha sido utilizado
            val tiempoCronometro = sharedPreferences.getLong("${habito}_cronometro", 0)
            if (tiempoCronometro > 0) {
                val minutes = tiempoCronometro / 60
                val seconds = tiempoCronometro % 60
                textViews[index].text = "Hoy haz realizado $habito durante $minutes minutos y $seconds segundos"
                textViews[index].visibility = View.VISIBLE
                imageViews[index].visibility = View.VISIBLE
                continue
            }
        }
    }
}
