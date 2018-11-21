package spryrocks.com.tristimer.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result")
    List<Result> getAllResults();

    @Insert
    void insertAll(Result... results);
}
