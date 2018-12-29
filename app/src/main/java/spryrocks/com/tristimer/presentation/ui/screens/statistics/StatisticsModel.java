package spryrocks.com.tristimer.presentation.ui.screens.statistics;

import com.spryrocks.android.modules.ui.mvvm.model.ObservableField;

public class StatisticsModel {
    public final ObservableField<Long> best = new ObservableField<>();
    public final ObservableField<Long> worst = new ObservableField<>();
    public final ObservableField<Long> avg5 = new ObservableField<>();
    public final ObservableField<Long> bestAvg5 = new ObservableField<>();
    public final ObservableField<Long> avg12 = new ObservableField<>();
    public final ObservableField<Long> bestAvg12 = new ObservableField<>();
    public final ObservableField<Long> avg50 = new ObservableField<>();
    public final ObservableField<Long> bestAvg50 = new ObservableField<>();
    public final ObservableField<Long> avg100 = new ObservableField<>();
    public final ObservableField<Long> bestAvg100 = new ObservableField<>();
    public final ObservableField<Long> sessionMean = new ObservableField<>();
}
