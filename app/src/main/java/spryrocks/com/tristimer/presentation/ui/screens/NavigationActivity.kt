package spryrocks.com.tristimer.presentation.ui.screens

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import spryrocks.com.tristimer.R
import spryrocks.com.tristimer.databinding.NavigationActivityBinding

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: NavigationActivityBinding = DataBindingUtil.setContentView(this, R.layout.navigation_activity)

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = TimerFragment()
        addFragment(fragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.results_item -> {
                val fragment = ResultsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.statistics_item -> {
                val fragment = StatisticsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.timer_item -> {
                val fragment = TimerFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment, null)
                .addToBackStack(null)
                .commit()
    }



}
