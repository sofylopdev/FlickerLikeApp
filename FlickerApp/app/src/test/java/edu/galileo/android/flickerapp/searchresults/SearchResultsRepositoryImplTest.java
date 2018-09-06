package edu.galileo.android.flickerapp.searchresults;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.galileo.android.flickerapp.BaseTest;
import edu.galileo.android.flickerapp.api.FlickrService;
import edu.galileo.android.flickerapp.api.entities.Photo;
import edu.galileo.android.flickerapp.api.entities.Photos;
import edu.galileo.android.flickerapp.api.entities.PhotosResponse;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.searchresults.events.SearchResultsEvent;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static edu.galileo.android.flickerapp.searchresults.SearchResultsRepositoryImpl.RESULTS_PER_PAGE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchResultsRepositoryImplTest extends BaseTest {

    @Mock
    private MyEventBus eventBus;
    @Mock
    private FlickrService service;

    @Mock
    private List<Picture> pictureList;

    @Mock
    private Picture picture;

    private SearchResultsRepositoryImpl repository;
    private ArgumentCaptor<SearchResultsEvent> argumentCaptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new SearchResultsRepositoryImpl(eventBus, service);
        argumentCaptor = ArgumentCaptor.forClass(SearchResultsEvent.class);
        repository.setPictureList(pictureList);
    }

    @Test
    public void testSavePicture_eventPosted() {
        when(picture.exists()).thenReturn(true);
        repository.savePicture(picture);

        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();

        assertEquals(SearchResultsEvent.SAVE_EVENT, event.getType());
        assertNull(event.getErrorMsg());
        assertNull(event.getPicture());
    }

    @Test
    public void testGetNextPictureListNotEmpty_eventPosted() {
        when(pictureList.size()).thenReturn(1);
        when(pictureList.get(0)).thenReturn(picture);

        repository.getNextPicture();

        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();

        assertEquals(SearchResultsEvent.GET_NEXT_EVENT, event.getType());
        assertNull(event.getErrorMsg());
        assertNotNull(event.getPicture());
    }

    @Test
    public void testGetNextPictureListEmpty_eventPosted() {
        when(pictureList.size()).thenReturn(0);
        repository.getNextPicture();

        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();

        assertEquals(SearchResultsEvent.NO_MORE_PICS_EVENT, event.getType());
        assertNull(event.getErrorMsg());
        assertNull(event.getPicture());
    }

    @Test
    public void testLoadPictureSuccess_eventPosted() {
        String tags = "tags";
        String errorMsg = "error";
        when(service.search(tags, RESULTS_PER_PAGE))
                .thenReturn(buildCall(true, false, errorMsg));
        when(repository.getPictureList().get(0)).thenReturn(picture);

        repository.loadPictures(tags);
        verify(service).search(tags, RESULTS_PER_PAGE);
        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();

        assertEquals(SearchResultsEvent.GET_NEXT_EVENT, event.getType());
        assertNull(event.getErrorMsg());
        assertNotNull(event.getPicture());
    }

    @Test
    public void testLoadPictureFail_eventPosted() {
        String tags = "tags";
        String errorMsg = "error";
        when(service.search(tags, RESULTS_PER_PAGE))
                .thenReturn(buildCall(false, false, errorMsg));

        repository.loadPictures(tags);
        verify(service).search(tags, RESULTS_PER_PAGE);
        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();
        assertEquals(SearchResultsEvent.ERROR_EVENT, event.getType());
        assertNotNull(event.getErrorMsg());
        assertNull(event.getPicture());
        assertEquals(errorMsg, event.getErrorMsg());
    }

    @Test
    public void testLoadPictureNoMorePics_eventPosted() {
        String tags = "tags";
        String errorMsg = "error";

        when(service.search(tags, RESULTS_PER_PAGE))
                .thenReturn(buildCall(true, true, errorMsg));

        repository.loadPictures(tags);

        verify(service).search(tags, RESULTS_PER_PAGE);
        verify(eventBus).post(argumentCaptor.capture());

        SearchResultsEvent event = argumentCaptor.getValue();

        assertEquals(SearchResultsEvent.NO_MORE_PICS_EVENT, event.getType());
        assertNull(event.getErrorMsg());
        assertNull(event.getPicture());
    }


    private Call<PhotosResponse> buildCall(final boolean success, final boolean listEmpty, final String errorMsg) {
        Call<PhotosResponse> response = new Call<PhotosResponse>() {
            @Override
            public Response<PhotosResponse> execute() throws IOException {
                Response<PhotosResponse> result = null;

                if (success) {
                    if (listEmpty) {
                        PhotosResponse response = new PhotosResponse();
                        Photos photos = new Photos();
                        photos.setPhoto(null);
                        response.setPhotos(photos);

                        result = Response.success(response);
                    } else {
                        PhotosResponse response = new PhotosResponse();
                        Photos photos = new Photos();
                        Photo photo = new Photo();
                        photo.setFarm(1);
                        photo.setServer("1");
                        photo.setId("1");
                        photo.setSecret("s");
                        photo.setTitle("title");
                        photos.setPhoto(Arrays.asList(photo));
                        response.setPhotos(photos);

                        result = Response.success(response);
                    }
                } else {
                    result = Response.error(null, null);
                }
                return result;
            }

            @Override
            public void enqueue(Callback<PhotosResponse> callback) {
                if (success) {
                    try {
                        callback.onResponse(this, execute());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    callback.onFailure(this, new Throwable(errorMsg));
                }
            }

            @Override
            public boolean isExecuted() {
                return true;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<PhotosResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        return response;
    }
}
