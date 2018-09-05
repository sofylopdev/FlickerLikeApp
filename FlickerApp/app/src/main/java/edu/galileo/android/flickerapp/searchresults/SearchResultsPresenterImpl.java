package edu.galileo.android.flickerapp.searchresults;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.searchresults.events.SearchResultsEvent;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;

public class SearchResultsPresenterImpl implements SearchResultsPresenter {

    private MyEventBus eventBus;
    private SearchResultsView view;

    private SavePictureInteractor savePictureInteractor;
    private GetNextPictureInteractor getNextPictureInteractor;
    private LoadPicturesInteractor loadPicturesInteractor;

    public SearchResultsPresenterImpl(
            MyEventBus eventBus,
            SearchResultsView view,
            SavePictureInteractor savePictureInteractor,
            GetNextPictureInteractor getNextPictureInteractor,
            LoadPicturesInteractor loadPicturesInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.savePictureInteractor = savePictureInteractor;
        this.getNextPictureInteractor = getNextPictureInteractor;
        this.loadPicturesInteractor = loadPicturesInteractor;
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
    public void getNextImage() {
        if (view != null) {
            view.hidUIElements();
            view.showProgress();
        }
        getNextPictureInteractor.executeGetNext();
    }

    @Override
    public void saveImage(Picture picture) {
        if (view != null) {
            view.hidUIElements();
            view.showProgress();
        }
        savePictureInteractor.executeSave(picture);
    }

    @Override
    @Subscribe
    public void onEventMainThread(SearchResultsEvent event) {
        if (view != null) {
            switch (event.getType()) {
                case SearchResultsEvent.SAVE_EVENT:
                    view.onPictureSaved();
                    getNextPictureInteractor.executeGetNext();
                    break;
                case SearchResultsEvent.GET_NEXT_EVENT:
                    view.showUIElements();
                    view.hideProgress();
                    view.setPictureAndTitle(event.getPicture());
                    break;
                case SearchResultsEvent.ERROR_EVENT:
                    imageError(event.getErrorMsg());
                    break;
                case SearchResultsEvent.NO_MORE_PICS_EVENT:
                    view.noMorePictures(event.getErrorMsg());
                    break;
            }
        }
    }

    @Override
    public void imageError(String error) {
        if (view != null) {
            view.hideProgress();
            view.onPictureError(error);
        }
    }

    @Override
    public void loadImages(String tags) {
        if (view != null) {
            view.hidUIElements();
            view.showProgress();
        }
        loadPicturesInteractor.executeLoading(tags);
    }

    @Override
    public SearchResultsView getView() {
        return view;
    }
}
