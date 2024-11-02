package com.example.goodhabits

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val crearCuentaButton: Button = findViewById(R.id.crear_cuenta)
        val anteriorButton: Button = findViewById(R.id.anterior_crear)
        val emailEditText: EditText = findViewById(R.id.editTextTextEmailAddress)
        val passwordEditText: EditText = findViewById(R.id.editTextTextPassword)

        // Configurar el botón "Crear Cuenta"
        crearCuentaButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validación de campos vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de formato básico de email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear cuenta con Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()

                        // Redirigir a la pantalla de inicio de sesión para autenticar los datos ingresados
                        val intent = Intent(this, InicioSesionActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Fallo en la creación de cuenta
                        Toast.makeText(this, "Error al crear la cuenta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Configurar el botón "Atrás" para llevar a la pantalla de inicio de sesión
        anteriorButton.setOnClickListener {
            val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
        }
    }
}
