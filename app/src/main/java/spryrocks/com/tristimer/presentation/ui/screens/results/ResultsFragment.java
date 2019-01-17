package spryrocks.com.tristimer.presentation.ui.screens.results;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.presentation.ui.utils.Converters;
import spryrocks.com.tristimer.presentation.ui.utils.Formatters;
import spryrocks.com.tristimer.presentation.ui.utils.PrintBitmapBuilder;

public class ResultsFragment extends Fragment {
    private ResultsAdapter adapter;
    List<Discipline> disciplines;
    @Nullable
    Discipline selectedDiscipline;
    DatabaseManager databaseManager;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.results_fragment, container, false);
        databaseManager = new DatabaseManager(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        adapter = new ResultsAdapter();

        recyclerView.setAdapter(adapter);
        spinner = view.findViewById(R.id.spinner_results);
        initializeSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedDiscipline(disciplines.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar_results);
        toolbar.inflateMenu(R.menu.results_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.clearResults:
                    clearSession();
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
        Discipline selectedDiscipline = this.selectedDiscipline;
        List<Result> results;
        if (selectedDiscipline != null) {
            results = databaseManager.getAllResults(selectedDiscipline.getId());
        } else {
            results = new ArrayList<>();
        }
        Collections.reverse(results);
        List<ResultsAdapter.ResultItem> resultItems = convertToResultItems(results);
        adapter.setItems(resultItems);
    }

    private void setSelectedDiscipline(Discipline discipline) {
        this.selectedDiscipline = discipline;
        loadData();
    }

    private void clearSession() {
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        if (results.size() == 0) {
            alertNoResults("There is no results yet");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to CLEAR SESSION?")
                .setTitle("Confirm action")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    databaseManager.clearSession(selectedDiscipline.getId());
                    databaseManager.setAlltoNull(selectedDiscipline.getId());
                    loadData();
                })
                .setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteSelectedItems() {
        if (selectedDiscipline == null)
            return;
        List<Result> results1 = databaseManager.getAllResults(selectedDiscipline.getId());
        if (results1.size() == 0) {
            alertNoResults("There is no results yet");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to delete selected items?")
                .setTitle("Confirm action")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    List<Result> results = new ArrayList<>();
                    for (ResultsAdapter.ResultItem item : adapter.getItems()) {
                        if (item.selected) {
                            results.add(item.result);
                        }
                    }
                    if (results.size() == 0) {
                        alertNoResults("You didn't select items");
                        return;
                    }
                    databaseManager.deleteSelectedResults(results);
                    loadData();
                })
                .setNegativeButton("Cancel", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alertNoResults(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(message)
                .setTitle("Warning")
                .setCancelable(false)
                .setPositiveButton("Ok", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initializeSpinner() {
        disciplines = databaseManager.getAllDisciplines();
        List<String> data = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            data.add(discipline.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.disciplines_spinner_item, data.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void print() {
        Context context = requireContext();

        Discipline selectedDiscipline = this.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        @Nullable List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        if ((results != null ? results.size() : 0) == 0) {
            alertNoResults("There is no results yet");
            return;
        }

        PrintBitmapBuilder builder = new PrintBitmapBuilder(context);
        StringBuilder sb = new StringBuilder();

        sb.append("My ").append(selectedDiscipline.getName()).append(" results").append("\n\n");
        for (int i = 0; i < results.size(); i++) {
            boolean penalty = false;
            if (results.get(i).getPenalty() == Result.Penalty.PENALTY_DNF) {
                penalty = true;
            }
            sb.append(i + 1).append(". ").append(Converters.timeToString(results.get(i).getTime(), penalty, false)).append("  ").append(results.get(i).getScramble()).append("  ").append(Formatters.formatDate(results.get(i).getDate())).append("\n");
        }

        builder.setTextAlign(PrintBitmapBuilder.ReceiptTextAlign.LEFT);
        builder.appendString(sb.toString());

        PrintHelper printHelper = new PrintHelper(context);
        printHelper.printBitmap("Print", builder.build());
    }
}
