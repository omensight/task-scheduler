package com.mivipa.productivity.timescheduler.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mivipa.productivity.timescheduler.R
import com.mivipa.productivity.timescheduler.data.model.Schedule
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.text.SimpleDateFormat

class ScheduleAdapter(private val onScheduleSelectedListener: OnScheduleSelectedListener): RecyclerView.Adapter<ScheduleViewHolder>() {


    private val items: MutableList<Schedule> = ArrayList()

    fun addItems(newItems: List<Schedule>){
        val diffUtil = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }

        })
        items.clear()
        items.addAll(newItems)
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view, onScheduleSelectedListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }

    interface OnScheduleSelectedListener{
        fun onScheduleSelected(schedule: Schedule)
    }
}

class ScheduleViewHolder(private val view: View, private val onScheduleSelectedListener: ScheduleAdapter.OnScheduleSelectedListener): RecyclerView.ViewHolder(view){
    fun bind(schedule: Schedule) {
        val dateFormat = SimpleDateFormat.getDateInstance()
        itemView.tv_schedule_date.text = dateFormat.format(schedule.startDate)
        itemView.tv_schedule_title.text = schedule.name
        view.setOnClickListener {
            onScheduleSelectedListener.onScheduleSelected(schedule)
        }
    }

}