package spryrocks.com.tristimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result WHERE `Discipline id` = :discipline")
    List<Result> getAllResults(int discipline);

    @Insert
    @TypeConverters(EnumConverter.class)
    void insertAll(Result... results);

    @Query("DELETE FROM result WHERE id = :resultID")
    void deleteSelectedResults(int resultID);

    @Query("DELETE FROM RESULT WHERE `Discipline id` = :discipline")
    void clearSession(int discipline);

    @Query("UPDATE result SET time = null, penalty = :penalty WHERE id = :resultID")
    @TypeConverters(EnumConverter.class)
    void setPenaltyDNF(int resultID, Result.Penalty penalty);

    @Query("UPDATE result SET time = time + 2000, penalty = :penalty WHERE id = :resultID")
    @TypeConverters(EnumConverter.class)
    void setPenaltyPlusTwo(int resultID, Result.Penalty penalty);
}
