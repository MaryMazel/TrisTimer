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
        Locale locale = new Locale("UK_ua");
        float myTime = time.floatValue();
        float t = (myTime / 1000);
        int second = (int) t;
        float rest = t - second;
        int millis = Integer.parseInt(String.valueOf(rest).split("\\.")[1]);
        String stringTime = String.format(locale, "%d.%02d", second, millis);
        return Float.parseFloat(stringTime);
    }

    public static String floatToString(float time) {
        long myTime = (long) time*1000;
        long millis = (myTime % 1000) / 10;
        long second = (myTime / 1000) % 60;
        long minute = (myTime / (1000 * 60)) % 60;
        String res;
        if (minute > 0) {
            res = String.format(Locale.US, "%02d:%02d", minute, second);
        } else {
            res = String.format(Locale.US, "%02d.%02d", second, millis);
        }
        return res;
    }

    public static Date timestampToDate(long date) {
        return new Date(date);
    }
}
