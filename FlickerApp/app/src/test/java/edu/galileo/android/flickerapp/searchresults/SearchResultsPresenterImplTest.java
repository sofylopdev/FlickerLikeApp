package edu.galileo.android.flickerapp.searchresults;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.searchresults.events.SearchResultsEvent;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchResultsPresenterImplTest extends BaseTest {

    @Mock
    private MyEventBus eventBus;
    @Mock
    private SearchResultsView view;
    @Mock
    private SavePictureInteractor savePictureInteractor;
    @Mock
    private GetNextPictureInteractor getNextPictureInteractor;
    @Mock
    private LoadPicturesInteractor loadPicturesInteractor;

    private SearchResultsPresenterImpl presenter;

    @Mock
    private Picture picture;

    @Mock
    private SearchResultsEvent event;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new SearchResultsPresenterImpl(eventBus, view, savePictureInteractor, getNextPictureInteractor, loadPicturesInteractor);
    }

    @Test
    public void testOnCreate_EventBusSubscribe() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_EventBusUnSubscribe() {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }


    @Test
    public void testGetNextImage_GetNextInteractorCallExecuteNext() {
        presenter.getNextImage();

        assertNotNull(presenter.getView());
        verify(view).hidUIElements();
        verify(view).showProgress();
        verify(getNextPictureInteractor).executeGetNext();
    }

    @Test
    public void testSaveImage_SaveInteractorCallExecuteSave() {
        presenter.saveImage(picture);

        assertNotNull(presenter.getView());
        verify(view).hidUIElements();
        verify(view).showProgress();
        verify(savePictureInteractor).executeSave(picture);
    }

    @Test
    public void testImageError_ViewShowError() {
        String errorMsg = "error";
        presenter.imageError(errorMsg);

        assertNotNull(presenter.getView());
        verify(view).hideProgress();
        verify(view).onPictureError(errorMsg);
    }

    @Test
    public void testLoadImages_LoadInteractorCallExecuteLoad() {
        String tags = "tags";
        presenter.loadImages(tags);

        assertNotNull(presenter.getView());
        verify(view).hidUIElements();
        verify(view).showProgress();
        verify(loadPicturesInteractor).executeLoading(tags);
    }

    @Test
    public void testSaveEvent_ViewCallPictureSavedAndInteractorCallNext() {
        when(event.getType()).thenReturn(SearchResultsEvent.SAVE_EVENT);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).onPictureSaved();
        verify(getNextPictureInteractor).executeGetNext();
    }

    @Test
    public void testGetNextEvent_ViewCallHideProgressShowElementsSetPicAndTitle() {
        when(event.getType()).thenReturn(SearchResultsEvent.GET_NEXT_EVENT);
        when(event.getPicture()).thenReturn(picture);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).showUIElements();
        verify(view).hideProgress();
        verify(view).setPictureAndTitle(picture);
    }

    @Test
    public void testErrorEvent_ViewCallPictureError() {
        String errorMsg = "errorMsg";
        when(event.getType()).thenReturn(SearchResultsEvent.ERROR_EVENT);
        when(event.getErrorMsg()).thenReturn(errorMsg);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).onPictureError(errorMsg);
        verify(view).hideProgress();
    }

    @Test
    public void testNoMorePicsEvent_ViewCallNoMorePictures() {
        when(event.getType()).thenReturn(SearchResultsEvent.NO_MORE_PICS_EVENT);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).noMorePictures();
    }
}
