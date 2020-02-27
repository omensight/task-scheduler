package com.mivipa.productivity.timescheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.WindowDecorActionBar
import com.mivipa.productivity.timescheduler.ui.main.ScheduleListFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ScheduleListFragment.newInstance())
                .commitNow()
        }
        setSupportActionBar(toolbar_main)

    }

}
