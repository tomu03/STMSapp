package com.example.stms

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var spinnerRole: Spinner
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etUserId: EditText
    private lateinit var etClass: EditText

    // Student
    private lateinit var panelStudent: LinearLayout
    private lateinit var etStudentResult: EditText
    private lateinit var etStudentNote: EditText
    private lateinit var etStudentDetails: EditText

    // Teacher
    private lateinit var panelTeacher: LinearLayout
    private lateinit var etCourse: EditText
    private lateinit var etTeacherResult: EditText
    private lateinit var etTeacherDetails: EditText

    private lateinit var btnRegister: Button
    private lateinit var tvGoLogin : TextView

    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        spinnerRole = findViewById(R.id.spinnerRole)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etUserId = findViewById(R.id.etUserId)
        etClass = findViewById(R.id.etClass)

        panelStudent = findViewById(R.id.panelStudent)
        etStudentResult = findViewById(R.id.etStudentResult)
        etStudentNote = findViewById(R.id.etStudentNote)
        etStudentDetails = findViewById(R.id.etStudentDetails)

        panelTeacher = findViewById(R.id.panelTeacher)
        etCourse = findViewById(R.id.etCourse)
        etTeacherResult = findViewById(R.id.etTeacherResult)
        etTeacherDetails = findViewById(R.id.etTeacherDetails)

        btnRegister = findViewById(R.id.btnRegister)

        tvGoLogin= findViewById(R.id.tvGoLogin)


        // Toggle sections based on role
        spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val role = spinnerRole.getItemAtPosition(position).toString()
                if (role == "Student") {
                    panelStudent.visibility = View.VISIBLE
                    panelTeacher.visibility = View.GONE
                } else {
                    panelStudent.visibility = View.GONE
                    panelTeacher.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        btnRegister.setOnClickListener {
            val role = spinnerRole.selectedItem.toString()
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            val userId = etUserId.text.toString().trim()
            val cls = etClass.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || userId.isEmpty() || cls.isEmpty()) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (role == "Student") {
                    val student = Student(
                        userId = userId,
                        name = name,
                        email = email,
                        password = pass,
                        className = cls,
                        result = etStudentResult.text.toString().ifBlank { null },
                        note = etStudentNote.text.toString().ifBlank { null },
                        details = etStudentDetails.text.toString().ifBlank { null },
                        imageUri = null
                    )
                    db.studentDao().insert(student)
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Student registered", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    val teacher = Teacher(
                        userId = userId,
                        name = name,
                        email = email,
                        password = pass,
                        className = cls,
                        course = etCourse.text.toString().ifBlank { "N/A" },
                        result = etTeacherResult.text.toString().ifBlank { null },
                        details = etTeacherDetails.text.toString().ifBlank { null },
                        imageUri = null
                    )
                    db.teacherDao().insert(teacher)
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Teacher registered", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        tvGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}