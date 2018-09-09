package edu.galileo.android.flickerapp.likedphotos;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.PictureDetailsActivity;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosView;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.LikedPicturesAdapter;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.PictureClickListener;
import edu.galileo.android.flickerapp.support.ShadowRecyclerViewAdapter;

import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.PICTURE_EXTRA;
import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.TITLE_EXTRA;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27,
        shadows = ShadowRecyclerViewAdapter.class)
public class LikedPhotosActivityTest extends BaseTest {

    private static final String TITLE_MOCK = "Title";
    private static final String URL_MOCK = "http://test.com";

    @Mock
    private LikedPicturesAdapter adapter;
    @Mock
    private LikedPhotosPresenter presenter;

    private LikedPhotosActivity activity;
    private LikedPhotosView view;
    private ActivityController<LikedPhotosActivity> controller;

    @Mock
    private List<Picture> pictureList;
    @Mock
    private Picture picture;

    private PictureClickListener pictureClickListener;

    @Mock
    private ImageLoader imageLoader;

    private ShadowRecyclerViewAdapter shadowAdapter;

    private ShadowActivity shadowActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        LikedPhotosActivity likedPhotosActivity = new LikedPhotosActivity() {
            public LikedPicturesAdapter getAdapter() {
                return adapter;
            }

            public LikedPhotosPresenter getPresenter() {
                return presenter;
            }
        };

        when(picture.getTitle()).thenReturn(TITLE_MOCK);
        when(picture.getImageURL()).thenReturn(URL_MOCK);

        controller = ActivityController.of(likedPhotosActivity).create().visible();
        activity = controller.get();
        view = activity;
        pictureClickListener = activity;
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void testActivityOnCreate_PresenterOnCreate() {
        verify(presenter).onCreate();

    }

    @Test
    public void testActivityOnStart_PresenterGetPictures() {
        controller.start();
        verify(presenter).getPictures();
    }

    @Test
    public void testActivityOnDestroy_PresenterOnDestroy() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testEmptyDB_ShowToast() {
        activity.emptyDB();
        String emptyDb = activity.getString(R.string.db_empty);

        assertNotNull(getShadowToasts());
        assertEquals(emptyDb, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testSetPictureList_ShouldSetInAdapter() {
        view.setPicturesList(pictureList);
        verify(adapter).setPictureList(pictureList);
    }

    @Test
    public void testRecyclerViewItemClicked_ShouldStartPictureDetailsActivity() {
        int positionToClick = 0;
        setupShadowAdapter(positionToClick);
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, PictureDetailsActivity.class), intent.getComponent());
        assertEquals(TITLE_MOCK, intent.getStringExtra(TITLE_EXTRA));
        assertEquals(URL_MOCK, intent.getStringExtra(PICTURE_EXTRA));
    }

    private void setupShadowAdapter(int positionToClick) {
        when(pictureList.get(positionToClick)).thenReturn(picture);

        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        LikedPicturesAdapter likedPicturesAdapter = new LikedPicturesAdapter(
                pictureList, pictureClickListener, imageLoader);

        recyclerView.setAdapter(likedPicturesAdapter);
        shadowAdapter = Shadow.extract(recyclerView.getAdapter());
    }

    @Test
    public void testBackButton_ActivityFinish() {
        shadowActivity.onBackPressed();
        assertTrue(shadowActivity.isFinishing());
    }
}
