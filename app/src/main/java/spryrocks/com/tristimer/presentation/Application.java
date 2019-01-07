package spryrocks.com.tristimer.presentation;

import spryrocks.com.tristimer.data.DatabaseInitializer;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseInitializer.initialize(this);
    }
}
