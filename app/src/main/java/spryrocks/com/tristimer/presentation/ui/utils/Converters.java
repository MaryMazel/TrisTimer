package spryrocks.com.tristimer.presentation.ui.utils;

import java.util.Date;
import java.util.Locale;

public class Converters {
    public static String timeToString(Long time, boolean penalty, boolean stats) {
        if (time != null) {
            long millis = (time % 1000) / 10;
            long second = (time / 1000) % 60;
            long minute = (time / (1000 * 60)) % 60;
            if (minute > 0)
                return String.format(Locale.US, "%02d:%02d.%02d", minute, second, millis);
            else
                return String.format(Locale.US, "%02d.%02d", second, millis);
        } else if (penalty) {
            return "DNF";
        } else if (stats) {
            return "-";
        } else {
            return "00.00";
        }
    }

    public static float timeToFloat(Long time) {
            long millis = (time % 1000) / 10;
            long second = (time / 1000) % 60;
            String myTime = String.format(Locale.US, "%02d.%02d", second, millis);
            return Float.parseFloat(myTime);
    }

    public static Date timestampToDate(long date) {
        return new Date(date);
    }
}
