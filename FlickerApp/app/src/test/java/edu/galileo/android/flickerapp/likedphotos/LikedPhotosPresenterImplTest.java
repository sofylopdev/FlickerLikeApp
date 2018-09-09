package edu.galileo.android.flickerapp.likedphotos;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.likedphotos.events.PictureListEvent;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

public class LikedPhotosPresenterImplTest extends BaseTest{

    @Mock
    private MyEventBus eventBus;
    @Mock
    private LikedPhotosView view;
    @Mock
    private LikedPhotosInteractor interactor;

    @Mock
    private List<Picture> pictureList;

    private LikedPhotosPresenterImpl presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new LikedPhotosPresenterImpl(eventBus, view, interactor);
    }

    @Test
    public void testPresenterOnCreate_EventBusRegister(){
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testPresenterOnDestroy_EventBusUnregister(){
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void testPresenterGetPictures_InteractorCallExecuteGetPictures(){
        presenter.getPictures();
        verify(interactor).executeGetPictures();
    }

    @Test
    public void testHasPicturesEvent_ViewCallSetPictureList(){
        PictureListEvent event = new PictureListEvent();
        event.setType(PictureListEvent.HAS_PICTURES_EVENT);
        event.setPictureList(pictureList);

        presenter.onEventMainThread(event);
        verify(presenter.getView()).setPicturesList(pictureList);
    }

    @Test
    public void testNoPicturesEvent_ViewCallEmptyDB(){
        PictureListEvent event = new PictureListEvent();
        event.setType(PictureListEvent.NO_PICTURES_EVENT);

        presenter.onEventMainThread(event);
        verify(presenter.getView()).emptyDB();
    }
}
