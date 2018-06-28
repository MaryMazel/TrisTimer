package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.domain.TimerManager;

public class TimerViewModel extends AndroidViewModel {
    public final TimerModel model = new TimerModel();
    private TimerManager timerManager;
    private Timer mTimer;
    private MyTimerTask myTimerTask;

    public TimerViewModel(Application application){
        super(application);
        timerManager = new TimerManager(application);
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
        mTimer.schedule(myTimerTask, 0, 50);
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
            handler.post(() -> {
                float time = System.currentTimeMillis() - startTime;
                model.time.set(time);
            });
        }

    }

}
