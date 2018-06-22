package spryrocks.com.tristimer.presentation.ui.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public class BindingAdapters {
    @BindingAdapter("android:onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }
}