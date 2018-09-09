package edu.galileo.android.flickerapp;

import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class PictureDetailsActivityTest extends BaseTest {

    private static final String TITLE_MOCK = "Title";
    private static final String URL_MOCK = "http://test.com";

    @Mock
    private Picture picture;
    @Mock
    private ImageLoader imageLoader;

    private PictureDetailsActivity activity;
    private ActivityController<PictureDetailsActivity> controller;
    private ShadowActivity shadowActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        PictureDetailsActivity pictureDetailsActivity = new PictureDetailsActivity() {
            public ImageLoader getImageLoader() {
                return imageLoader;
            }

            public Picture getPicture() {
                return picture;
            }
        };
        when(picture.getImageURL()).thenReturn(URL_MOCK);
        when(picture.getTitle()).thenReturn(TITLE_MOCK);
        controller = ActivityController.of(pictureDetailsActivity).create().visible();
        activity = controller.get();
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void activityCreated_ImageAndTextSet() {
        ImageView imageView = activity.findViewById(R.id.picture);
        TextView textView = activity.findViewById(R.id.pictureTitle);

        assertEquals(TITLE_MOCK, textView.getText().toString());
        verify(imageLoader).load(imageView, URL_MOCK);
    }

    @Test
    public void testBackButton_ActivityFinish() {
        shadowActivity.onBackPressed();
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void testMenuClickDelete_PictureDeletedAndActivityFinish() {
        shadowActivity.clickMenuItem(R.id.deleteAction);
        assertFalse(picture.exists());
        assertTrue(shadowActivity.isFinishing());
    }
}
