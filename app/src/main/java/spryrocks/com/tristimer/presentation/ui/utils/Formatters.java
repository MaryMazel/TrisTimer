package spryrocks.com.tristimer.presentation.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatters {
    public static String formatDate(Date date) {
        Locale locale = new Locale("uk");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
        return simpleDateFormat.format(date);
    }


}
