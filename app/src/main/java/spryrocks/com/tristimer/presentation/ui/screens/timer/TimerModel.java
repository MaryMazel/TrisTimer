package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.spryrocks.android.modules.ui.mvvm.model.OnClickCommand;

import java.util.ArrayList;

public class TimerModel {
    public final ObservableField<Float> time = new ObservableField<>();
    public final OnClickCommand btStartClick = new OnClickCommand();
    public final OnClickCommand btStopClick = new OnClickCommand();
}
