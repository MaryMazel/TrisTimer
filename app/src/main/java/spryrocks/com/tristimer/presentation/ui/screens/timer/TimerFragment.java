package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.databinding.TimerFragmentBinding;

public class TimerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TimerViewModel viewModel = ViewModelProviders.of(this).get(TimerViewModel.class);

        TimerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.timer_fragment, container, false);
        binding.setModel(viewModel.model);

        return binding.getRoot();
    }
}
