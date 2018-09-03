package edu.galileo.android.flickerapp.libs;

import org.greenrobot.eventbus.EventBus;

import edu.galileo.android.flickerapp.libs.interfaces.MyEventBusInterface;

public class MyEventBus implements MyEventBusInterface {

    private EventBus eventBus;

    public MyEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
