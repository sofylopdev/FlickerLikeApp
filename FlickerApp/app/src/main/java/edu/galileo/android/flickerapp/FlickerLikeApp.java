package edu.galileo.android.flickerapp;

import android.app.Application;

import edu.galileo.android.flickerapp.libs.di.LibsModule;
import edu.galileo.android.flickerapp.main.di.DaggerMainActivityComponent;
import edu.galileo.android.flickerapp.main.di.MainActivityComponent;
import edu.galileo.android.flickerapp.main.di.MainActivityModule;
import edu.galileo.android.flickerapp.main.ui.MainActivity;
import edu.galileo.android.flickerapp.main.ui.MainView;

public class FlickerLikeApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public MainActivityComponent getMainComponent(MainActivity activity, MainView view){
        return DaggerMainActivityComponent.builder()
                .libsModule(new LibsModule(activity))
                .mainActivityModule(new MainActivityModule(view))
                .build();
    }
}
