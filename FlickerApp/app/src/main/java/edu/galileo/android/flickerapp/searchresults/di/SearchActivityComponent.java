package edu.galileo.android.flickerapp.searchresults.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.libs.di.LibsModule;
import edu.galileo.android.flickerapp.searchresults.SearchResultsPresenter;

@Singleton
@Component(modules = {LibsModule.class, SearchActivityModule.class})
public interface SearchActivityComponent {
    ImageLoader getImageLoader();
    SearchResultsPresenter getPresenter();
}
