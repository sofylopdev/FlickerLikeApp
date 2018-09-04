package edu.galileo.android.flickerapp.searchresults;

import edu.galileo.android.flickerapp.entities.Picture;

public interface SearchResultsRepository {

    void getNextPicture();
    void savePicture(Picture picture);

}
