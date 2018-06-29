package spryrocks.com.tristimer.presentation.ui.screens

import com.spryrocks.android.modules.ui.mvvm.model.ObservableField

class NavigationModel {
    val menuItem : ObservableField<MenuItem> = ObservableField()

    enum class MenuItem {
        TIMER, RESULTS, STATISTICS
    }
}

