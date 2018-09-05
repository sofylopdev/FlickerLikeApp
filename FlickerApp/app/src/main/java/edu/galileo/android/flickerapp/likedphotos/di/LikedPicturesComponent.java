package edu.galileo.android.flickerapp.likedphotos.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.libs.di.LibsModule;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosPresenter;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.LikedPicturesAdapter;

@Singleton
@Component(modules = {LibsModule.class, LikedPicturesModule.class})
public interface LikedPicturesComponent {
    LikedPhotosPresenter getPresenter();
    LikedPicturesAdapter getAdapter();
    ImageLoader getImageLoader();
}
