package edu.galileo.android.flickerapp.likedphotos;

import edu.galileo.android.flickerapp.likedphotos.events.PictureListEvent;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;

public interface LikedPhotosPresenter {

    void onCreate();
    void onDestroy();
    void onEventMainThread(PictureListEvent event);

    void getPictures();

    LikedPhotosView getView();
}
