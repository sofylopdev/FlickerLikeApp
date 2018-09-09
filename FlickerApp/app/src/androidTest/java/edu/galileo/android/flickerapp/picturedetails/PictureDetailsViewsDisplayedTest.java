package edu.galileo.android.flickerapp.picturedetails;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PictureDetailsViewsDisplayedTest {

    public static final String EXAMPLE_PICTURE_TITLE = "Photo 2018";

    @Rule
    public ActivityTestRule<LikedPhotosActivity> mActivityTestRule =
            new ActivityTestRule<>(LikedPhotosActivity.class);

    @Test
    public void testClickLikedImage_PictureDetailsShouldDisplayTitleAndImage() {
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(ViewMatchers.withId(R.id.pictureTitle)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.picture)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.pictureTitle)).check(matches(withText(EXAMPLE_PICTURE_TITLE)));
    }
}
