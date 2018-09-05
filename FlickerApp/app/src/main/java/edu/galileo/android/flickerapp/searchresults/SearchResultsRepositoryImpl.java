package edu.galileo.android.flickerapp.searchresults;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.api.FlickrService;
import edu.galileo.android.flickerapp.api.entities.Photo;
import edu.galileo.android.flickerapp.api.entities.PhotosResponse;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.searchresults.events.SearchResultsEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsRepositoryImpl implements SearchResultsRepository {

    public static final int RESULTS_PER_PAGE = 15;

    private MyEventBus eventBus;
    private FlickrService service;

    private List<Picture> pictureList;

    public SearchResultsRepositoryImpl(MyEventBus eventBus, FlickrService service) {
        this.eventBus = eventBus;
        this.service = service;
        pictureList = new ArrayList<>();
    }

    @Override
    public void loadPictures(String tags) {
        Call<PhotosResponse> call = service.search(tags, RESULTS_PER_PAGE);
        call.enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                if (response.isSuccessful()) {

                    PhotosResponse photosResponse = response.body();
                    if (photosResponse.getPhotos() != null) {
                        List<Photo> photoList = photosResponse.getPhotos().getPhoto();

                        if (photoList != null && !photoList.isEmpty()) {
                            for (Photo photo : photoList) {
                                Picture picture = new Picture();
                                picture.setTitle(photo.getTitle());
                                String pictureUrl = buildPictureUrl(
                                        photo.getFarm(),
                                        photo.getServer(),
                                        photo.getId(),
                                        photo.getSecret());
                                picture.setImageURL(pictureUrl);
                                pictureList.add(picture);
                            }
                        }
                    }

                    post(pictureList.get(0), null, SearchResultsEvent.GET_NEXT_EVENT);
                } else {
                    post(null, response.message(), SearchResultsEvent.ERROR_EVENT);
                }
            }

            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                post(null, t.getLocalizedMessage(), SearchResultsEvent.ERROR_EVENT);
            }
        });
    }

    @Override
    public void getNextPicture() {
        pictureList.remove(0);
        if(pictureList.size() != 0)
        post(pictureList.get(0), null, SearchResultsEvent.GET_NEXT_EVENT);
        else{
            post(null, null, SearchResultsEvent.NO_MORE_PICS_EVENT);
        }

    }

    @Override
    public void savePicture(Picture picture) {
        picture.save();
        post(null, null, SearchResultsEvent.SAVE_EVENT);
    }

    private void post(Picture picture, String errorMsg, int type) {
        SearchResultsEvent event = new SearchResultsEvent();
        event.setErrorMsg(errorMsg);
        event.setPicture(picture);
        event.setType(type);
        eventBus.post(event);
    }

    private String buildPictureUrl(int farmId, String serverId, String pictureId, String secret) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://farm");
        builder.append(farmId);
        builder.append(".staticflickr.com/");
        builder.append(serverId);
        builder.append("/");
        builder.append(pictureId);
        builder.append("_");
        builder.append(secret);
        builder.append(".jpg");

        return builder.toString();
    }
}
