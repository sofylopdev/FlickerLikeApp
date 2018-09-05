package edu.galileo.android.flickerapp.likedphotos.ui.adapters;

import android.view.View;

import edu.galileo.android.flickerapp.entities.Picture;

public interface PictureClickListener {
    void onPictureClick(Picture picture, int position);
}
