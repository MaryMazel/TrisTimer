package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DisciplineDao {
    @Query("SELECT * FROM discipline")
    List<Discipline> getAllDisciplines();

    @Insert
    void insertAll(Discipline... disciplines);
}
