package edu.galileo.android.flickerapp.searchresults.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.flickerapp.api.FlickerClient;
import edu.galileo.android.flickerapp.api.FlickrService;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.searchresults.GetNextPictureInteractor;
import edu.galileo.android.flickerapp.searchresults.GetNextPictureInteractorImpl;
import edu.galileo.android.flickerapp.searchresults.LoadPicturesInteractor;
import edu.galileo.android.flickerapp.searchresults.LoadPicturesInteractorImpl;
import edu.galileo.android.flickerapp.searchresults.SavePictureInteractor;
import edu.galileo.android.flickerapp.searchresults.SavePictureInteractorImpl;
import edu.galileo.android.flickerapp.searchresults.SearchResultsPresenter;
import edu.galileo.android.flickerapp.searchresults.SearchResultsPresenterImpl;
import edu.galileo.android.flickerapp.searchresults.SearchResultsRepository;
import edu.galileo.android.flickerapp.searchresults.SearchResultsRepositoryImpl;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;

@Module
public class SearchActivityModule {

    private SearchResultsView view;

    public SearchActivityModule(SearchResultsView view) {
        this.view = view;
    }

    @Singleton
    @Provides
    SearchResultsPresenter providesPresenter(MyEventBus eventBus,
                                             SearchResultsView view,
                                             SavePictureInteractor savePictureInteractor,
                                             GetNextPictureInteractor getNextPictureInteractor,
                                             LoadPicturesInteractor loadPicturesInteractor){
        return new SearchResultsPresenterImpl(
                eventBus,
                view,
                savePictureInteractor,
                getNextPictureInteractor,
                loadPicturesInteractor);
    }

    @Singleton
    @Provides
    SearchResultsView providesView(){
        return this.view;
    }

    @Singleton
    @Provides
    SearchResultsRepository providesRepository(MyEventBus eventBus, FlickrService service){
        return new SearchResultsRepositoryImpl(eventBus, service);
    }

    @Singleton
    @Provides
    FlickrService providesService(){
        return new FlickerClient().getFlickrService();
    }

    @Singleton
    @Provides
    SavePictureInteractor providesSaveInteractor(SearchResultsRepository repository){
        return new SavePictureInteractorImpl(repository);
    }

    @Singleton
    @Provides
    GetNextPictureInteractor providesGetNextInteractor(SearchResultsRepository repository){
        return new GetNextPictureInteractorImpl(repository);
    }

    @Singleton
    @Provides
    LoadPicturesInteractor providesLoadInteractor(SearchResultsRepository repository){
        return new LoadPicturesInteractorImpl(repository);
    }

}
