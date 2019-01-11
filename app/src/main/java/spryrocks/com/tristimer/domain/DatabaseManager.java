package spryrocks.com.tristimer.domain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.List;

import spryrocks.com.tristimer.data.AppDatabase;
import spryrocks.com.tristimer.data.DatabaseInitializer;
import spryrocks.com.tristimer.data.Discipline;
import spryrocks.com.tristimer.data.Result;

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

    public void clearSession() {
        database.resultDao().clearSession();
    }

    public void setPenaltyDNF(Result result, Result.Penalty penalty) {
        database.resultDao().setPenaltyDNF(result.getId(), penalty);
    }

    public void setPenaltyPlusTwo(Result result, Result.Penalty penalty){
        database.resultDao().setPenaltyPlusTwo(result.getId(), penalty);
    }
}
