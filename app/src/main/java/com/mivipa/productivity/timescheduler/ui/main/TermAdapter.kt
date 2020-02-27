package com.mivipa.productivity.timescheduler.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mivipa.productivity.timescheduler.R
import com.mivipa.productivity.timescheduler.data.model.Term
import kotlinx.android.synthetic.main.item_term.view.*
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TermAdapter(): RecyclerView.Adapter<TermViewHolder>() {


    private val items: MutableList<Term> = ArrayList()

    fun addItems(newItems: List<Term>){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_term, parent, false)
        return TermViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }
}

class TermViewHolder(view: View): RecyclerView.ViewHolder(view){
    fun bind(term: Term) {
        itemView.tv_start.text = convertDateToString(term.start!!)
        itemView.tv_end.text = convertDateToString(term.end!!)
        itemView.tv_duration.text = convertMillisToSimpleTime(term.durationMillis)
    }

    private fun convertDateToString(date: Date): String{
        val dateFormat = DateFormat.getTimeInstance()
        return dateFormat.format(date)
    }

    private fun convertMillisToSimpleTime(millis: Long): String{
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minuteMillis = TimeUnit.HOURS.toMillis(hours)
        val minutes= TimeUnit.MILLISECONDS.toMinutes(millis-minuteMillis)
        val minutesAndHoursMillis = TimeUnit.MINUTES.toMillis(minutes)+TimeUnit.HOURS.toMillis(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis-minutesAndHoursMillis)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}