package edu.galileo.android.flickerapp.searchresults;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsActivity;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsView;
import edu.galileo.android.flickerapp.support.ShadowImageView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27,
        shadows = {ShadowImageView.class})
public class SearchResultsActivityTest extends BaseTest {

    @Mock
    private SearchResultsPresenter presenter;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private Picture picture;

    private ActivityController<SearchResultsActivity> controller;
    private SearchResultsActivity activity;
    private SearchResultsView view;

    private ShadowActivity shadowActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        SearchResultsActivity searchResultsActivity = new SearchResultsActivity() {
            public ImageLoader getImageLoader() {
                return imageLoader;
            }

            public SearchResultsPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(searchResultsActivity).create().visible();
        activity = controller.get();
        view = activity;
        shadowActivity = shadowOf(activity);
        activity.setPicture(picture);
    }

    @Test
    public void testActivityOnCreate_PresenterCallOnCreate() {
        verify(presenter).onCreate();
    }

    @Test
    public void testActivityOnDestroy_PresenterCallOnDestroy() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testShowProgress_ProgressBarVisible() {
        view.showProgress();
        ProgressBar progressBar = activity.findViewById(R.id.progressBar);
        assertEquals(View.VISIBLE, progressBar.getVisibility());
    }

    @Test
    public void testHideProgress_ProgressBarGone() {
        view.hideProgress();
        ProgressBar progressBar = activity.findViewById(R.id.progressBar);
        assertEquals(View.GONE, progressBar.getVisibility());
    }

    @Test
    public void testShowUIElements_UIElementsVisible() {
        view.showUIElements();

        ImageView imageView = activity.findViewById(R.id.picture);
        assertEquals(View.VISIBLE, imageView.getVisibility());

        TextView textView = activity.findViewById(R.id.pictureTitle);
        assertEquals(View.VISIBLE, textView.getVisibility());
    }

    @Test
    public void testHideUIElements_UIElementsGone() {
        view.hidUIElements();

        ImageView imageView = activity.findViewById(R.id.picture);
        assertEquals(View.GONE, imageView.getVisibility());

        TextView textView = activity.findViewById(R.id.pictureTitle);
        assertEquals(View.GONE, textView.getVisibility());
    }

    @Test
    public void testOnPictureSaved_showToast() {
        view.onPictureSaved();
        String savedTextToast = activity.getString(R.string.pic_saved);

        assertNotNull(getShadowToasts());
        assertEquals(savedTextToast, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testOnPictureError_showToast() {
        String errorMsg = "errorMsg";
        view.onPictureError(errorMsg);

        assertNotNull(getShadowToasts());
        assertEquals(errorMsg, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testNoMorePictures_showToastAndActivityFinish() {
        view.noMorePictures();
        String noMorePictures = activity.getString(R.string.no_more_pictures);

        assertNotNull(getShadowToasts());
        assertEquals(noMorePictures, ShadowToast.getTextOfLatestToast());
        assertTrue(activity.isFinishing());
    }

    @Test
    public void testOnSave_PresenterCallSaveImage() {
        activity.onSave();
        assertEquals(picture, activity.getPicture());
        verify(presenter).saveImage(picture);
    }

    @Test
    public void testOnDismiss_PresenterCallGetNextImage() {
        activity.onDismiss();
        verify(presenter).getNextImage();
    }

    @Test
    public void testSetPictureAndTitle_PictureObjectIsSet() {
        picture = null;
        Picture pictureTest = new Picture();
        pictureTest.setTitle("title");
        pictureTest.setImageURL("http://test.com");

        activity.setPictureAndTitle(pictureTest);

        assertEquals(pictureTest.getTitle(), activity.getPicture().getTitle());
        assertEquals(pictureTest.getImageURL(), activity.getPicture().getImageURL());
    }

    @Test
    public void testSetPictureAndTitle_ImageLoaderCalled() {
        Picture pictureTest = new Picture();
        pictureTest.setImageURL("http://test.com");

        activity.setPictureAndTitle(pictureTest);

        ImageView imageView = activity.findViewById(R.id.picture);
        verify(imageLoader).load(imageView, pictureTest.getImageURL());
    }

    @Test
    public void testSetPictureAndTitle_TextViewIsSet() {

        Picture pictureTest = new Picture();
        pictureTest.setTitle("title");

        activity.setPictureAndTitle(pictureTest);

        TextView textView = activity.findViewById(R.id.pictureTitle);
        String expectedTextOnTextView = String.format(activity.getString(R.string.title), pictureTest.getTitle());
        assertEquals(expectedTextOnTextView, textView.getText().toString());
    }


    @Test
    public void testSwipeUp_PresenterCallGetNextImage() {
        ImageView imageView = activity.findViewById(R.id.picture);
        ShadowImageView shadowImageView = Shadow.extract(imageView);
        shadowImageView.performSwipe(200, 200, 210, -200);
        verify(presenter).getNextImage();
    }

    @Test
    public void testSwipeDown_PresenterCallGetNextImage() {
        ImageView imageView = activity.findViewById(R.id.picture);
        ShadowImageView shadowImageView = Shadow.extract(imageView);
        shadowImageView.performSwipe(200, 200, 210, 600);
        verify(presenter).getNextImage();
    }

    @Test
    public void testSwipeRight_PresenterCallSaveImage() {
        ImageView imageView = activity.findViewById(R.id.picture);
        ShadowImageView shadowImageView = Shadow.extract(imageView);
        shadowImageView.performSwipe(200, 200, 500, 210);
        verify(presenter).saveImage(picture);
    }

    @Test
    public void testSwipeLeft_PresenterCallSaveImage() {
        ImageView imageView = activity.findViewById(R.id.picture);
        ShadowImageView shadowImageView = Shadow.extract(imageView);
        shadowImageView.performSwipe(200, 200, -300, 210);
        verify(presenter).saveImage(picture);
    }

    @Test
    public void testBackButton_ActivityFinish() {
        shadowActivity.onBackPressed();
        assertTrue(shadowActivity.isFinishing());
    }
}
