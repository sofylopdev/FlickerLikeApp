package edu.galileo.android.flickerapp.searchresults;

import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.searchresults.events.SearchResultsEvent;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;

public interface SearchResultsPresenter {

    void onCreate();
    void onDestroy();

    void dismissImage();
    void getNextImage();
    void saveImage(Picture picture);
    void onEventMainThread(SearchResultsEvent event);

    void imageError(String error);
    void imageReady();

    void loadImages(String tags);


    SearchResultsView getView();
}
