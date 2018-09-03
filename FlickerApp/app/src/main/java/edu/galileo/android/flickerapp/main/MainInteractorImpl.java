package edu.galileo.android.flickerapp.main;

public class MainInteractorImpl implements MainInteractor{

    private MainRepository repository;

    public MainInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String tags) {
        repository.onSearchClicked(tags);
    }
}
