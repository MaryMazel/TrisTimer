package spryrocks.com.tristimer.domain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.List;

import androidx.room.Room;
import spryrocks.com.tristimer.data.AppDatabase;
import spryrocks.com.tristimer.data.Result;

public class DatabaseManager {
    private AppDatabase database;

    private DatabaseManager(@NonNull Context context) {
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
}
