package spryrocks.com.tristimer.data;

import android.arch.persistence.room.TypeConverter;

import static spryrocks.com.tristimer.data.Result.Penalty.PENALTY_DNF;
import static spryrocks.com.tristimer.data.Result.Penalty.PENALTY_OK;
import static spryrocks.com.tristimer.data.Result.Penalty.PENALTY_PLUSTWO;

public class EnumConverter {
    @TypeConverter
    public static Result.Penalty toCode(int code) {
        if (code == PENALTY_OK.getCode()) {
            return PENALTY_OK;
        } else if (code == PENALTY_PLUSTWO.getCode()) {
            return PENALTY_PLUSTWO;
        } else if (code == PENALTY_DNF.getCode()) {
            return PENALTY_DNF;
        } else {
            throw new IllegalArgumentException("Could not recognize code");
        }
    }

    @TypeConverter
    public static Integer toInteger(Result.Penalty penalty) {
        return penalty.getCode();
    }
}
