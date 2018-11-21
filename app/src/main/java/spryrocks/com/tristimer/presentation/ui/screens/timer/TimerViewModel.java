package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.domain.TimerManager;
import spryrocks.com.tristimer.presentation.ui.utils.ScrambleGenerator;

public class TimerViewModel extends AndroidViewModel {
    public final TimerModel model = new TimerModel();
    private TimerManager timerManager;
    private Timer mTimer;

    public TimerViewModel(Application application) {
        super(application);
        timerManager = new TimerManager(application);
        model.scramble.set(ScrambleGenerator.generateScramble());
        model.timeClick.addCallback(this::timeClick);
    }

    private void timeClick() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            timerManager.stopTimer();
            String scramble = ScrambleGenerator.generateScramble();
            model.scramble.set(scramble);
        } else {
            timerManager.startTimer();
            mTimer = new Timer();
            MyTimerTask myTimerTask = new MyTimerTask();
            mTimer.schedule(myTimerTask, 0, 10);
        }
    }

    class MyTimerTask extends TimerTask {
        private final Handler handler = new Handler();
        private long startTime = timerManager.getTime();

        @Override
        public void run() {
            float time = System.currentTimeMillis() - startTime;
            handler.post(() -> model.time.set(time));
        }
    }
}
