package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.domain.TimerManager;

public class TimerViewModel extends ViewModel {
    public final TimerModel model = new TimerModel();
    private TimerManager timerManager;
    private Timer mTimer;
    private MyTimerTask myTimerTask;

    public TimerViewModel(Context context){
        timerManager = new TimerManager(context);
        model.btStartClick.addCallback(this::btStartClick);
        model.btStopClick.addCallback(this::btStopClick);
    }

    private void btStartClick() {
        timerManager.startTimer();
        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        myTimerTask = new MyTimerTask();
        mTimer.schedule(myTimerTask, 5);
    }

    private void btStopClick() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        timerManager.stopTimer();
    }

    class MyTimerTask extends TimerTask {
        private final Handler handler = new Handler();
        private long startTime = timerManager.getTime();

        @Override
        public void run() {
            long time = System.currentTimeMillis() - startTime;

            handler.post(() -> model.time.set((float)time));
        }
    }

}
