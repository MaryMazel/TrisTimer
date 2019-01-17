package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.spryrocks.android.modules.ui.mvvm.model.OnClickCommand;

import spryrocks.com.tristimer.data.entities.Discipline;

public class TimerModel {
    public final ObservableBoolean isTimerRunning = new ObservableBoolean();
    @Nullable
    public Discipline selectedDiscipline;
    public final ObservableField<String> scramble = new ObservableField<>();
    public final OnClickCommand timeClick = new OnClickCommand();
    public final OnClickCommand deleteClick = new OnClickCommand();
    public final OnClickCommand plusTwoClick = new OnClickCommand();
    public final OnClickCommand dnfClick = new OnClickCommand();
    public ObservableField<Long> timerTime = new ObservableField<>();
}
