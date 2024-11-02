package com.example.goodhabits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class Bienvenida2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bienvenida_2)

        val siguienteButton: Button = findViewById(R.id.siguiente2)
        siguienteButton.setOnClickListener {
            val intent = Intent(this, Bienvenida3Activity::class.java)
            startActivity(intent)
        }

        val radioButton1: RadioButton = findViewById(R.id.radioButton1)
        val radioButton2: RadioButton = findViewById(R.id.radioButton2)
        val radioButton3: RadioButton = findViewById(R.id.radioButton3)

        radioButton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        radioButton2.setOnClickListener {
            val intent = Intent(this, Bienvenida2Activity::class.java)
            startActivity(intent)
        }

        radioButton3.setOnClickListener {
            val intent = Intent(this, Bienvenida3Activity::class.java)
            startActivity(intent)
        }
    }
}