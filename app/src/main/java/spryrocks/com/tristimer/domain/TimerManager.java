package spryrocks.com.tristimer.domain;

import android.content.Context;

import spryrocks.com.tristimer.data.TimeHolder;

public class TimerManager {
    private final TimeHolder timeHolder;

    public TimerManager(Context context) {
        timeHolder = new TimeHolder(context);
    }

    public void startTimer() {
        timeHolder.setTime(System.currentTimeMillis());
    }

    public void stopTimer() {
        timeHolder.setTime(null);
    }

    public Long getTime() {
        return timeHolder.getTime();
    }
}
