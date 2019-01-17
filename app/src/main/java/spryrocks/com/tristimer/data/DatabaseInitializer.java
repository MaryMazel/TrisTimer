package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import spryrocks.com.tristimer.data.dao.DisciplineDao;
import spryrocks.com.tristimer.data.dao.StatisticsDao;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Statistics;

public class DatabaseInitializer {
    public static void initialize(@NonNull Context context) {
        AppDatabase database = createDatabase(context);

        DisciplineDao disciplineDao = database.disciplineDao();
        List<Discipline> disciplines = disciplineDao.getAllDisciplines();
        if (disciplines.size() == 0) {
            disciplineDao.insertAll(getInitialDisciplineData());
        }

        StatisticsDao statisticsDao = database.statisticsDao();
        List<Statistics> statistics = statisticsDao.getAllStatistics();
        if (statistics.size() == 0) {
            statisticsDao.insertAll(getInitialStatisticsData());
        }
    }

    private static Discipline[] getInitialDisciplineData() {
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

    private static Statistics[] getInitialStatisticsData() {
        List<Statistics> statistics = new ArrayList<>();
        statistics.add(new Statistics(1, 1, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(2, 2, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(3, 3, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(4, 4, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(5, 5, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(6, 6, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        statistics.add(new Statistics(7, 7, null, null, null, null, null,
                null, null, null, null, null, null, null, null));

        return statistics.toArray(new Statistics[0]);
    }

    @NonNull
    public static AppDatabase createDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "timer_database")
                .allowMainThreadQueries()
                .build();
    }
}
