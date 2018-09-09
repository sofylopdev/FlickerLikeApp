package edu.galileo.android.flickerapp.likedphotos;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.FlickerLikeApp;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.likedphotos.events.PictureListEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class LikedPhotosRepositoryImplTest extends BaseTest {

    @Mock
    private MyEventBus eventBus;

    private LikedPhotosRepositoryImpl repository;

    private FlickerLikeApp app;
    private ArgumentCaptor<PictureListEvent> argumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new LikedPhotosRepositoryImpl(eventBus);
        app = (FlickerLikeApp) RuntimeEnvironment.application;
        argumentCaptor = ArgumentCaptor.forClass(PictureListEvent.class);
        app.onCreate();
    }

    @After
    public void tearDown() {
        app.onTerminate();
    }

    @Test
    public void testGetPicturesFromDbWhenHasPictures_EventPosted() {
        int picturesToStore = 3;
        Picture currentPicture;
        List<Picture> pictureList = new ArrayList<>();
        for (int i = 0; i < picturesToStore; i++) {
            currentPicture = new Picture();
            currentPicture.setImageURL("url" + i);
            currentPicture.save();
            pictureList.add(currentPicture);
        }

        List<Picture> picturesInDB = new Select()
                .from(Picture.class)
                .queryList();

        repository.getPicturesFromDb();

        verify(eventBus).post(argumentCaptor.capture());
        PictureListEvent event = argumentCaptor.getValue();

        assertEquals(PictureListEvent.HAS_PICTURES_EVENT, event.getType());
        assertEquals(picturesInDB, event.getPictureList());

        for (Picture picture : pictureList) {
            picture.delete();
        }
    }

    @Test
    public void testGetPicturesFromDbWhenNoPictures_EventPosted() {

        List<Picture> picturesInDB = new Select()
                .from(Picture.class)
                .queryList();

        repository.getPicturesFromDb();

        verify(eventBus).post(argumentCaptor.capture());
        PictureListEvent event = argumentCaptor.getValue();

        assertEquals(0, picturesInDB.size());
        assertEquals(PictureListEvent.NO_PICTURES_EVENT, event.getType());
        assertNull(event.getPictureList());
    }
}
