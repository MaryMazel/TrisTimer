package spryrocks.com.tristimer.presentation.ui.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Converters {
    public static String timeToString(Float time) {
        if (time != null) {

            long millis = (long)(time % 100);
            long second = (long)(time / 1000) % 60;
            long minute = (long)(time / (1000 * 60)) % 60;
            if (minute > 0)
                return String.format(Locale.US, "%02d:%02d.%02d",  minute, second, millis);
            else
                return String.format(Locale.US, "%02d.%02d", second, millis);
        }
        else
            return "DNF";
    }
}
