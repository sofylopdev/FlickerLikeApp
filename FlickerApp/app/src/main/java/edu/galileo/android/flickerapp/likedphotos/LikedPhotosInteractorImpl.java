package edu.galileo.android.flickerapp.likedphotos;

public class LikedPhotosInteractorImpl implements LikedPhotosInteractor{
    private LikedPhotosRepository repository;

    public LikedPhotosInteractorImpl(LikedPhotosRepository repository) {
        this.repository = repository;
    }


    @Override
    public void executeGetPictures() {
        repository.getPicturesFromDb();
    }
}
