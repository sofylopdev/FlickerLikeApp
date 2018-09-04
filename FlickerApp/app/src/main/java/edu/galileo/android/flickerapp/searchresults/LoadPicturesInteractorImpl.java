package edu.galileo.android.flickerapp.searchresults;

public class LoadPicturesInteractorImpl implements LoadPicturesInteractor{

    private SearchResultsRepository repository;

    public LoadPicturesInteractorImpl(SearchResultsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeLoading(String tags) {
        repository.loadPictures(tags);
    }
}
