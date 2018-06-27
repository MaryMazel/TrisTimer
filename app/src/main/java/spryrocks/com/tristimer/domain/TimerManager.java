package spryrocks.com.tristimer.domain;

import android.content.Context;

import spryrocks.com.tristimer.data.TimeHolder;

public class TimerManager {
    private Context context;

    public TimerManager(Context context) {
        this.context = context;
    }

    public TimeHolder timeHolder = new TimeHolder(context);

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
