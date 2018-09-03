package edu.galileo.android.flickerapp.main.events;

public class MainActivityEvent {
    private int type;
    private String tags;

    public static final int EMPTY_STRING = 0;
    public static final int NOT_EMPTY_STRING = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
