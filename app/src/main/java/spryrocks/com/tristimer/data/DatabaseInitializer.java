package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {
    public static void initialize(@NonNull Context context) {
        AppDatabase database = createDatabase(context);

        DisciplineDao disciplineDao = database.disciplineDao();
        List<Discipline> disciplines = disciplineDao.getAllDisciplines();
        if (disciplines.size() == 0) {
            disciplineDao.insertAll(getInitialData());
        }
    }

    private static Discipline[] getInitialData() {
        List<Discipline> disciplines = new ArrayList<>();
        disciplines.add(new Discipline(1, "3x3"));
        disciplines.add(new Discipline(2, "3x3 OH"));
        disciplines.add(new Discipline(3, "3x3 BLD"));
        disciplines.add(new Discipline(4, "2x2"));
        disciplines.add(new Discipline(5, "4x4"));
        disciplines.add(new Discipline(6, "Skewb"));
        disciplines.add(new Discipline(7, "Pyraminx"));

        return disciplines.toArray(new Discipline[0]);
    }

    @NonNull
    public static AppDatabase createDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timer_database")
                .allowMainThreadQueries()
                .build();
    }
}
