package spryrocks.com.tristimer.presentation.ui.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class YAxisValueFormatter implements IAxisValueFormatter {
    //private DecimalFormat format;

    public YAxisValueFormatter() {
        //format = new DecimalFormat("#0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return Converters.floatToString(value);
    }
}