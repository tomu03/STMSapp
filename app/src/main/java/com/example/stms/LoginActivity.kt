package com.example.stms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var btnLogin: Button
    private lateinit var tvGoRegister: TextView

    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        spinnerRole = findViewById(R.id.spinnerRole)
        btnLogin = findViewById(R.id.btnLogin)
        tvGoRegister = findViewById(R.id.tvGoRegister)

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            val role = spinnerRole.selectedItem.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Email & Password required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (role == "Student") {
                    val student = db.studentDao().login(email, pass)
                    if (student != null) {
                        val intent = Intent(this@LoginActivity, StudentProfileActivity::class.java)
                        intent.putExtra("pk", student.pk)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid student credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val teacher = db.teacherDao().login(email, pass)
                    if (teacher != null) {
                        val intent = Intent(this@LoginActivity, TeacherProfileActivity::class.java)
                        intent.putExtra("pk", teacher.pk)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid teacher credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}