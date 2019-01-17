package spryrocks.com.tristimer.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import spryrocks.com.tristimer.data.entities.Discipline;

@Dao
public interface DisciplineDao {
    @Query("SELECT * FROM discipline")
    List<Discipline> getAllDisciplines();

    @Insert
    void insertAll(Discipline... disciplines);
}
