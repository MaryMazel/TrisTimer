package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableField;

import com.spryrocks.android.modules.ui.mvvm.model.OnClickCommand;

public class TimerModel {
    //public final ObservableField<String> time = new ObservableField<>();
    public final ObservableField<String> scramble = new ObservableField<>();
    public final OnClickCommand timeClick = new OnClickCommand();

    public ObservableField<Long> timerTime = new ObservableField<>();
}
