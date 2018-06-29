package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableField;
import android.databinding.ObservableFloat;

import com.spryrocks.android.modules.ui.mvvm.model.OnClickCommand;

public class TimerModel {
    public final ObservableFloat time = new ObservableFloat();
    public final ObservableField<String> scramble = new ObservableField<>();
    public final OnClickCommand timeClick = new OnClickCommand();
}
