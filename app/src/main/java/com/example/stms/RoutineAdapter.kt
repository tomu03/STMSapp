package com.example.stms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stms.database.Routine

class RoutineAdapter : RecyclerView.Adapter<RoutineAdapter.VH>() {

    private var items = listOf<Routine>()

    fun submit(list: List<Routine>) {
        items = list
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCourseName: TextView = itemView.findViewById(R.id.tvCourseName)
        val tvDayTime: TextView = itemView.findViewById(R.id.tvDayTime)
        val tvClassName: TextView = itemView.findViewById(R.id.tvClassName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = items[position]
        holder.tvCourseName.text = r.subject
        holder.tvClassName.text = "Class: ${r.className}"
        holder.tvDayTime.text = "${dayName(r.dayOfWeek)} • ${formatTime(r.startMinutes)} - ${formatTime(r.endMinutes)}"
    }

    private fun dayName(day: Int): String {
        val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        return days[day - 1]
    }

    private fun formatTime(mins: Int): String {
        val h = mins / 60
        val m = mins % 60
        return "%02d:%02d".format(h, m)
    }
}