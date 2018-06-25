package spryrocks.com.tristimer.presentation.ui.utils;

public class Converters {
    public static String timeToString(Float time) {
        if (time != null)
            return time.toString();
        else
            return "DNF";
    }
}
