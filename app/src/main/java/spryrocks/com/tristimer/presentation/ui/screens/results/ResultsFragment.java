package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import spryrocks.com.tristimer.R;

public class ResultsFragment extends Fragment {
    List<String> results = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ResultsViewModel viewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View view = inflater.inflate(R.layout.results_fragment, container, false);
        setInitialData();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ResultsAdapter adapter = new ResultsAdapter(requireContext(), results);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setInitialData(){
        results.add("1234");
        results.add("kfjherf");
        results.add("sfjv");
        results.add("flkrkf");
    }
}
