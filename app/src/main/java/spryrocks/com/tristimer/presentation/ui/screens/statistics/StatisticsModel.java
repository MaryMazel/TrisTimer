package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import com.spryrocks.android.modules.ui.mvvm.model.ObservableField;

public class StatisticsModel {
    public final ObservableField<Float> best = new ObservableField<>();
    public final ObservableField<Float> worst = new ObservableField<>();
    public final ObservableField<Float> avg5 = new ObservableField<>();
    public final ObservableField<Float> bestAvg5 = new ObservableField<>();
    public final ObservableField<Float> avg12 = new ObservableField<>();
    public final ObservableField<Float> bestAvg12 = new ObservableField<>();
    public final ObservableField<Float> avg50 = new ObservableField<>();
    public final ObservableField<Float> bestAvg50 = new ObservableField<>();
    public final ObservableField<Float> avg100 = new ObservableField<>();
    public final ObservableField<Float> bestAvg100 = new ObservableField<>();
    public final ObservableField<Float> sessionMean = new ObservableField<>();
}
