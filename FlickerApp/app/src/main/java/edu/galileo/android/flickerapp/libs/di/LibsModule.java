package edu.galileo.android.flickerapp.libs.di;

import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.libs.MyEventBus;

@Module
public class LibsModule {
    private AppCompatActivity activity;

    public LibsModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    MyEventBus providesMyEventBus(EventBus eventBus) {
        return new MyEventBus(eventBus);
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager) {
        return new ImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(AppCompatActivity activity) {
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    AppCompatActivity providesActivity() {
        return this.activity;
    }
}
