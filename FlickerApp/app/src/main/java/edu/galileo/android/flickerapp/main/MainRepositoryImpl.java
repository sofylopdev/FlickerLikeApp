package edu.galileo.android.flickerapp.main;

import android.text.TextUtils;

import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.main.events.MainActivityEvent;

public class MainRepositoryImpl implements MainRepository {

    private MyEventBus eventBus;

    public MainRepositoryImpl(MyEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onSearchClicked(String tags) {
        if (TextUtils.isEmpty(tags)) {
            post(MainActivityEvent.EMPTY_STRING, null);
        } else {
            post(MainActivityEvent.NOT_EMPTY_STRING, tags);
        }
    }

    private void post(int type, String tags) {
        MainActivityEvent event = new MainActivityEvent();
        event.setTags(tags);
        event.setType(type);
        eventBus.post(event);
    }
}
