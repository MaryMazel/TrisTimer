package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.databinding.ResultsFragmentBinding;

public class ResultsFragment extends Fragment {
    public final ResultsViewModel viewModel = new ResultsViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ResultsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.results_fragment, container, false);
        binding.setModel(viewModel.model);
        return binding.getRoot();
    }
}
