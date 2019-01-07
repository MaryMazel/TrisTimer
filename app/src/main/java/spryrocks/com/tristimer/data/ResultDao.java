package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result WHERE `Discipline id` = :discipline")
    List<Result> getAllResults(int discipline);

    @Insert
    void insertAll(Result... results);

    @Query("DELETE FROM result WHERE id = :resultID")
    void deleteSelectedResults(int resultID);

    @Query("DELETE FROM RESULT")
    void clearSession();

    @Query("UPDATE result SET time = null WHERE id = :resultID")
    void setPenaltyDNF(int resultID);

    @Query("UPDATE result SET time = time + 2000 WHERE id = :resultID")
    void setPenaltyPlusTwo(int resultID);
}
