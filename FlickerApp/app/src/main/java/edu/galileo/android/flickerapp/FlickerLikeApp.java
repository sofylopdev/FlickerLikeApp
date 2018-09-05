package edu.galileo.android.flickerapp;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.raizlabs.android.dbflow.config.FlowManager;

import edu.galileo.android.flickerapp.libs.di.DaggerLibsComponent;
import edu.galileo.android.flickerapp.libs.di.LibsComponent;
import edu.galileo.android.flickerapp.libs.di.LibsModule;
import edu.galileo.android.flickerapp.likedphotos.di.DaggerLikedPicturesComponent;
import edu.galileo.android.flickerapp.likedphotos.di.LikedPicturesComponent;
import edu.galileo.android.flickerapp.likedphotos.di.LikedPicturesModule;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.PictureClickListener;
import edu.galileo.android.flickerapp.main.di.DaggerMainActivityComponent;
import edu.galileo.android.flickerapp.main.di.MainActivityComponent;
import edu.galileo.android.flickerapp.main.di.MainActivityModule;
import edu.galileo.android.flickerapp.main.ui.MainActivity;
import edu.galileo.android.flickerapp.main.ui.MainView;
import edu.galileo.android.flickerapp.searchresults.di.DaggerSearchActivityComponent;
import edu.galileo.android.flickerapp.searchresults.di.SearchActivityComponent;
import edu.galileo.android.flickerapp.searchresults.di.SearchActivityModule;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsActivity;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;

public class FlickerLikeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }

    private void initDatabase() {
        FlowManager.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    public MainActivityComponent getMainComponent(MainActivity activity, MainView view) {
        return DaggerMainActivityComponent.builder()
                .libsModule(new LibsModule(activity))
                .mainActivityModule(new MainActivityModule(view))
                .build();
    }

    public SearchActivityComponent getSearchActivityComponent(SearchResultsActivity activity, SearchResultsView view){
        return DaggerSearchActivityComponent.builder()
                .libsModule(new LibsModule(activity))
                .searchActivityModule(new SearchActivityModule(view))
                .build();
    }

    public LikedPicturesComponent getLikedPicturesComponent(LikedPhotosActivity activity, LikedPhotosView view, PictureClickListener listener){
        return DaggerLikedPicturesComponent.builder()
                .libsModule(new LibsModule(activity))
                .likedPicturesModule(new LikedPicturesModule(view, listener))
                .build();
    }

    public LibsComponent getLibsComponent(AppCompatActivity activity){
        return DaggerLibsComponent.builder()
                .libsModule(new LibsModule(activity))
                .build();
    }

}
