package spryrocks.com.tristimer.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import spryrocks.com.tristimer.data.entities.Statistics;

@Dao
public interface StatisticsDao {
    @Insert
    void insertAll(Statistics... statistics);

    @Query("SELECT * FROM statistics")
    List<Statistics> getAllStatistics();

    @Query("SELECT * FROM statistics WHERE discipline = :discipline")
    Statistics getStatisicsByDiscipline(int discipline);

    @Query("UPDATE statistics SET best = :best WHERE discipline = :discipline")
    void updateBest(Long best, int discipline);

    @Query("UPDATE statistics SET worst = :worst WHERE discipline = :discipline")
    void updateWorst(Long worst, int discipline);

    @Query("UPDATE statistics SET sessionMean = :sessionMean WHERE discipline = :discipline")
    void updateSessionMean(Long sessionMean, int discipline);

    @Query("UPDATE statistics SET avg3 = :avg3 WHERE discipline = :discipline")
    void updateAvg3(Long avg3, int discipline);

    @Query("UPDATE statistics SET avg5 = :avg5 WHERE discipline = :discipline")
    void updateAvg5(Long avg5, int discipline);

    @Query("UPDATE statistics SET avg12 = :avg12 WHERE discipline = :discipline")
    void updateAvg12(Long avg12, int discipline);

    @Query("UPDATE statistics SET avg50 = :avg50 WHERE discipline = :discipline")
    void updateAvg50(Long avg50, int discipline);

    @Query("UPDATE statistics SET avg100 = :avg100 WHERE discipline = :discipline")
    void updateAvg100(Long avg100, int discipline);

    @Query("UPDATE statistics SET bestAvg3 = :bestAvg3 WHERE discipline = :discipline")
    void updateBestAvg3(Long bestAvg3, int discipline);

    @Query("UPDATE statistics SET bestAvg5 = :bestAvg5 WHERE discipline = :discipline")
    void updateBestAvg5(Long bestAvg5, int discipline);

    @Query("UPDATE statistics SET bestAvg12 = :bestAvg12 WHERE discipline = :discipline")
    void updateBestAvg12(Long bestAvg12, int discipline);

    @Query("UPDATE statistics SET bestAvg50 = :bestAvg50 WHERE discipline = :discipline")
    void updateBestAvg50(Long bestAvg50, int discipline);

    @Query("UPDATE statistics SET bestAvg100 = :bestAvg100 WHERE discipline = :discipline")
    void updateBestAvg100(Long bestAvg100, int discipline);

    @Query("UPDATE statistics SET best = null, sessionMean = null, worst = null, avg3 = null, avg5 = null, avg12 = null, avg50 = null, avg100 = null, bestAvg3 = null, bestAvg5 = null, bestAvg12 = null, bestAvg50 = null, bestAvg100 = null WHERE discipline = :discipline")
    void setAllToNull(int discipline);
}
