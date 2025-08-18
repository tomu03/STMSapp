package com.example.stms

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TeacherProfileActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_profile)

        val pk = intent.getIntExtra("pk", -1)
        if (pk == -1) finish()

        val img = findViewById<ImageView>(R.id.imgTeacher)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvId = findViewById<TextView>(R.id.tvId)
        val tvClass = findViewById<TextView>(R.id.tvClass)
        val tvCourse = findViewById<TextView>(R.id.tvCourse)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val btnLogout = findViewById<TextView>(R.id.btnLogout)

        lifecycleScope.launch {
            val teacher = db.teacherDao().getByPk(pk)
            teacher?.let {
                runOnUiThread {
                    tvName.text = it.name
                    tvId.text = "ID: ${it.userId}"
                    tvClass.text = "Class: ${it.className}"
                    tvCourse.text = "Course: ${it.course}"
                    tvResult.text = "Result: ${it.result ?: "N/A"}"
                    tvDetails.text = it.details ?: "No details"

                    if (!it.imageUri.isNullOrBlank()) {
                        img.setImageURI(Uri.parse(it.imageUri))
                    }
                }
            }
        }
        btnLogout.setOnClickListener    {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}