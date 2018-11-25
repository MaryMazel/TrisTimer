package spryrocks.com.tristimer.domain;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.List;

import spryrocks.com.tristimer.data.AppDatabase;
import spryrocks.com.tristimer.data.Result;
import spryrocks.com.tristimer.presentation.ui.screens.results.ResultsAdapter;

public class DatabaseManager {
    private AppDatabase database;

    public DatabaseManager(@NonNull Context context) {
        database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timer_database")
                .allowMainThreadQueries()
                .build();
    }

    public DatabaseManager(@NonNull Fragment fragment) {
        this(fragment.requireContext());
    }

    public List<Result> getAllResults() {
        return database.resultDao().getAllResults();
    }

    public void insertResult(Result result){
        database.resultDao().insertAll(result);
    }

    public void deleteSelectedResults(List<Result> results) {
        for (Result result : results) {
            database.resultDao().deleteSelectedResults(result.getId());
        }
    }

    public void clearSession() {
        database.resultDao().clearSession();
    }
}
