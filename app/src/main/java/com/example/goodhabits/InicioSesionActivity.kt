package com.example.goodhabits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val crearCuentaButton: Button = findViewById(R.id.crear_cuenta)
        crearCuentaButton.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        val iniciarSesionButton: Button = findViewById(R.id.iniciar_sesion)
        val emailEditText: EditText = findViewById(R.id.editTextTextEmailAddress)
        val passwordEditText: EditText = findViewById(R.id.editTextTextPassword)

        iniciarSesionButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validación de campos vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Iniciar sesión con Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Inicio de sesión exitoso
                        val intent = Intent(this, SeleccionHabitosActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Fallo en el inicio de sesión
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
