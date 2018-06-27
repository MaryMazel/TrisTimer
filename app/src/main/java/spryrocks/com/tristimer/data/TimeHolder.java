package spryrocks.com.tristimer.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TimeHolder {
    Context context;

    public TimeHolder(Context context){
        this.context = context;
    }

    SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sPref.edit();
    private static final String TIME = "Time";

    public void setTime(Long time){
        editor.putLong(TIME, time);
        editor.apply();
    }

    public Long getTime(){
        return sPref.getLong(TIME, 0);
    }
}
