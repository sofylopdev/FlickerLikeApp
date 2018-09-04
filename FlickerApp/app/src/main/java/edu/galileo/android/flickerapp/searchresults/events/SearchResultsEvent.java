package edu.galileo.android.flickerapp.searchresults.events;

import edu.galileo.android.flickerapp.entities.Picture;

public class SearchResultsEvent {

    private int type;
    private String errorMsg;
    private Picture picture;

    public static final int SAVE_EVENT = 0;
    public static final int GET_NEXT_EVENT = 1;
    public static final int ERROR_EVENT = 2;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
