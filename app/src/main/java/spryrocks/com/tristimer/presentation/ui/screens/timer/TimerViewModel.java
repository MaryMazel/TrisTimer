package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.domain.TimerManager;
import spryrocks.com.tristimer.presentation.ui.utils.ScrambleGenerator;

public class TimerViewModel extends AndroidViewModel {
    public final TimerModel model = new TimerModel();

    private TimerManager timerManager;
    private DatabaseManager databaseManager;
    private Timer mTimer;

    public TimerViewModel(Application application) {
        super(application);
        timerManager = new TimerManager(application);
        databaseManager = new DatabaseManager(application);
        model.scramble.set(ScrambleGenerator.generateScramble());
        model.timeClick.addCallback(this::timeClick);
    }

    private void timeClick() {
        if (mTimer != null) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        timerManager.startTimer();
        mTimer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        mTimer.schedule(myTimerTask, 0, 10);
    }

    private void stopTimer() {
        mTimer.cancel();
        mTimer = null;
        timerManager.stopTimer();
        //model.time.set(Converters.timeToString(model.timerTime.get()));
        saveResult();

        String scramble = ScrambleGenerator.generateScramble();
        model.scramble.set(scramble);
    }

    private Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private void saveResult() {
        Result result = new Result(model.timerTime.get(), model.scramble.get(), getCurrentDate().getTime());
        databaseManager.insertResult(result);
    }

    class MyTimerTask extends TimerTask {
        private final Handler handler = new Handler();
        private long startTime = timerManager.getTime();
        @Override
        public void run() {
            long time = System.currentTimeMillis() - startTime;
            handler.post(() -> model.timerTime.set(time));
        }
    }
}
