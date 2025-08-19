package com.example.stms.activity

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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stms.database.AppDatabase
import com.example.stms.R
import com.example.stms.database.Student
import com.example.stms.database.Teacher
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinnerRole: Spinner = findViewById(R.id.spinnerRole)
        val etName: EditText = findViewById(R.id.etName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val etUserId: EditText = findViewById(R.id.etUserId)
        val etClass: EditText = findViewById(R.id.etClass)

        val panelStudent: LinearLayout = findViewById(R.id.panelStudent)
        val etStudentDetails: EditText = findViewById(R.id.etStudentDetails)

        val panelTeacher: LinearLayout = findViewById(R.id.panelTeacher)
        val etCourse: EditText = findViewById(R.id.etCourse)
        val etTeacherDetails: EditText = findViewById(R.id.etTeacherDetails)

        val btnRegister: Button = findViewById(R.id.btnRegister)

        spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                val role = spinnerRole.getItemAtPosition(pos).toString()
                panelStudent.visibility = if (role == "Student") View.VISIBLE else View.GONE
                panelTeacher.visibility = if (role == "Teacher") View.VISIBLE else View.GONE
            }
        }

        btnRegister.setOnClickListener {
            val role = spinnerRole.selectedItem.toString()
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            val userId = etUserId.text.toString().trim()
            val cls = etClass.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || userId.isEmpty() || cls.isEmpty()) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }

            lifecycleScope.launch {
                if (role == "Student") {
                    db.studentDao().insert(Student(userId=userId,name=name,email=email,password=pass,className=cls,details=etStudentDetails.text.toString()))
                    runOnUiThread { Toast.makeText(this@RegisterActivity, "Student registered", Toast.LENGTH_SHORT).show(); finish() }
                } else {
                    db.teacherDao().insert(Teacher(userId=userId,name=name,email=email,password=pass,className=cls,course=etCourse.text.toString(),details=etTeacherDetails.text.toString()))
                    runOnUiThread { Toast.makeText(this@RegisterActivity, "Teacher registered", Toast.LENGTH_SHORT).show(); finish() }
                }
            }
        }
    }
}