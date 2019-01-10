package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.data.Discipline;
import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.domain.DatabaseManager;
import spryrocks.com.tristimer.domain.TimerManager;
import spryrocks.com.tristimer.presentation.ui.utils.ScrambleGenerator;

public class TimerViewModel extends AndroidViewModel {
    public final TimerModel model = new TimerModel();

    private TimerManager timerManager;
    private DatabaseManager databaseManager;
    private Timer mTimer;
    @Nullable
    public Activity activity;

    public TimerViewModel(Application application) {
        super(application);
        timerManager = new TimerManager(application);
        databaseManager = new DatabaseManager(application);

        model.scramble.set(ScrambleGenerator.generateScramble());
        model.timeClick.addCallback(this::timeClick);
        model.deleteClick.addCallback(this::deleteClick);
        model.dnfClick.addCallback(this::dnfClick);
        model.plusTwoClick.addCallback(this::plusTwoClick);
    }

    private void deleteClick() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        databaseManager.deleteLastResult(results.get(results.size() - 1));
    }

    private void dnfClick() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        Result result = results.get(results.size() - 1);
        if (result.getPenalty() == Result.Penalty.PENALTY_OK) {
            databaseManager.setPenaltyDNF(result, Result.Penalty.PENALTY_DNF);
        } else {
            showPenaltyError();
        }

    }

    private void plusTwoClick() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        Result result = results.get(results.size() - 1);
        if (result.getPenalty() == Result.Penalty.PENALTY_OK) {
            databaseManager.setPenaltyPlusTwo(result, Result.Penalty.PENALTY_PLUSTWO);
        } else {
            showPenaltyError();
        }
    }

    public void showPenaltyError() {
        Activity act = activity;
        if (act != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setMessage("You cannot set penalty twice")
                    .setTitle("Penalty Error")
                    .setCancelable(false)
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
        }
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
        saveResult();

        String scramble = ScrambleGenerator.generateScramble();
        model.scramble.set(scramble);
    }

    private Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private void saveResult() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        Result result = new Result(model.timerTime.get(),
                                    model.scramble.get(),
                                    getCurrentDate().getTime(),
                                    selectedDiscipline.getId(),
                                    Result.Penalty.PENALTY_OK);
        databaseManager.insertResult(result);
    }

    List<Discipline> getDataForSpinner() {
        return databaseManager.getAllDisciplines();
    }

    void setSelectDiscipline(@NonNull Discipline discipline) {
        model.selectedDiscipline = discipline;
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
