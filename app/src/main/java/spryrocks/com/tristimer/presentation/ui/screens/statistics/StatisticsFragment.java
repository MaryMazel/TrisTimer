package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.databinding.StatisticsFragmentBinding;

public class StatisticsFragment extends Fragment {
    public final StatisticsViewModel viewModel = new StatisticsViewModel();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StatisticsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.statistics_fragment, container, false);
        binding.setModel(viewModel.model);
        return binding.getRoot();
    }
}
