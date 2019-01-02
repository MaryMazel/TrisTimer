package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.presentation.ui.utils.Converters;
import spryrocks.com.tristimer.presentation.ui.utils.Formatters;
import spryrocks.com.tristimer.presentation.ui.utils.PrintBitmapBuilder;

public class ResultsFragment extends Fragment {
    private ResultsAdapter adapter;

    DatabaseManager databaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.results_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        adapter = new ResultsAdapter();

        recyclerView.setAdapter(adapter);

        databaseManager = new DatabaseManager(this);

        loadData();

        Toolbar toolbar = view.findViewById(R.id.toolbar_results);
        toolbar.inflateMenu(R.menu.results_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.clearResults:
                    databaseManager.clearSession();
                    loadData();
                    break;
                case R.id.clearSelected:
                    deleteSelectedItems();
                    break;
                case R.id.save_as_pdf:
                    print();
                    break;
            }
            return true;
        });
        return view;
    }

    private List<ResultsAdapter.ResultItem> convertToResultItems(List<Result> results) {
        List<ResultsAdapter.ResultItem> resultItems = new ArrayList<>();
        for (Result result : results) {
            resultItems.add(new ResultsAdapter.ResultItem(result));
        }
        return resultItems;
    }

    private void loadData() {
        List<Result> results = databaseManager.getAllResults();
        Collections.reverse(results);
        List<ResultsAdapter.ResultItem> resultItems = convertToResultItems(results);
        adapter.setItems(resultItems);
    }

    private void deleteSelectedItems() {
        List<Result> results = new ArrayList<>();

        for (ResultsAdapter.ResultItem item : adapter.getItems()) {
            if (item.selected) {
                results.add(item.result);
            }
        }

        databaseManager.deleteSelectedResults(results);

        loadData();
    }

    public void print() {
        Context context = requireContext();

        @Nullable List<Result> results = databaseManager.getAllResults();
        if (results == null)
            return;

        PrintBitmapBuilder builder = new PrintBitmapBuilder(context);
        StringBuilder sb = new StringBuilder();

        sb.append("My 3 by 3 results" + "\n\n");
        for (int i = 0; i < results.size(); i++) {
            sb.append(i + 1).append(". ").append(Converters.timeToString(results.get(i).getTime())).append("  ").append(results.get(i).getScramble()).append("  ").append(Formatters.formatDate(results.get(i).getDate())).append("\n");
        }

        builder.setTextAlign(PrintBitmapBuilder.ReceiptTextAlign.LEFT);
        builder.appendString(sb.toString());

        PrintHelper printHelper = new PrintHelper(context);
        printHelper.printBitmap("Print", builder.build());
    }
}
