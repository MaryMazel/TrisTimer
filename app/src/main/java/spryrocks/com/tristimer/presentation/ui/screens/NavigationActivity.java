package spryrocks.com.tristimer.presentation.ui.screens;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.spryrocks.android.modules.utils.Actions;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.databinding.NavigationActivityBinding;
import spryrocks.com.tristimer.presentation.ui.screens.results.ResultsFragment;
import spryrocks.com.tristimer.presentation.ui.screens.statistics.StatisticsFragment;
import spryrocks.com.tristimer.presentation.ui.screens.timer.TimerFragment;

public class NavigationActivity extends AppCompatActivity {
    public NavigationViewModel viewModel = new NavigationViewModel();

    FragmentTransaction transaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        NavigationActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.navigation_activity);
        binding.setView(this);
        binding.setModel(viewModel.model);

        viewModel.model.menuItem.addCallback(onMenuItemChanged);

        addFragment(new TimerFragment(), false);
    }

    public final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.results_item:
                    viewModel.model.menuItem.set(NavigationModel.MenuItem.RESULTS);
                    break;
                case R.id.statistics_item:
                    viewModel.model.menuItem.set(NavigationModel.MenuItem.STATISTICS);
                    break;
                case R.id.timer_item:
                    viewModel.model.menuItem.set(NavigationModel.MenuItem.TIMER);
                    break;
                default:
                    viewModel.model.menuItem.set(null);
            }
            return true;
        }
    };

    private final Actions.Action1<NavigationModel.MenuItem> onMenuItemChanged = new Actions.Action1<NavigationModel.MenuItem>() {
        @Override
        public void run(NavigationModel.MenuItem menuItem) {
            if (menuItem == null)
                return;
            else {
                switch (menuItem) {
                    case RESULTS:
                        addFragment(new ResultsFragment(), true);
                        break;
                    case STATISTICS:
                        addFragment(new StatisticsFragment(), true);
                        break;
                    case TIMER:
                        addFragment(new TimerFragment(), true);
                        break;
                }
            }
        }
    };

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, null);
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
