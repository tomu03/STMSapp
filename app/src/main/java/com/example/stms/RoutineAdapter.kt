package com.example.stms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stms.database.Routine

class RoutineAdapter : RecyclerView.Adapter<RoutineAdapter.VH>() {
    private val data = mutableListOf<Routine>()

    fun submit(list: List<Routine>) {
        data.clear(); data.addAll(list); notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        return VH(v)
    }
    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val tvSubject: TextView = v.findViewById(R.id.tvSubject)
        private val tvTimeRoom: TextView = v.findViewById(R.id.tvTimeRoom)
        private val tvCourseDetails: TextView = v.findViewById(R.id.tvCourseDetails)

        fun bind(r: Routine) {
            tvSubject.text = r.subject
            val time = "${formatMinutes(r.startMinutes)} - ${formatMinutes(r.endMinutes)}"
            val room = r.room?.let { " | Room $it" } ?: ""
            tvTimeRoom.text = "$time$room"
            tvCourseDetails.text = r.courseDetails ?: ""
        }
    }
}

// utils
fun formatMinutes(m: Int): String {
    val h = m / 60
    val mm = m % 60
    return "%02d:%02d".format(h, mm)
}