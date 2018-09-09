package edu.galileo.android.flickerapp.searchresults;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.main.ui.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static edu.galileo.android.flickerapp.main.ui.MainActivity.TAGS_EXTRA;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class SearchResultsActivityIntentTest {

    public static final String SEARCH_STRING = "search";

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testSearchResultsActivity_SearchResultsActivityShouldReceiveIntentWithExtra() {
        final EditText editText = mActivityTestRule.getActivity().findViewById(R.id.searchTagsEditText);

        mActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText(SEARCH_STRING);
            }
        });

        onView(ViewMatchers.withId(R.id.buttonSearch))
                .perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".searchresults.ui.SearchResultsActivity")),
                hasExtra(TAGS_EXTRA, SEARCH_STRING)
        ));
    }
}
