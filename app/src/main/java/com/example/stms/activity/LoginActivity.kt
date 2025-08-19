package com.example.stms.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stms.database.AppDatabase
import com.example.stms.R
import com.example.stms.activity.RegisterActivity
import com.example.stms.activity.StudentProfileActivity
import com.example.stms.activity.TeacherProfileActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val spinnerRole: Spinner = findViewById(R.id.spinnerRole)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvGoRegister: TextView = findViewById(R.id.tvGoRegister)

        tvGoRegister.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

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
                    val s = db.studentDao().login(email, pass)
                    if (s != null) startActivity(Intent(this@LoginActivity, StudentProfileActivity::class.java).putExtra("pk", s.pk))
                    else runOnUiThread { Toast.makeText(this@LoginActivity, "Invalid student credentials", Toast.LENGTH_SHORT).show() }
                } else {
                    val t = db.teacherDao().login(email, pass)
                    if (t != null) startActivity(Intent(this@LoginActivity, TeacherProfileActivity::class.java).putExtra("pk", t.pk))
                    else runOnUiThread { Toast.makeText(this@LoginActivity, "Invalid teacher credentials", Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
}