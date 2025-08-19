package com.example.stms.activity

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stms.DAYS
import com.example.stms.R
import com.example.stms.database.AppDatabase
import com.example.stms.database.Routine
import kotlinx.coroutines.launch
import java.util.Calendar

class RoutineEditorActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_editor)

        val etClass: EditText = findViewById(R.id.etClassName)
        val etTeacherId: EditText = findViewById(R.id.etTeacherId)
        val spDay: Spinner = findViewById(R.id.spDay)
        val etStart: EditText = findViewById(R.id.etStart)
        val etEnd: EditText = findViewById(R.id.etEnd)
        val etSubject: EditText = findViewById(R.id.etSubject)
        val etRoom: EditText = findViewById(R.id.etRoom)
        val etDetails: EditText = findViewById(R.id.etCourseDetails)
        val btnSave: Button = findViewById(R.id.btnSaveRoutine)

        // Pre-fill if launched from teacher screen
        etClass.setText(intent.getStringExtra("prefClass") ?: "")
        etTeacherId.setText(intent.getStringExtra("prefTeacherId") ?: "")

        spDay.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, DAYS)

        fun timePick(target: EditText) {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                this, { _, h, m -> target.setText("%02d:%02d".format(h, m)) },
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
            ).show()
        }
        etStart.setOnClickListener { timePick(etStart) }
        etEnd.setOnClickListener { timePick(etEnd) }

        btnSave.setOnClickListener {
            val cls = etClass.text.toString().trim()
            val teach = etTeacherId.text.toString().trim().ifEmpty { null }
            val dayIndex = spDay.selectedItemPosition + 1
            val startMin = parseToMinutes(etStart.text.toString())
            val endMin = parseToMinutes(etEnd.text.toString())
            val subject = etSubject.text.toString().trim()
            if (cls.isEmpty() || startMin == null || endMin == null || subject.isEmpty()) {
                Toast.makeText(this, "Fill class, start/end time and subject", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                db.routineDao().insert(
                    Routine(
                        className = cls,
                        teacherId = teach,
                        dayOfWeek = dayIndex,
                        startMinutes = startMin,
                        endMinutes = endMin,
                        subject = subject,
                        room = etRoom.text.toString().ifBlank { null },
                        courseDetails = etDetails.text.toString().ifBlank { null }
                    )
                )
                runOnUiThread {
                    Toast.makeText(this@RoutineEditorActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun parseToMinutes(hhmm: String): Int? {
        val parts = hhmm.split(":")
        if (parts.size != 2) return null
        val h = parts[0].toIntOrNull() ?: return null
        val m = parts[1].toIntOrNull() ?: return null
        return h * 60 + m
    }
}