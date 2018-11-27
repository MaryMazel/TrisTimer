package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;

public class StatisticsFragment extends Fragment {
    private DatabaseManager databaseManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*StatisticsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.statistics_fragment, container, false);
        binding.setModel(viewModel.model);*/
        View view = inflater.inflate(R.layout.statistics_graph, container, false);

        databaseManager = new DatabaseManager(this);
        GraphView graph = view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataToGraph());
        graph.addSeries(series);

        series.setColor(Color.parseColor("#01579b"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setThickness(4);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(2); // only 4 because of the space
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setScalable(true);

        return view;
    }

    private DataPoint[] getDataToGraph() {
        List<Result> results = databaseManager.getAllResults();
        DataPoint dataPoint[] = new DataPoint[results.size()];
        for (int i = 0; i < results.size(); i++) {
            dataPoint[i] = new DataPoint(convertToDate(results.get(i).getDate()), convertToDouble(results.get(i).getTime()));
        }
        return dataPoint;
    }

    private Double convertToDouble(String result) {
        Double time = null;
        try {
            time = Double.parseDouble(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    private Date convertToDate(String dateString){
        Locale locale = new Locale("uk");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM", locale);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
}
