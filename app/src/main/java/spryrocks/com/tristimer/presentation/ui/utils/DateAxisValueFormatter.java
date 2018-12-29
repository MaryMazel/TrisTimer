package spryrocks.com.tristimer.presentation.ui.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAxisValueFormatter  implements IAxisValueFormatter {
    private long referenceTimestamp; // minimum timestamp in your data set
    private SimpleDateFormat dateFormat;
    private Date date;

    public DateAxisValueFormatter(long referenceTimestamp) {
        Locale locale = new Locale("uk_UA");
        this.referenceTimestamp = referenceTimestamp;
        this.dateFormat = new SimpleDateFormat("dd/MM", locale);
        this.date = new Date();
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;

        // Retrieve original timestamp
        long originalTimestamp = referenceTimestamp + convertedTimestamp;

        // Convert timestamp to hour:minute
        return getDate(originalTimestamp);
    }

    private String getDate(long timestamp){
        try{
            date.setTime(timestamp);
            return dateFormat.format(date);
        }
        catch(Exception ex){
            return "ex";
        }
    }
}
