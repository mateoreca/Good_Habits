package com.example.goodhabits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.*

class PantallaPrincipalActivity : AppCompatActivity() {

    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var elapsedTimeInSeconds: Long = 0 // Tiempo transcurrido en segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_principal)

        val textViewFecha: TextView = findViewById(R.id.textViewFecha)
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        val fechaActual = dateFormat.format(Date())
        textViewFecha.text = "Hoy, $fechaActual"

        val imageButton: ImageButton = findViewById(R.id.imageButton4)
        imageButton.setOnClickListener {
            val intent = Intent(this, CalendarioActivity::class.java)
            startActivity(intent)
        }

        val añadirButton: Button = findViewById(R.id.añadir)
        añadirButton.setOnClickListener {
            val intentSeleccionHabitos = Intent(this, SeleccionHabitosActivity::class.java)
            startActivity(intentSeleccionHabitos)
        }

        val imageButtonConfiguracion: ImageButton = findViewById(R.id.imageButton3)
        imageButtonConfiguracion.setOnClickListener {
            val intentConfiguracion = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intentConfiguracion)
        }

        // Recuperar la lista de hábitos seleccionados desde SharedPreferences
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        val habitos = sharedPreferences.getStringSet("lista_habitos", setOf())?.toList() ?: listOf()
        val metodosEvaluacion = sharedPreferences.getStringSet("metodos_evaluacion", setOf())?.toList() ?: listOf()

        // Limpiar los TextViews previos antes de agregar nuevos
        val habito1: TextView = findViewById(R.id.habito1)
        val habito2: TextView = findViewById(R.id.habito2)
        val habito3: TextView = findViewById(R.id.habito3)
        val habito4: TextView = findViewById(R.id.habito4)
        val textViews = listOf(habito1, habito2, habito3, habito4)

        // Limpiar los TextView previos para evitar duplicados
        textViews.forEach { it.text = "" }

        val inflater = layoutInflater

        // Asignar los hábitos seleccionados a los TextView sin duplicarlos
        for ((index, habito) in habitos.withIndex()) {
            if (index >= textViews.size) break

            textViews[index].text = habito

            // Buscar el método de evaluación correspondiente
            val metodoEvaluacion = metodosEvaluacion.find { it.startsWith("$habito:") }?.split(":")?.get(1)

            // Añadir un componente específico según el método de evaluación
            when (metodoEvaluacion) {
                "Si/No" -> {
                    val checkBoxLayout = inflater.inflate(R.layout.boton_si_no, null) as LinearLayout
                    val checkBox: CheckBox = checkBoxLayout.findViewById(R.id.check_hecho)

                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.startToEnd = textViews[index].id
                    layoutParams.topToTop = textViews[index].id
                    layoutParams.marginStart = 16
                    checkBoxLayout.layoutParams = layoutParams

                    val parentLayout = textViews[index].parent as ConstraintLayout
                    parentLayout.addView(checkBoxLayout)

                    // Recuperar el estado del checkbox desde SharedPreferences
                    val isChecked = sharedPreferences.getBoolean("${habito}_checkbox", false)
                    checkBox.isChecked = isChecked

                    // Guardar el estado del checkbox cuando se cambia
                    checkBox.setOnCheckedChangeListener { _, isChecked ->
                        sharedPreferences.edit().putBoolean("${habito}_checkbox", isChecked).apply()
                    }
                }
                "Cantidad" -> {
                    val cantidadLayout = inflater.inflate(R.layout.boton_cantidad, null) as LinearLayout

                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.startToEnd = textViews[index].id
                    layoutParams.topToTop = textViews[index].id
                    layoutParams.marginStart = 16
                    cantidadLayout.layoutParams = layoutParams

                    val parentLayout = textViews[index].parent as ConstraintLayout
                    parentLayout.addView(cantidadLayout)

                    val menosButton: Button = cantidadLayout.findViewById(R.id.btn_menos)
                    val masButton: Button = cantidadLayout.findViewById(R.id.btn_mas)
                    val cantidadTextView: TextView = cantidadLayout.findViewById(R.id.tv_cantidad)

                    val cantidad = sharedPreferences.getInt("${habito}_cantidad", 0)
                    cantidadTextView.text = cantidad.toString()

                    menosButton.setOnClickListener {
                        val currentValue = cantidadTextView.text.toString().toInt()
                        if (currentValue > 0) {
                            val newValue = currentValue - 1
                            cantidadTextView.text = newValue.toString()
                            sharedPreferences.edit().putInt("${habito}_cantidad", newValue).apply()
                        }
                    }

                    masButton.setOnClickListener {
                        val currentValue = cantidadTextView.text.toString().toInt()
                        val newValue = currentValue + 1
                        cantidadTextView.text = newValue.toString()
                        sharedPreferences.edit().putInt("${habito}_cantidad", newValue).apply()
                    }
                }
                "Cronómetro" -> {
                    val cronometroLayout = inflater.inflate(R.layout.boton_cronometro, null) as LinearLayout

                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.startToEnd = textViews[index].id
                    layoutParams.topToTop = textViews[index].id
                    layoutParams.marginStart = 16
                    cronometroLayout.layoutParams = layoutParams

                    val parentLayout = textViews[index].parent as ConstraintLayout
                    parentLayout.addView(cronometroLayout)

                    val iniciarButton: Button = cronometroLayout.findViewById(R.id.btn_iniciar)
                    val finalizarButton: Button = cronometroLayout.findViewById(R.id.btn_finalizar)
                    val cronometroTextView: TextView = cronometroLayout.findViewById(R.id.tv_tiempo)

                    elapsedTimeInSeconds = sharedPreferences.getLong("${habito}_cronometro", 0)
                    val minutes = elapsedTimeInSeconds / 60
                    val seconds = elapsedTimeInSeconds % 60
                    cronometroTextView.text = String.format("%02d:%02d", minutes, seconds)

                    iniciarButton.setOnClickListener {
                        if (!isTimerRunning) {
                            isTimerRunning = true
                            iniciarCronometro(cronometroTextView, habito)
                        }
                    }

                    finalizarButton.setOnClickListener {
                        detenerCronometro(habito)
                        cronometroTextView.text = "Tiempo detenido"
                    }
                }
            }
        }
    }

    // Método para iniciar el cronómetro
    private fun iniciarCronometro(cronometroTextView: TextView, habito: String) {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTimeInSeconds++
                val minutes = elapsedTimeInSeconds / 60
                val seconds = elapsedTimeInSeconds % 60
                cronometroTextView.text = String.format("%02d:%02d", minutes, seconds)

                // Guardar el tiempo transcurrido en SharedPreferences
                val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
                sharedPreferences.edit().putLong("${habito}_cronometro", elapsedTimeInSeconds).apply()
            }

            override fun onFinish() {
                // No se utilizará finish en este caso ya que el cronómetro se reinicia cada segundo
            }
        }.start()
    }

    // Método para detener el cronómetro
    private fun detenerCronometro(habito: String) {
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false

        // Guardar el tiempo transcurrido en SharedPreferences
        val sharedPreferences = getSharedPreferences("habitos_seleccionados", Context.MODE_PRIVATE)
        sharedPreferences.edit().putLong("${habito}_cronometro", elapsedTimeInSeconds).apply()

        elapsedTimeInSeconds = 0 // Reiniciar el tiempo transcurrido
    }
}
