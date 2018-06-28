package spryrocks.com.tristimer.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TimeHolder {
    private final SharedPreferences sPref;

    public TimeHolder(Context context){
        sPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static final String TIME = "Time";

    public void setTime(Long time){
        SharedPreferences.Editor editor = sPref.edit();
        if (time != null){
            editor.putLong(TIME, time);
            editor.apply();
        }
        else {
            editor.remove(TIME);
        }
    }

    public Long getTime(){
        return sPref.getLong(TIME, 0);
    }
}
