package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeleccionHabitosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seleccion_habitos)

        val siguienteButton: Button = findViewById(R.id.siguiente_habitos)
        siguienteButton.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalActivity::class.java)
            startActivity(intent)
        }

        val meditarButton: Button = findViewById(R.id.meditar)
        val dejarHabitoButton: Button = findViewById(R.id.dejar_habito)
        val ejercicioButton: Button = findViewById(R.id.ejercicio)
        val leerButton: Button = findViewById(R.id.leer)
        val otroButton: Button = findViewById(R.id.otro)
        val tomarAguaButton: Button = findViewById(R.id.tomar_agua)

        meditarButton.setOnClickListener {
            agregarHabitoSiNoExiste("Meditar") {
                val intent = Intent(this, EvaluarProgresoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Meditar")
                startActivity(intent)
            }
        }

        dejarHabitoButton.setOnClickListener {
            agregarHabitoSiNoExiste("Dejar un hábito") {
                val intent = Intent(this, DejarHabitoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Dejar un hábito")
                startActivity(intent)
            }
        }

        ejercicioButton.setOnClickListener {
            agregarHabitoSiNoExiste("Hacer ejercicio") {
                val intent = Intent(this, EvaluarProgresoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Hacer ejercicio")
                startActivity(intent)
            }
        }

        leerButton.setOnClickListener {
            agregarHabitoSiNoExiste("Leer") {
                val intent = Intent(this, EvaluarProgresoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Leer")
                startActivity(intent)
            }
        }

        otroButton.setOnClickListener {
            agregarHabitoSiNoExiste("Otro") {
                val intent = Intent(this, OtroHabitoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Otro")
                startActivity(intent)
            }
        }

        tomarAguaButton.setOnClickListener {
            agregarHabitoSiNoExiste("Tomar agua") {
                val intent = Intent(this, EvaluarProgresoActivity::class.java)
                intent.putExtra("habito_seleccionado", "Tomar agua")
                startActivity(intent)
            }
        }
    }

    // Método para verificar si un hábito ya está seleccionado y agregarlo si no lo está
    private fun agregarHabitoSiNoExiste(habito: String, onSuccess: () -> Unit) {
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val habitos = sharedPreferences.getStringSet("lista_habitos", mutableSetOf()) ?: mutableSetOf()

        if (habitos.size >= 4) {
            // Mostrar un mensaje si ya hay 4 hábitos seleccionados
            Toast.makeText(this, "Pagar premium para más hábitos", Toast.LENGTH_SHORT).show()
        } else if (habitos.contains(habito)) {
            // Mostrar un mensaje si el hábito ya fue seleccionado (solo si hay menos de 4 hábitos)
            Toast.makeText(this, "Este hábito ya ha sido seleccionado", Toast.LENGTH_SHORT).show()
        } else {
            // Agregar el hábito y continuar
            habitos.add(habito)
            sharedPreferences.edit().putStringSet("lista_habitos", habitos).apply()
            onSuccess()
        }
    }
}