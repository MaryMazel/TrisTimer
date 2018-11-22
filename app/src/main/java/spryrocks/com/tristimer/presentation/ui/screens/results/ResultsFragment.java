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

import java.util.List;
import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;

public class ResultsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ResultsViewModel viewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View view = inflater.inflate(R.layout.results_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        DatabaseManager databaseManager = new DatabaseManager(this);

        List<Result> results = databaseManager.getAllResults();

        ResultsAdapter adapter = new ResultsAdapter(results);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
