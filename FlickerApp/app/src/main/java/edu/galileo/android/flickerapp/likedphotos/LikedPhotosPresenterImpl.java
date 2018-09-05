package edu.galileo.android.flickerapp.likedphotos;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.likedphotos.events.PictureListEvent;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;

public class LikedPhotosPresenterImpl implements LikedPhotosPresenter {

    private MyEventBus eventBus;
    private LikedPhotosView view;
    private LikedPhotosInteractor interactor;

    public LikedPhotosPresenterImpl(MyEventBus eventBus, LikedPhotosView view, LikedPhotosInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    @Subscribe
    public void onEventMainThread(PictureListEvent event) {
        if (view != null) {
            switch (event.getType()) {
                case PictureListEvent.HAS_PICTURES_EVENT:
                    view.setPicturesList(event.getPictureList());
                    break;
                case PictureListEvent.NO_PICTURES_EVENT:
                    view.emptyDB();
                    break;
            }
        }
    }

    @Override
    public void getPictures() {
        interactor.executeGetPictures();
    }

    @Override
    public LikedPhotosView getView() {
        return view;
    }
}
