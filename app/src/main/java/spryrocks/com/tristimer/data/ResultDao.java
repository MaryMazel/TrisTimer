package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result")
    List<Result> getAllResults();

    @Insert
    void insertAll(Result... results);

    @Query("DELETE FROM result WHERE id = :resultID")
    void deleteSelectedResults(int resultID);

    @Query("DELETE FROM RESULT")
    void clearSession();
}
