package edu.galileo.android.flickerapp.libs.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.flickerapp.libs.ImageLoader;

@Singleton
@Component(modules = LibsModule.class)
public interface LibsComponent {

    ImageLoader getImageLoader();
}
