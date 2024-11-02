package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracion)

        // Botón "Cerrar Sesión"
        val cerrarSesionButton: Button = findViewById(R.id.cerrar_sesion)
        cerrarSesionButton.setOnClickListener {
            // Navegar de vuelta a la pantalla de inicio de sesión
            val intent = Intent(this, InicioSesionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Botón "Eliminar Hábitos"
        val eliminarHabitosButton: Button = findViewById(R.id.eliminar_habitos)
        eliminarHabitosButton.setOnClickListener {
            // Eliminar todos los hábitos guardados en SharedPreferences
            val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Eliminar la lista de hábitos seleccionados
            editor.remove("lista_habitos")
            // Eliminar métodos de evaluación
            editor.remove("metodos_evaluacion")

            // Eliminar todos los estados relacionados con cada hábito
            val habitos = sharedPreferences.getStringSet("lista_habitos", setOf())?.toList() ?: listOf()
            for (habito in habitos) {
                editor.remove("${habito}_checkbox")
                editor.remove("${habito}_cantidad")
                editor.remove("${habito}_cronometro")
            }

            // Aplicar los cambios
            editor.apply()

            // Mostrar un mensaje para indicar que los hábitos han sido eliminados
            Toast.makeText(this, "Se han eliminado los hábitos", Toast.LENGTH_SHORT).show()
        }

        // Botón "Anterior"
        val anteriorButton: Button = findViewById(R.id.anterior_configuracion)
        anteriorButton.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}
