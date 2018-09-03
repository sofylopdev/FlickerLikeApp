package edu.galileo.android.flickerapp.main.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.main.MainInteractor;
import edu.galileo.android.flickerapp.main.MainInteractorImpl;
import edu.galileo.android.flickerapp.main.MainPresenter;
import edu.galileo.android.flickerapp.main.MainPresenterImpl;
import edu.galileo.android.flickerapp.main.MainRepository;
import edu.galileo.android.flickerapp.main.MainRepositoryImpl;
import edu.galileo.android.flickerapp.main.ui.MainView;

@Module
public class MainActivityModule {

    private MainView view;

    public MainActivityModule(MainView view) {
        this.view = view;
    }

    @Singleton
    @Provides
    MainPresenter providesMainPresenter(MyEventBus eventBus, MainView view, MainInteractor interactor){
        return new MainPresenterImpl(eventBus, view, interactor);
    }

    @Singleton
    @Provides
    MainView providesMainView(){
        return this.view;
    }

    @Singleton
    @Provides
    MainInteractor providesMainInteractor(MainRepository repository){
        return new MainInteractorImpl(repository);
    }

    @Singleton
    @Provides
    MainRepository providesMainRepository(MyEventBus eventBus){
        return new MainRepositoryImpl(eventBus);
    }

}
