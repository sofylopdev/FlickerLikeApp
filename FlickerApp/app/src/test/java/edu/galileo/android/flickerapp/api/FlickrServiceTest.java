package edu.galileo.android.flickerapp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.BuildConfig;
import edu.galileo.android.flickerapp.api.entities.Photo;
import edu.galileo.android.flickerapp.api.entities.PhotosResponse;
import retrofit2.Call;
import retrofit2.Response;

import static edu.galileo.android.flickerapp.searchresults.SearchResultsRepositoryImpl.RESULTS_PER_PAGE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class FlickrServiceTest extends BaseTest{
    private FlickrService service;
    public static final String ONE_TAG = "cats";
    public static final String TWO_TAGS = "cats, dogs";
    public static final String INVALID_TAG = "gfkgrln";

    @Override
    public void setUp() throws Exception {
        super.setUp();

        FlickerClient client = new FlickerClient();
        service = client.getFlickrService();
    }

    @Test
    public void searchOneTag_getPhotosFromApi() throws Exception{
        Call<PhotosResponse> call = service.search(ONE_TAG, RESULTS_PER_PAGE);
        Response<PhotosResponse> response = call.execute();
        assertTrue(response.isSuccessful());
        List<Photo> photoList = response.body().getPhotos().getPhoto();
        assertEquals(RESULTS_PER_PAGE, photoList.size());
        Photo photo = photoList.get(0);
        assertNotNull(photo);
    }

    @Test
    public void searchTwoTags_getPhotosFromApi() throws Exception{
        Call<PhotosResponse> call = service.search(TWO_TAGS, RESULTS_PER_PAGE);
        Response<PhotosResponse> response = call.execute();
        assertTrue(response.isSuccessful());
        List<Photo> photoList = response.body().getPhotos().getPhoto();
        assertEquals(RESULTS_PER_PAGE, photoList.size());
        Photo photo = photoList.get(0);
        assertNotNull(photo);
    }

    @Test
    public void searchInvalidTag_getPhotosFromApi() throws Exception{
        Call<PhotosResponse> call = service.search(INVALID_TAG, RESULTS_PER_PAGE);
        Response<PhotosResponse> response = call.execute();
        assertTrue(response.isSuccessful());
        List<Photo> photoList = response.body().getPhotos().getPhoto();
        assertEquals(0, photoList.size());
    }
}
