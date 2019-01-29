package spryrocks.com.tristimer.presentation.ui.screens;

import com.spryrocks.android.modules.ui.mvvm.model.ObservableField;

public class NavigationModel {
    public final ObservableField<MenuItem> menuItem = new ObservableField<>();

    enum MenuItem {
        TIMER, RESULTS, STATISTICS
    }
}
