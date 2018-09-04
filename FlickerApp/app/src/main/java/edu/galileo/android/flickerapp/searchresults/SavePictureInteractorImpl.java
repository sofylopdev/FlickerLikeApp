package edu.galileo.android.flickerapp.searchresults;

import edu.galileo.android.flickerapp.entities.Picture;

public class SavePictureInteractorImpl implements SavePictureInteractor{

    private SearchResultsRepository repository;

    public SavePictureInteractorImpl(SearchResultsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeSave(Picture picture) {
       repository.savePicture(picture);
    }
}
