package edu.galileo.android.flickerapp.searchresults;

import edu.galileo.android.flickerapp.entities.Picture;

public interface SearchResultsRepository {

    void loadPictures(String tags);
    void getNextPicture();
    void savePicture(Picture picture);

}
