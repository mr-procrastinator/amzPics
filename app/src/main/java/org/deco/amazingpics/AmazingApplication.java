package org.deco.amazingpics;

import android.app.Application;

import org.deco.amazingpics.injector.AppModule;
import org.deco.amazingpics.injector.components.AppComponent;
import org.deco.amazingpics.injector.components.DaggerAppComponent;

public class AmazingApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {

        mAppComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
