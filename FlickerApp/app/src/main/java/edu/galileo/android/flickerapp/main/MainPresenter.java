package edu.galileo.android.flickerapp.main;

import edu.galileo.android.flickerapp.main.events.MainActivityEvent;

public interface MainPresenter {

    void onCreate();
    void onDestroy();
    void onEventMainThread(MainActivityEvent event);

    void search(String search);

}
