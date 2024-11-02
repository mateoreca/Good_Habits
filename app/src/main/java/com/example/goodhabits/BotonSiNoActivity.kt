package com.example.goodhabits

import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class BotonSiNoActivity : AppCompatActivity() {

    private var hecho: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boton_si_no)

        val checkBox: CheckBox = findViewById(R.id.check_hecho)

        // Configurar el CheckBox para marcar "hecho" o "no hecho"
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            hecho = isChecked
        }
    }
}
