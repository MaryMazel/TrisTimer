package spryrocks.com.tristimer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = @Index(value="Discipline id"), foreignKeys = @ForeignKey(entity = Discipline.class,
                                    parentColumns = "id",
                                    childColumns = "Discipline id",
                                    onDelete = CASCADE))
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

    @ColumnInfo(name = "penalty")
    @TypeConverters({EnumConverter.class})
    private Penalty penalty;

    public Result(Long time, String scramble, long date, int discipline, Penalty penalty) {
        this.time = time;
        this.scramble = scramble;
        this.date = date;
        this.discipline = discipline;
        this.penalty = penalty;
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

    public Penalty getPenalty() {
        return penalty;
    }

    public enum Penalty {
        PENALTY_OK(0),
        PENALTY_PLUSTWO(1),
        PENALTY_DNF(2);

        private int code;

        Penalty(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
