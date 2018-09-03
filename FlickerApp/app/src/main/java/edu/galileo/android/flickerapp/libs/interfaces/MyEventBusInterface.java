package edu.galileo.android.flickerapp.libs.interfaces;

public interface MyEventBusInterface {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
