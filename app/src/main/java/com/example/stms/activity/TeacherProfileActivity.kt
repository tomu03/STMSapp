package com.example.stms.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stms.DAYS
import com.example.stms.database.AppDatabase
import com.example.stms.R
import com.example.stms.RoutineAdapter
import com.example.stms.labelToDayIndex
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class TeacherProfileActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }
    private val adapter = RoutineAdapter()
    private var teacherId: String = ""
    private var className: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_profile)

        val pk = intent.getIntExtra("pk", -1); if (pk == -1) finish()

        val img: ImageView = findViewById(R.id.imgTeacher)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvId: TextView = findViewById(R.id.tvId)
        val tvClass: TextView = findViewById(R.id.tvClass)
        val tvCourse: TextView = findViewById(R.id.tvCourse)
        val tvDetails: TextView = findViewById(R.id.tvDetails)
        val btnAdd: Button = findViewById(R.id.btnAddRoutine)
        val btnLogout: Button = findViewById(R.id.btnLogout)
        val tab: TabLayout = findViewById(R.id.tabDays)
        val rv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvRoutine)
        rv.layoutManager = LinearLayoutManager(this); rv.adapter = adapter

        DAYS.forEach { tab.addTab(tab.newTab().setText(it)) }

        lifecycleScope.launch {
            val t = db.teacherDao().getByPk(pk) ?: return@launch
            teacherId = t.userId; className = t.className
            runOnUiThread {
                tvName.text = t.name
                tvId.text = "ID: ${t.userId}"
                tvClass.text = "Class: ${t.className}"
                tvCourse.text = "Course: ${t.course}"
                tvDetails.text = t.details ?: ""
                t.imageUri?.let { img.setImageURI(Uri.parse(it)) }
            }
            loadDay(1)
        }

        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(t: TabLayout.Tab) { loadDay(labelToDayIndex(t.position)) }
            override fun onTabUnselected(t: TabLayout.Tab) {}
            override fun onTabReselected(t: TabLayout.Tab) { loadDay(labelToDayIndex(t.position)) }
        })

        btnAdd.setOnClickListener {
            startActivity(Intent(this, RoutineEditorActivity::class.java)
                .putExtra("prefClass", className)
                .putExtra("prefTeacherId", teacherId))
        }
        btnLogout.setOnClickListener { finish() }
    }

    private fun loadDay(dayIndex: Int) = lifecycleScope.launch {
        val mine = db.routineDao().forTeacher(teacherId)
        val filtered = mine.filter { it.dayOfWeek == dayIndex }
        runOnUiThread { adapter.submit(filtered) }
    }
}