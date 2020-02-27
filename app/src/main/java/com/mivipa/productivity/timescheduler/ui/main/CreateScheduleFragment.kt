package com.mivipa.productivity.timescheduler.ui.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mivipa.productivity.timescheduler.R
import com.mivipa.productivity.timescheduler.data.model.Schedule
import kotlinx.android.synthetic.main.create_schedule_fragment.*
import kotlinx.android.synthetic.main.item_schedule.*
import kotlinx.android.synthetic.main.schedule_list_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CreateScheduleFragment: Fragment() {
    private lateinit var viewModel: MainViewModel


    private val job = Job()
    private val backgroundCoroutineScope = CoroutineScope(job+Dispatchers.Default)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.create_schedule_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_schedule, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_validate_schedule->{
                validate()
            }
        }
        return false
    }

    private fun validate() {
        val scheduleName = et_schedule_name.text.toString().trim()
        val calendar = Calendar.getInstance()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            calendar.set(Calendar.HOUR, time_picker_start.currentHour)
            calendar.set(Calendar.MINUTE, time_picker_start.currentMinute)
        }else{
            calendar.set(Calendar.HOUR, time_picker_start.hour)
            calendar.set(Calendar.MINUTE, time_picker_start.hour)
        }
        if (scheduleName.isNotEmpty()){
            val schedule = Schedule(
                id = UUID.randomUUID().toString(),
                name = scheduleName,
                startDate = calendar.time
            )
            backgroundCoroutineScope.launch {
                viewModel.saveSchedule(schedule)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }else{
            til_schedule_name.error = getString(R.string.error_field_required)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }
}
