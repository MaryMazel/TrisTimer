package spryrocks.com.tristimer.domain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.List;

import spryrocks.com.tristimer.data.AppDatabase;
import spryrocks.com.tristimer.data.DatabaseInitializer;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Result;
import spryrocks.com.tristimer.data.entities.Statistics;

public class DatabaseManager {
    private AppDatabase database;

    public DatabaseManager(@NonNull Context context) {
        database = DatabaseInitializer.createDatabase(context);
    }

    public DatabaseManager(@NonNull Fragment fragment) {
        this(fragment.requireContext());
    }

    public List<Result> getAllResults(int discipline) {
        return database.resultDao().getAllResults(discipline);
    }

    public List<Discipline> getAllDisciplines() {
        return database.disciplineDao().getAllDisciplines();
    }

    public void insertResult(Result result){
        database.resultDao().insertAll(result);
    }

    public void deleteSelectedResults(List<Result> results) {
        for (Result result : results) {
            database.resultDao().deleteSelectedResults(result.getId());
        }
    }

    public void deleteLastResult(Result result) {
        database.resultDao().deleteSelectedResults(result.getId());
    }

    public void clearSession(int discipline) {
        database.resultDao().clearSession(discipline);
    }

    public void setPenaltyDNF(Result result, Result.Penalty penalty) {
        database.resultDao().setPenaltyDNF(result.getId(), penalty);
    }

    public void setPenaltyPlusTwo(Result result, Result.Penalty penalty){
        database.resultDao().setPenaltyPlusTwo(result.getId(), penalty);
    }

    public Statistics getStatisticsByDiscipline(int discipline) {
        return database.statisticsDao().getStatisicsByDiscipline(discipline);
    }

    public void updateBest(Long best, int discipline) {
        database.statisticsDao().updateBest(best, discipline);
    }

    public void updateWorst(Long worst, int discipline) {
        database.statisticsDao().updateWorst(worst, discipline);
    }

    public void updateSessionMean(Long sessionMean, int discipline) {
        database.statisticsDao().updateSessionMean(sessionMean, discipline);
    }

    public void updateAvg3(Long avg3, int discipline) {
        database.statisticsDao().updateAvg3(avg3, discipline);
    }

    public void updateAvg5(Long avg5, int discipline) {
        database.statisticsDao().updateAvg5(avg5, discipline);
    }

    public void updateAvg12(Long avg12, int discipline) {
        database.statisticsDao().updateAvg12(avg12, discipline);
    }

    public void updateAvg50(Long avg50, int discipline) {
        database.statisticsDao().updateAvg50(avg50, discipline);
    }

    public void updateAvg100(Long avg100, int discipline) {
        database.statisticsDao().updateAvg100(avg100, discipline);
    }

    public void updateBestAvg3(Long bestAvg3, int discipline) {
        database.statisticsDao().updateBestAvg3(bestAvg3, discipline);
    }

    public void updateBestAvg5(Long bestAvg5, int discipline) {
        database.statisticsDao().updateBestAvg5(bestAvg5, discipline);
    }

    public void updateBestAvg12(Long bestAvg12, int discipline) {
        database.statisticsDao().updateBestAvg12(bestAvg12, discipline);
    }

    public void updateBestAvg50(Long bestAvg50, int discipline) {
        database.statisticsDao().updateBestAvg50(bestAvg50, discipline);
    }

    public void updateBestAvg100(Long bestAvg100, int discipline) {
        database.statisticsDao().updateBestAvg100(bestAvg100, discipline);
    }

    public void setAlltoNull(int discipline) {
        database.statisticsDao().setAllToNull(discipline);
    }
}
