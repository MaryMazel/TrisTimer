package spryrocks.com.tristimer.presentation.ui.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatters {
    public static String formatDate(Date date) {
        Locale locale = new Locale("uk");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
        return simpleDateFormat.format(date);
    }

    public static String formatDate(long date) {
        Locale locale = new Locale("uk");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
        Timestamp ts = new Timestamp(date);
        Date formattedDate = new Date(ts.getTime());
        return simpleDateFormat.format(formattedDate);
    }
}
