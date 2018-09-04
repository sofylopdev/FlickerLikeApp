package edu.galileo.android.flickerapp.searchresults;

public class GetNextPictureInteractorImpl implements GetNextPictureInteractor{

    private SearchResultsRepository repository;

    public GetNextPictureInteractorImpl(SearchResultsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeGetNext() {
        repository.getNextPicture();
    }
}
