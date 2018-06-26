package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class TimerModel {
    @Nullable
    public final ObservableField<Float> time = new ObservableField<>();
    public final ObservableField<ArrayList<Float>> allResults = new ObservableField<>();
    public final ArrayList<Float> results = new ArrayList<>();

    public void addTime(Float time){
        results.add(time);
        allResults.set(results);
    }
}
