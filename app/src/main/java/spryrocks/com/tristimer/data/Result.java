package spryrocks.com.tristimer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Result.class,
                                    parentColumns = "id",
                                    childColumns = "Discipline id"))
public class Result {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private Long time;

    @ColumnInfo(name = "scramble")
    private String scramble;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "Discipline id")
    private int discipline;

    public Result(Long time, String scramble, long date, int discipline) {
        this.time = time;
        this.scramble = scramble;
        this.date = date;
        this.discipline = discipline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public String getScramble() {
        return scramble;
    }

    public long getDate() {
        return date;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }
}
