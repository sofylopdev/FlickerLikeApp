package edu.galileo.android.flickerapp.likedphotos;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.List;

import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.MyEventBus;
import edu.galileo.android.flickerapp.likedphotos.events.PictureListEvent;

public class LikedPhotosRepositoryImpl implements LikedPhotosRepository {

    private MyEventBus eventBus;

    public LikedPhotosRepositoryImpl(MyEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPicturesFromDb() {
        FlowCursorList<Picture> storedPictures =
                new FlowCursorList.Builder<Picture>(Picture.class)
                        .cacheModels(false)
                        .build();

        if (storedPictures.isEmpty()) {
            post(PictureListEvent.NO_PICTURES_EVENT, null);
        } else {
            post(PictureListEvent.HAS_PICTURES_EVENT, storedPictures.getAll());
        }
        storedPictures.close();
    }

    private void post(int type, List<Picture> pictureList) {
        PictureListEvent event = new PictureListEvent();
        event.setType(type);
        event.setPictureList(pictureList);
        eventBus.post(event);
    }
}
