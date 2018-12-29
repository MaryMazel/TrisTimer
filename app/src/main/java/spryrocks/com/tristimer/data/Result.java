package spryrocks.com.tristimer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Result {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private Long time;

    @ColumnInfo(name = "scramble")
    private String scramble;

    @ColumnInfo(name = "date")
    private long date;

    public Result(Long time, String scramble, long date) {
        this.time = time;
        this.scramble = scramble;
        this.date = date;
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
}
