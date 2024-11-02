package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EvaluarProgresoActivity : AppCompatActivity() {

    private var habitoSeleccionado: String? = null
    private var metodoEvaluacion: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evaluar_progreso)

        habitoSeleccionado = intent.getStringExtra("habito_seleccionado")

        val anteriorButton: Button = findViewById(R.id.anterior_evaluar)
        val siNoButton: Button = findViewById(R.id.si_no)
        val cantidadButton: Button = findViewById(R.id.cantidad)
        val cronometroButton: Button = findViewById(R.id.cronometro)

        anteriorButton.setOnClickListener {
            val intentSeleccionHabitos = Intent(this, SeleccionHabitosActivity::class.java)
            startActivity(intentSeleccionHabitos)
        }


        siNoButton.setOnClickListener {
            metodoEvaluacion = "Si/No"
            guardarSeleccion() // Guardar la selección en SharedPreferences
            navegarFrecuenciaActivity()
        }

        cantidadButton.setOnClickListener {
            metodoEvaluacion = "Cantidad"
            guardarSeleccion() // Guardar la selección en SharedPreferences
            navegarFrecuenciaActivity()
        }

        cronometroButton.setOnClickListener {
            metodoEvaluacion = "Cronómetro"
            guardarSeleccion() // Guardar la selección en SharedPreferences
            navegarFrecuenciaActivity()
        }
    }

    // Guardar el hábito seleccionado y el método de evaluación en SharedPreferences sin duplicados
    private fun guardarSeleccion() {
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Recuperar la lista actual de hábitos y métodos de evaluación
        val habitos = sharedPreferences.getStringSet("lista_habitos", mutableSetOf()) ?: mutableSetOf()
        val evaluaciones = sharedPreferences.getStringSet("metodos_evaluacion", mutableSetOf()) ?: mutableSetOf()

        // Guardar el hábito seleccionado
        habitos.add(habitoSeleccionado ?: "")
        editor.putStringSet("lista_habitos", habitos)

        // Guardar el método de evaluación
        val nuevoMetodoEvaluacion = "$habitoSeleccionado:$metodoEvaluacion"
        evaluaciones.add(nuevoMetodoEvaluacion)
        editor.putStringSet("metodos_evaluacion", evaluaciones)

        editor.apply()
    }

    // Navegar a la actividad FrecuenciaActivity
    private fun navegarFrecuenciaActivity() {
        val intentFrecuencia = Intent(this, SeleccionHabitosActivity::class.java)
        startActivity(intentFrecuencia)
    }
}
