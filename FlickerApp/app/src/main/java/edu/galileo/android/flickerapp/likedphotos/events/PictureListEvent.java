package edu.galileo.android.flickerapp.likedphotos.events;

import java.util.List;

import edu.galileo.android.flickerapp.entities.Picture;

public class PictureListEvent {
    private int type;
    private List<Picture> pictureList;

    public static final int HAS_PICTURES_EVENT = 0;
    public static final int NO_PICTURES_EVENT = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }
}
