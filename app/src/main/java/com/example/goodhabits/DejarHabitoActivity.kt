package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DejarHabitoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dejar_habito)

        val anteriorButton: Button = findViewById(R.id.anterior_dejar)
        val siguienteButton: Button = findViewById(R.id.siguiente_dejar)
        val dejarHabitoEditText: EditText = findViewById(R.id.dejar_habito_btn)

        // Botón "Anterior" que lleva a la actividad de selección de hábitos
        anteriorButton.setOnClickListener {
            val intent = Intent(this, SeleccionHabitosActivity::class.java)
            startActivity(intent)
        }

        // Botón "Siguiente" que lleva a la actividad de evaluar progreso
        siguienteButton.setOnClickListener {
            var habitoIngresado = dejarHabitoEditText.text.toString().trim()

            // Hacer que la primera letra del hábito esté en mayúscula
            if (habitoIngresado.isNotEmpty()) {
                habitoIngresado = habitoIngresado.replaceFirstChar { it.uppercase() }
            }

            // Formatear el hábito como "Dejar de (hábito ingresado)"
            val habitoFormateado = "Dejar de $habitoIngresado"

            // Validar que se haya ingresado un hábito
            if (habitoIngresado.isNotBlank()) {
                if (!esHabitoDuplicado(habitoFormateado)) {
                    guardarHabitoPersonalizado(habitoFormateado)
                    val intent = Intent(this, EvaluarProgresoActivity::class.java)
                    intent.putExtra("habito_seleccionado", habitoFormateado)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Este hábito ya ha sido ingresado", Toast.LENGTH_SHORT).show()
                }
            } else {
                dejarHabitoEditText.error = "Por favor, ingrese un hábito"
            }
        }
    }

    private fun esHabitoDuplicado(habito: String): Boolean {
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val habitos = sharedPreferences.getStringSet("lista_habitos", mutableSetOf()) ?: mutableSetOf()
        return habitos.contains(habito)
    }

    private fun guardarHabitoPersonalizado(habito: String) {
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val habitos = sharedPreferences.getStringSet("lista_habitos", mutableSetOf()) ?: mutableSetOf()

        // Eliminar el valor genérico "Dejar un hábito" si está presente
        habitos.remove("Dejar un hábito")

        // Agregar el hábito personalizado con el formato "Dejar de (hábito ingresado)"
        habitos.add(habito)
        editor.putStringSet("lista_habitos", habitos)
        editor.apply()
    }
}
