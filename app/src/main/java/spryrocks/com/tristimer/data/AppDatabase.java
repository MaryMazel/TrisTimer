package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import spryrocks.com.tristimer.data.dao.DisciplineDao;
import spryrocks.com.tristimer.data.dao.ResultDao;
import spryrocks.com.tristimer.data.dao.StatisticsDao;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.data.entities.Result;
import spryrocks.com.tristimer.data.entities.Statistics;

@Database(entities = {Result.class, Discipline.class, Statistics.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ResultDao resultDao();
    public abstract DisciplineDao disciplineDao();
    public abstract StatisticsDao statisticsDao();
 }
