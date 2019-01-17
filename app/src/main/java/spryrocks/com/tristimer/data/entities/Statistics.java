package spryrocks.com.tristimer.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = @Index(value="discipline"), foreignKeys = @ForeignKey(entity = Discipline.class,
                                                                        parentColumns = "id",
                                                                        childColumns = "discipline",
                                                                        onDelete = CASCADE))
public class Statistics {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "discipline")
    private int discipline;

    @ColumnInfo(name = "best")
    private Long best;

    @ColumnInfo(name = "worst")
    private Long worst;

    @ColumnInfo(name = "sessionMean")
    private Long sessionMean;

    @ColumnInfo(name = "avg3")
    private Long avg3;

    @ColumnInfo(name = "avg5")
    private Long avg5;

    @ColumnInfo(name = "avg12")
    private Long avg12;

    @ColumnInfo(name = "avg50")
    private Long avg50;

    @ColumnInfo(name = "avg100")
    private Long avg100;

    @ColumnInfo(name = "bestAvg3")
    private Long bestAvg3;

    @ColumnInfo(name = "bestAvg5")
    private Long bestAvg5;

    @ColumnInfo(name = "bestAvg12")
    private Long bestAvg12;

    @ColumnInfo(name = "bestAvg50")
    private Long bestAvg50;

    @ColumnInfo(name = "bestAvg100")
    private Long bestAvg100;

    public Statistics(int id, int discipline, Long best, Long worst,
                      Long sessionMean, Long avg3, Long avg5, Long avg12,
                      Long avg50, Long avg100, Long bestAvg3, Long bestAvg5,
                      Long bestAvg12, Long bestAvg50, Long bestAvg100) {
        this.id = id;
        this.discipline = discipline;
        this.best = best;
        this.worst = worst;
        this.sessionMean = sessionMean;
        this.avg3 = avg3;
        this.avg5 = avg5;
        this.avg12 = avg12;
        this.avg50 = avg50;
        this.avg100 = avg100;
        this.bestAvg3 = bestAvg3;
        this.bestAvg5 = bestAvg5;
        this.bestAvg12 = bestAvg12;
        this.bestAvg50 = bestAvg50;
        this.bestAvg100 = bestAvg100;
    }

    public int getId() {
        return id;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public Long getBest() {
        return best;
    }

    public void setBest(Long best) {
        this.best = best;
    }

    public Long getWorst() {
        return worst;
    }

    public void setWorst(Long worst) {
        this.worst = worst;
    }

    public Long getBestAvg3() {
        return bestAvg3;
    }

    public void setBestAvg3(Long bestAvg3) {
        this.bestAvg3 = bestAvg3;
    }

    public Long getBestAvg5() {
        return bestAvg5;
    }

    public void setBestAvg5(Long bestAvg5) {
        this.bestAvg5 = bestAvg5;
    }

    public Long getBestAvg12() {
        return bestAvg12;
    }

    public void setBestAvg12(Long bestAvg12) {
        this.bestAvg12 = bestAvg12;
    }

    public Long getBestAvg50() {
        return bestAvg50;
    }

    public void setBestAvg50(Long bestAvg50) {
        this.bestAvg50 = bestAvg50;
    }

    public Long getBestAvg100() {
        return bestAvg100;
    }

    public void setBestAvg100(Long bestAvg100) {
        this.bestAvg100 = bestAvg100;
    }

    public Long getSessionMean() {
        return sessionMean;
    }

    public void setSessionMean(Long sessionMean) {
        this.sessionMean = sessionMean;
    }

    public Long getAvg3() {
        return avg3;
    }

    public void setAvg3(Long avg3) {
        this.avg3 = avg3;
    }

    public Long getAvg5() {
        return avg5;
    }

    public void setAvg5(Long avg5) {
        this.avg5 = avg5;
    }

    public Long getAvg12() {
        return avg12;
    }

    public void setAvg12(Long avg12) {
        this.avg12 = avg12;
    }

    public Long getAvg50() {
        return avg50;
    }

    public void setAvg50(Long avg50) {
        this.avg50 = avg50;
    }

    public Long getAvg100() {
        return avg100;
    }

    public void setAvg100(Long avg100) {
        this.avg100 = avg100;
    }
}
