package edu.galileo.android.flickerapp.picturedetails;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.PICTURE_EXTRA;
import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.TITLE_EXTRA;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class PictureDetailsIntentTest {

    public static final String EXAMPLE_PICTURE_TITLE = "Photo 2018";
    public static final String EXAMPLE_PICTURE_URL = "https://farm2.staticflickr.com/1885/44491700761_60ec65529d.jpg";

    @Rule
    public IntentsTestRule<LikedPhotosActivity> mActivityTestRule =
            new IntentsTestRule<>(LikedPhotosActivity.class);

    @Test
    public void testClickLikedImage_PictureDetailsActivityShouldReceiveIntentWithExtras() {
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        intended(allOf(
                hasComponent(hasShortClassName(".PictureDetailsActivity")),
                hasExtra(TITLE_EXTRA, EXAMPLE_PICTURE_TITLE),
                hasExtra(PICTURE_EXTRA, EXAMPLE_PICTURE_URL)
        ));
    }
}
