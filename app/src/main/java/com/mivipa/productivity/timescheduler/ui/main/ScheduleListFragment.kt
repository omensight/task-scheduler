package com.mivipa.productivity.timescheduler.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mivipa.productivity.timescheduler.R
import com.mivipa.productivity.timescheduler.data.model.Schedule
import kotlinx.android.synthetic.main.schedule_list_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class ScheduleListFragment : Fragment(), ScheduleAdapter.OnScheduleSelectedListener {
    private val job = Job()
    private val backgroundCoroutineScope = CoroutineScope(job + Dispatchers.Default)
    private val scheduleAdapter = ScheduleAdapter(this)

    companion object {
        fun newInstance() = ScheduleListFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.schedule_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loadDurations()
        fab_add_schedule.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, CreateScheduleFragment()).addToBackStack(null).commit()
        }
    }

    private fun loadDurations(){
        rv_schedule_list.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = scheduleAdapter
        }
        backgroundCoroutineScope.launch {
            viewModel.schedulesList.collect{
                Log.d("Schedules", it.toString())
                withContext(Dispatchers.Main){
                    scheduleAdapter.addItems(it)
                }
            }
        }
    }

    override fun onScheduleSelected(schedule: Schedule) {
        val fragment = ScheduleFragment()
        val args = Bundle()
        args.putString(ScheduleFragment.KEY_SCHEDULE_ID, schedule.id)
        fragment.arguments = args
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
    }

}
