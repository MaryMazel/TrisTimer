package spryrocks.com.tristimer.presentation.ui.screens

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import spryrocks.com.tristimer.R
import spryrocks.com.tristimer.databinding.NavigationActivityBinding
import android.arch.lifecycle.ViewModelProviders
import com.spryrocks.android.modules.utils.Actions.Action1
import spryrocks.com.tristimer.presentation.ui.screens.NavigationModel.MenuItem.*
import spryrocks.com.tristimer.presentation.ui.screens.results.ResultsFragment
import spryrocks.com.tristimer.presentation.ui.screens.statistics.StatisticsFragment
import spryrocks.com.tristimer.presentation.ui.screens.timer.TimerFragment

class NavigationActivity : AppCompatActivity() {
    private var viewModel: NavigationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(NavigationViewModel::class.java)
        this.viewModel = viewModel

        val binding: NavigationActivityBinding = DataBindingUtil.setContentView(this, R.layout.navigation_activity)
        binding.view = this
        binding.model = viewModel.model

        viewModel.model.menuItem.addCallback(onMenuItemChanged)

        addFragment(TimerFragment(), false)
    }

    val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        viewModel!!.model.menuItem.set(when (item.itemId) {
            R.id.results_item -> RESULTS
            R.id.statistics_item -> STATISTICS
            R.id.timer_item -> TIMER
            else -> null
        })
        true
    }

    private val onMenuItemChanged = Action1<NavigationModel.MenuItem> { menuItem ->
        if (menuItem == null)
            return@Action1
        addFragment(when (menuItem) {
            RESULTS -> ResultsFragment()
            STATISTICS -> StatisticsFragment()
            TIMER -> TimerFragment()
        })
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment, null)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }
}
