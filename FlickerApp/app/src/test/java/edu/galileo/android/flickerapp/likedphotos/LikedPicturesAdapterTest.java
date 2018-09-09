package edu.galileo.android.flickerapp.likedphotos;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.LikedPicturesAdapter;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.PictureClickListener;
import edu.galileo.android.flickerapp.support.ShadowRecyclerViewAdapter;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27,
        shadows = {ShadowRecyclerViewAdapter.class})
public class LikedPicturesAdapterTest extends BaseTest {

    private static final String TITLE_MOCK = "Title";
    private static final String URL_MOCK = "http://test.com";

    @Mock
    private List<Picture> pictureList;
    @Mock
    private PictureClickListener listener;
    @Mock
    private ImageLoader imageLoader;

    @Mock
    private Picture picture;

    private LikedPicturesAdapter adapter;
    private ShadowRecyclerViewAdapter shadowAdapter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        when(picture.getImageURL()).thenReturn(URL_MOCK);
        when(picture.getTitle()).thenReturn(TITLE_MOCK);

        adapter = new LikedPicturesAdapter(pictureList, listener, imageLoader);
        shadowAdapter = Shadow.extract(adapter);

        AppCompatActivity activity = Robolectric.setupActivity(AppCompatActivity.class);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    @Test
    public void testSetPictures_ItemCountMatches() {
        int itemCount = 3;
        when(pictureList.size()).thenReturn(itemCount);

        adapter.setPictureList(pictureList);
        assertEquals(itemCount, adapter.getItemCount());
    }

    @Test
    public void testOnItemClick_ShouldCallListener() {
        int positionToClick = 0;
        when(pictureList.get(positionToClick)).thenReturn(picture);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);
        verify(listener).onPictureClick(picture, positionToClick);
    }

    @Test
    public void testViewHolder_ShouldLoadPicture() {
        int positionToShow = 1;
        when(pictureList.get(positionToShow)).thenReturn(picture);

        shadowAdapter.itemVisible(positionToShow);
        View view = shadowAdapter.getViewHolderPosition(positionToShow);
        ImageView imageView = view.findViewById(R.id.image);

        verify(imageLoader).load(imageView, URL_MOCK);
    }
}
