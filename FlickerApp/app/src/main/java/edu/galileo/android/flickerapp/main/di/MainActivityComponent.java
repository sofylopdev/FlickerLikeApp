package edu.galileo.android.flickerapp.main.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.flickerapp.libs.di.LibsModule;
import edu.galileo.android.flickerapp.main.MainPresenter;

@Singleton
@Component(modules = {MainActivityModule.class, LibsModule.class})
public interface MainActivityComponent {

    MainPresenter getMainPresenter();
}
