package com.mivipa.productivity.timescheduler.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mivipa.productivity.timescheduler.R
import com.mivipa.productivity.timescheduler.data.model.Term
import kotlinx.android.synthetic.main.item_term.*
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.text.DateFormat
import java.util.*

class ScheduleFragment: Fragment(R.layout.schedule_fragment) {

    private lateinit var scheduleId: String
    private val job = Job()
    private val backgroundCoroutineScope = CoroutineScope(job + Dispatchers.Default)

    private lateinit var termAdapter: TermAdapter

    lateinit var viewModel: MainViewModel

    companion object{
        const val KEY_SCHEDULE_ID = "SCHEDULE_ID"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        populateView()

        //AddItem click
        bt_add.setOnClickListener {
            val hoursStr = et_term_hours.text.toString().trim()
            val minutesStr = et_term_minutes.text.toString().trim()
            val secondsStr = et_term_seconds.text.toString().trim()
            var duration = 0L
            if (hoursStr.isNotEmpty()){
                duration += hoursStr.toLong().times(1000).times(60).times(60)
            }
            if (minutesStr.isNotEmpty()){
                duration += minutesStr.toLong().times(1000).times(60)
            }
            if (secondsStr.isNotEmpty()){
                duration += secondsStr.toLong().times(1000)
            }
            if (duration > 0){
                val term = Term(
                    id = UUID.randomUUID().toString(),
                    fkScheduleId = scheduleId,
                    durationMillis = duration
                )
                et_term_hours.setText("")
                et_term_minutes.setText("")
                et_term_seconds.setText("")
                backgroundCoroutineScope.launch {
                    viewModel.saveTerm(term)
                }
            }
        }
    }

    private fun populateView() {
        termAdapter = TermAdapter()
        rv_term_list.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = termAdapter
        }
        arguments?.let { it ->
            scheduleId = it.getString(KEY_SCHEDULE_ID,"")
            backgroundCoroutineScope.launch {
                val scheduleFlow = viewModel.getScheduleWithDurations(scheduleId)

                scheduleFlow.collect{ scheduleWithDurations->

                    Log.d("Terms", scheduleWithDurations.terms.toString())
                    val terms = scheduleWithDurations.terms
                    val schedule = scheduleWithDurations.schedule
                    withContext(Dispatchers.Main){
                        tv_title.text = schedule.name
                        tv_start_date.text = DateFormat.getDateTimeInstance().format(schedule.startDate)
                    }
                    val scheduleStart = schedule.startDate
                    val durations = terms.map { it.durationMillis }
                    for (termIndex in terms.indices){
                        val term = terms[termIndex]
                        val currentDuration = term.durationMillis
                        var previousDuration = 0L
                        for (durationIndex in 0 until termIndex){
                            previousDuration+=durations[durationIndex]
                        }
                        Log.d("PreviousDurationTo $termIndex", previousDuration.toString())
                        term.start = Date(scheduleStart.time + previousDuration)
                        term.end = Date(scheduleStart.time + previousDuration + currentDuration)
                    }
                    withContext(Dispatchers.Main){
                        termAdapter.addItems(terms)
                    }
                }
            }
        }

    }
}