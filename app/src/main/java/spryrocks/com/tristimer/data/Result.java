package spryrocks.com.tristimer.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Result {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "scramble")
    private String scramble;

    @ColumnInfo(name = "date")
    private String date;

    public Result(String time, String scramble, String date) {
        this.time = time;
        this.scramble = scramble;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getScramble() {
        return scramble;
    }

    public String getDate() {
        return date;
    }
}
