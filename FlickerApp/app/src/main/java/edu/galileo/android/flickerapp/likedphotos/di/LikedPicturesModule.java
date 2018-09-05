package edu.galileo.android.flickerapp.likedphotos.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosInteractor;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosInteractorImpl;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosPresenter;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosPresenterImpl;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosRepository;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosRepositoryImpl;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.LikedPicturesAdapter;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.PictureClickListener;

@Module
public class LikedPicturesModule {

    private LikedPhotosView view;
    private PictureClickListener listener;

    public LikedPicturesModule(LikedPhotosView view, PictureClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Singleton
    @Provides
    LikedPicturesAdapter providesAdapter(List<Picture> pictureList, PictureClickListener listener, ImageLoader imageLoader){
        return new LikedPicturesAdapter(pictureList, listener, imageLoader);
    }

    @Singleton
    @Provides
    List<Picture> providesEmptyList(){
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    PictureClickListener providesListener(){
        return this.listener;
    }

    @Singleton
    @Provides
    LikedPhotosPresenter providesListPresenter(MyEventBus eventBus, LikedPhotosView view, LikedPhotosInteractor interactor){
        return new LikedPhotosPresenterImpl(eventBus, view, interactor);
    }

    @Singleton
    @Provides
    LikedPhotosView providesView(){
        return this.view;
    }

    @Singleton
    @Provides
    LikedPhotosInteractor providesListInteractor(LikedPhotosRepository repository){
        return new LikedPhotosInteractorImpl(repository);
    }

    @Singleton
    @Provides
    LikedPhotosRepository providesListRepository(MyEventBus eventBus){
        return new LikedPhotosRepositoryImpl(eventBus);
    }
}
