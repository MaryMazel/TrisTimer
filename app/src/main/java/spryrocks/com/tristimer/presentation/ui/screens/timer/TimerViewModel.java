package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Result;
import spryrocks.com.tristimer.data.entities.Statistics;
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

        model.penalty.set(false);
        model.isTimerRunning.set(false);
        model.scramble.set(getScramble());
        model.timeClick.addCallback(this::timeClick);
        model.deleteClick.addCallback(this::deleteClick);
        model.dnfClick.addCallback(this::dnfClick);
        model.plusTwoClick.addCallback(this::plusTwoClick);
    }

    void setScramble() {
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
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Result> results = databaseManager.getAllResults(selectedDiscipline.getId());
        if (results.size() == 0) {
            alertNoResults("You have no results yet. You can't delete anything now");
            return;
        }
        Activity act = activity;
        if (act != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setMessage("Are you sure you want to delete the last result?")
                    .setTitle("Confirm action")
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        databaseManager.deleteLastResult(results.get(results.size() - 1));
                        model.timerTime.set(null);
                        model.penalty.set(false);
                        updateStats();
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
        if (results.size() == 0) {
            alertNoResults("You have no results yet. You can't set penalty DNF now");
            return;
        }
        Result result = results.get(results.size() - 1);
        if (result.getPenalty() == Result.Penalty.PENALTY_OK) {
            Activity act = activity;
            if (act != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setMessage("Are you sure you want to set DNF to the last result?")
                        .setTitle("Confirm action")
                        .setCancelable(false)
                        .setPositiveButton("Ok", (dialog, which) -> {
                            databaseManager.setPenaltyDNF(result, Result.Penalty.PENALTY_DNF);
                            model.penalty.set(true);
                            model.timerTime.set(null);
                            updateStats();
                        })
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
        if (results.size() == 0) {
            alertNoResults("You have no results yet. You can't set penalty +2 now");
            return;
        }
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
                            List<Result> newResults = databaseManager.getAllResults(selectedDiscipline.getId());
                            Result lastResult = newResults.get(newResults.size() - 1);
                            model.timerTime.set(lastResult.getTime());
                            updateStats();
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        } else {
            showPenaltyError();
        }
    }

    private void alertNoResults(String message) {
        Activity act = activity;
        if (act != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setMessage(message)
                    .setTitle("Warning")
                    .setCancelable(false)
                    .setPositiveButton("Ok", null);
            AlertDialog alert = builder.create();
            alert.show();
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
        model.isTimerRunning.set(true);
        mTimer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        mTimer.schedule(myTimerTask, 0, 10);
    }

    private void stopTimer() {
        mTimer.cancel();
        mTimer = null;
        timerManager.stopTimer();
        model.isTimerRunning.set(false);
        model.penalty.set(false);
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
        updateStats();
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


    private void updateStats() {
        updateBest();
        updateWorst();
        updateAvg3();
        updateAvg5();
        updateAvg12();
        updateAvg50();
        updateAvg100();
        updateSessionMean();
    }

    private List<Result> getResults() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return null;
        return databaseManager.getAllResults(selectedDiscipline.getId());
    }

    private List<Long> getTimes() {
        List<Result> results = getResults();
        if ((results != null ? results.size() : 0) == 0)
            return null;
        List<Long> times = new ArrayList<>();
        for (Result result : results) {
            if (result.getTime() != null)
                times.add(result.getTime());
        }
        return times;
    }

    private void updateBest() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long best = Collections.min(times);
        if (statistics.getBest() == null || best < statistics.getBest()) {
            databaseManager.updateBest(best, selectedDiscipline.getId());
        }
    }

    private void updateWorst() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long worst = Collections.max(times);
        if (statistics.getWorst() == null || worst > statistics.getWorst()) {
            databaseManager.updateWorst(worst, selectedDiscipline.getId());
        }
    }

    private void updateAvg3() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0 || times.size() < 3)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long currentAvg3 = getCurrentAverage3(times);
        //update current
        databaseManager.updateAvg3(currentAvg3, selectedDiscipline.getId());
        //update best
        if (statistics.getBestAvg3() == null || currentAvg3 < statistics.getBestAvg3()) {
            databaseManager.updateBestAvg3(currentAvg3, selectedDiscipline.getId());
        }
    }

    private void updateAvg5() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0 || times.size() < 5)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long currentAvg5 = getCurrentAverage(getLastNTimes(times, 5));
        //update current
        databaseManager.updateAvg5(currentAvg5, selectedDiscipline.getId());
        //update best
        if (statistics.getBestAvg5() == null || currentAvg5 < statistics.getBestAvg5()) {
            databaseManager.updateBestAvg5(currentAvg5, selectedDiscipline.getId());
        }
    }

    private void updateAvg12() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0 || times.size() < 12)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long currentAvg12 = getCurrentAverage(getLastNTimes(times, 12));
        // update current
        databaseManager.updateAvg12(currentAvg12, selectedDiscipline.getId());
        //update best
        if (statistics.getBestAvg12() == null || currentAvg12 < statistics.getBestAvg12()) {
            databaseManager.updateBestAvg12(currentAvg12, selectedDiscipline.getId());
        }
    }

    private void updateAvg50() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0 || times.size() < 50)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long currentAvg50 = getCurrentAverage(getLastNTimes(times, 50));
        //update current
        databaseManager.updateAvg50(currentAvg50, selectedDiscipline.getId());
        //update best
        if (statistics.getBestAvg50() == null || currentAvg50 < statistics.getBestAvg50()) {
            databaseManager.updateBestAvg50(currentAvg50, selectedDiscipline.getId());
        }
    }

    private void updateAvg100() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0 || times.size() < 100)
            return;
        Statistics statistics = databaseManager.getStatisticsByDiscipline(selectedDiscipline.getId());
        Long currentAvg100 = getCurrentAverage(getLastNTimes(times, 100));
        //update current
        databaseManager.updateAvg100(currentAvg100, selectedDiscipline.getId());
        //update best
        if (statistics.getBestAvg100() == null || currentAvg100 < statistics.getBestAvg100()) {
            databaseManager.updateBestAvg100(currentAvg100, selectedDiscipline.getId());
        }
    }

    private void updateSessionMean() {
        Discipline selectedDiscipline = model.selectedDiscipline;
        if (selectedDiscipline == null)
            return;
        List<Long> times = getTimes();
        if ((times != null ? times.size() : 0) == 0)
            return;
        Long currentSessionMean = getCurrentAverage(getLastNTimes(times, times.size()));

        databaseManager.updateSessionMean(currentSessionMean, selectedDiscipline.getId());
    }

    private Long getCurrentAverage(List<Long> times) {
        if (times.size() < 3)
            return null;
        Long best = Collections.min(times);
        Long worst = Collections.max(times);
        Long sum = (long) 0;
        for (int i = 0; i < times.size(); i++) {
            sum += times.get(i);
        }
        return (sum - best - worst) / (times.size() - 2);
    }

    private Long getCurrentAverage3(List<Long> times) {
        Long sum = (long) 0;
        Collections.reverse(times);
        for (int i = 0; i < 3; i++) {
            sum += times.get(i);
        }
        return sum / 3;
    }

    private List<Long> getLastNTimes(List<Long> list, int number) {
        Collections.reverse(list);
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            times.add(list.get(i));
        }
        return times;
    }
}
