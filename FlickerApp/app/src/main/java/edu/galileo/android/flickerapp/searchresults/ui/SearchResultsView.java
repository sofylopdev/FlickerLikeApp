package edu.galileo.android.flickerapp.searchresults.ui;

import edu.galileo.android.flickerapp.entities.Picture;

public interface SearchResultsView {
    void showProgress();
    void hideProgress();

    void showUIElements();
    void hidUIElements();

    void onPictureSaved();

    void setPictureAndTitle(Picture picture);
    void onPictureError(String error);
    void noMorePictures();
}
