package edu.galileo.android.flickerapp.main;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity;
import edu.galileo.android.flickerapp.main.events.MainActivityEvent;
import edu.galileo.android.flickerapp.main.ui.MainActivity;
import edu.galileo.android.flickerapp.main.ui.MainView;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class MainActivityTest extends BaseTest {

    public static final String EMPTY_TAG = "";
    public static final String NOT_EMPTY_TAG = "dogs";

    @Mock
    private MainPresenter presenter;

    private MainView view;
    private MainActivity activity;

    private ActivityController<MainActivity> controller;
    private ShadowActivity shadowActivity;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        MainActivity mainActivity = new MainActivity() {
            public MainPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(mainActivity).create().visible();
        activity = controller.get();
        view = activity;
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void testActivityCreated_presenterCreated() {
        verify(presenter).onCreate();
    }

    @Test
    public void testActivityDestroyed_presenterDestroyed() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testButtonViewPhotosClicked_ShouldLaunchLikedPhotosActivity() {
        Button button = activity.findViewById(R.id.buttonViewPhotos);
        button.performClick();
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, LikedPhotosActivity.class), intent.getComponent());
    }

    @Test
    public void testButtonSearchClickedAndTagEmpty_PresenterShouldCallOnSearchUsingEMPTY_TAG() {
        EditText editText =  (EditText) activity.findViewById(R.id.searchTagsEditText);
        String tags = editText.getText().toString();
        assertEquals(EMPTY_TAG, tags);

        Button button = activity.findViewById(R.id.buttonSearch);
        button.performClick();
        verify(presenter).search(tags);
    }

    @Test
    public void testButtonSearchClickedAndTagNotEmpty_PresenterShouldCallOnSearchUsingNOT_EMPTY_TAG() {
        EditText editText = (EditText) shadowActivity.findViewById(R.id.searchTagsEditText);
        editText.setText(NOT_EMPTY_TAG);
        String tags = editText.getText().toString();
        assertEquals(NOT_EMPTY_TAG, tags);

        Button button = (Button) shadowActivity.findViewById(R.id.buttonSearch);
        button.performClick();
        verify(presenter).search(NOT_EMPTY_TAG);
    }
}
