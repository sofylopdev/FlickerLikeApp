package edu.galileo.android.flickerapp.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = PicturesDatabase.NAME, version = PicturesDatabase.VERSION)
public class PicturesDatabase {
    public static final int VERSION = 1;
    public static final String NAME = "LikedPictures";
}
