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
import spryrocks.com.tristimer.presentation.ui.utils.scrambles.Scramble2by2Generator;
import spryrocks.com.tristimer.presentation.ui.utils.scrambles.Scramble3by3Generator;
import spryrocks.com.tristimer.presentation.ui.utils.scrambles.Scramble4by4Generator;
import spryrocks.com.tristimer.presentation.ui.utils.scrambles.ScramblePyraminxGenerator;
import spryrocks.com.tristimer.presentation.ui.utils.scrambles.ScrambleSkewbGenerator;

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

        model.scramble.set(getScramble());
        model.timeClick.addCallback(this::timeClick);
        model.deleteClick.addCallback(this::deleteClick);
        model.dnfClick.addCallback(this::dnfClick);
        model.plusTwoClick.addCallback(this::plusTwoClick);
    }

    public void setScramble() {
        model.scramble.set(getScramble());
    }
    private String getScramble() {
        String scramble = null;
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return Scramble3by3Generator.generateScramble();
        switch (selectedDiscipline.getName()) {
            case "3x3":
                scramble = Scramble3by3Generator.generateScramble();
                break;
            case "3x3 OH":
                scramble = Scramble3by3Generator.generateScramble();
                break;
            case "3x3 BLD":
                scramble = Scramble3by3Generator.generateScramble();
                break;
            case "2x2":
                scramble = Scramble2by2Generator.generateScramble();
                break;
            case "4x4":
                scramble = Scramble4by4Generator.generateScramble();
                break;
            case "Skewb":
                scramble = ScrambleSkewbGenerator.generateScramble();
                break;
            case "Pyraminx":
                scramble = ScramblePyraminxGenerator.generateScramble();
                break;
            default:
                scramble = Scramble3by3Generator.generateScramble();
                break;
        }
        return scramble;
    }

    private void deleteClick() {
        Activity act = activity;
        if (act != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setMessage("Are you sure you want to delete the last result?")
                    .setTitle("Confirm action")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        Discipline selectedDiscipline = model.selectedDiscipline;
                        if (selectedDiscipline == null)
                            return;
                        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
                        databaseManager.deleteLastResult(results.get(results.size() - 1));
                    })
            .setNegativeButton("Cancel", null);
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void dnfClick() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        Result result = results.get(results.size() - 1);
        if (result.getPenalty() == Result.Penalty.PENALTY_OK) {
            Activity act = activity;
            if (act != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage("Are you sure you want to set DNF to the last result?")
                        .setTitle("Confirm action")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> databaseManager.setPenaltyDNF(result, Result.Penalty.PENALTY_DNF))
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
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
            Activity act = activity;
            if (act != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage("Are you sure you want to set +2 to the last result?")
                        .setTitle("Confirm action")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> {
                            databaseManager.setPenaltyPlusTwo(result, Result.Penalty.PENALTY_PLUSTWO);
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            showPenaltyError();
        }
    }

    private void showPenaltyError() {
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

        String scramble = getScramble();
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
