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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;

public class ResultsFragment extends Fragment {
    List<Result> results = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ResultsViewModel viewModel = ViewModelProviders.of(this).get(ResultsViewModel.class);
        View view = inflater.inflate(R.layout.results_fragment, container, false);

        setInitialData();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ResultsAdapter adapter = new ResultsAdapter(results);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setInitialData(){
        Locale locale = new Locale("uk");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", locale);

        results.add(new Result("13.00", " this is scramble ", format1.format(Calendar.getInstance().getTime())));
        results.add(new Result("15.00", " this is scramble ", format1.format(Calendar.getInstance().getTime())));
        results.add(new Result("14.00", " this is scramble ", format1.format(Calendar.getInstance().getTime())));
        results.add(new Result("11.00", " this is scramble ", format1.format(Calendar.getInstance().getTime())));
    }
}
