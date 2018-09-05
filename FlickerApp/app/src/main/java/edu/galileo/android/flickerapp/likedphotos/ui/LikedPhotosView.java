package edu.galileo.android.flickerapp.likedphotos.ui;

import java.util.List;

import edu.galileo.android.flickerapp.entities.Picture;

public interface LikedPhotosView {
    void emptyDB();
    void setPicturesList(List<Picture> list);
}
