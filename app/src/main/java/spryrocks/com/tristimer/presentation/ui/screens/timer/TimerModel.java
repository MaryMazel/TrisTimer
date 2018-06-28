package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableFloat;

import com.spryrocks.android.modules.ui.mvvm.model.OnClickCommand;

public class TimerModel {
    public final ObservableFloat time = new ObservableFloat();
    public final OnClickCommand btStartClick = new OnClickCommand();
    public final OnClickCommand btStopClick = new OnClickCommand();
}
