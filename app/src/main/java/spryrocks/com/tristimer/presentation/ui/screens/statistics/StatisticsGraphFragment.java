package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import android.graphics.Color;
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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.presentation.ui.utils.Converters;
import spryrocks.com.tristimer.presentation.ui.utils.DateAxisValueFormatter;
import spryrocks.com.tristimer.presentation.ui.utils.YAxisValueFormatter;

public class StatisticsGraphFragment extends Fragment {
    private Spinner spinner;
    private Long referenceTimestamp;
    private DatabaseManager databaseManager;
    private View view;
    @Nullable
    private Discipline selectedDiscipline;

    private List<Discipline> disciplines;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistics_graph, container, false);
        databaseManager = new DatabaseManager(this);
        referenceTimestamp = (long)0;
        spinner = view.findViewById(R.id.spinner_graph);
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
        return view;
    }

    public List<Entry> getEntries(List<Result> results) {
        if (results.size() == 0)
            return new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        List<Long> dates = minifyTimestamps(getDates(results));
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getTime() != null)
            entries.add(new Entry((float) dates.get(i), Converters.timeToFloat(results.get(i).getTime())));
        }
        return entries;
    }

    public List<Long> getDates(List<Result> results) {
        List<Long> dates = new ArrayList<>();
        for (Result result : results) {
            dates.add(result.getDate());
        }
        return dates;
    }

    public List<Long> minifyTimestamps(List<Long> timestamps) {
        List<Long> newTs = new ArrayList<>();
        referenceTimestamp = Collections.min(timestamps);
        for (Long timestamp : timestamps) {
            newTs.add(timestamp - referenceTimestamp);
        }
        return newTs;
    }

    private void refreshChart(Discipline discipline) {
        if (discipline == null)
            return;
        selectedDiscipline = discipline;

        TextView textView = view.findViewById(R.id.no_results_text);
        LineChart lineChart = view.findViewById(R.id.line_chart);

        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        if (results.size() == 0) {
            lineChart.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            lineChart.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }

        Description description = new Description();
        description.setText("Session Chart");
        lineChart.setDescription(description);

        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.setViewPortOffsets(10f, 8f, 10f, 70f);

        List<Entry> entries = getEntries(results);
        LineDataSet dataSet = new LineDataSet(entries, "Session");
        dataSet.setColor(Color.WHITE);
        dataSet.setCircleRadius(5);
        dataSet.setCircleColor(getResources().getColor(R.color.colorAccent));
        dataSet.setValueTextColor(Color.YELLOW);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.YELLOW);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new DateAxisValueFormatter(referenceTimestamp));
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxis.setValueFormatter(new YAxisValueFormatter());
        yAxis.setAxisLineColor(Color.YELLOW);
        yAxis.setTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
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
        refreshChart(selectedDiscipline);
    }
}

