package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Statistics;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.presentation.ui.utils.Converters;

public class StatisticsTextFragment extends Fragment {
    private Spinner spinner;
    @Nullable
    private Discipline selectedDiscipline;
    private List<Discipline> disciplines;
    private DatabaseManager databaseManager;
    TextView best;
    TextView worst;
    TextView sessionMean;
    TextView avg3;
    TextView avg5;
    TextView avg12;
    TextView avg50;
    TextView avg100;
    TextView bestAvg3;
    TextView bestAvg5;
    TextView bestAvg12;
    TextView bestAvg50;
    TextView bestAvg100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_text_fragment, container, false);
        databaseManager = new DatabaseManager(this);

        best = view.findViewById(R.id.best);
        worst = view.findViewById(R.id.worst);
        sessionMean = view.findViewById(R.id.session_mean);
        avg3 = view.findViewById(R.id.avg3);
        avg5 = view.findViewById(R.id.avg5);
        avg12 = view.findViewById(R.id.avg12);
        avg50 = view.findViewById(R.id.avg50);
        avg100 = view.findViewById(R.id.avg100);
        bestAvg3 = view.findViewById(R.id.best_avg3);
        bestAvg5 = view.findViewById(R.id.best_avg5);
        bestAvg12 = view.findViewById(R.id.best_avg12);
        bestAvg50 = view.findViewById(R.id.best_avg50);
        bestAvg100 = view.findViewById(R.id.best_avg100);

        spinner = view.findViewById(R.id.spinner_text);
        initializeSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSelectedDiscipline(disciplines.get(position));
                updateTextViews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void updateTextViews() {
        if (selectedDiscipline == null)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        best.setText(Converters.timeToString(statistics.getBest(), false, true));
        worst.setText(Converters.timeToString(statistics.getWorst(), false, true));
        sessionMean.setText(Converters.timeToString(statistics.getSessionMean(), false, true));
        avg3.setText(Converters.timeToString(statistics.getAvg3(), false, true));
        avg5.setText(Converters.timeToString(statistics.getAvg5(), false, true));
        avg12.setText(Converters.timeToString(statistics.getAvg12(), false, true));
        avg50.setText(Converters.timeToString(statistics.getAvg50(), false, true));
        avg100.setText(Converters.timeToString(statistics.getAvg100(), false, true));
        bestAvg3.setText(Converters.timeToString(statistics.getBestAvg3(), false, true));
        bestAvg5.setText(Converters.timeToString(statistics.getBestAvg5(), false, true));
        bestAvg12.setText(Converters.timeToString(statistics.getBestAvg12(), false, true));
        bestAvg50.setText(Converters.timeToString(statistics.getBestAvg50(), false, true));
        bestAvg100.setText(Converters.timeToString(statistics.getBestAvg100(), false, true));
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

    private void setSelectedDiscipline(Discipline discipline) {
        if (discipline == null)
            return;
        selectedDiscipline = discipline;
    }

}
