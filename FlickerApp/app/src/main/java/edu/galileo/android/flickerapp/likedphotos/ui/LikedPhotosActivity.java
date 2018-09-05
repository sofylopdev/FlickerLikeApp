package edu.galileo.android.flickerapp.likedphotos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.galileo.android.flickerapp.FlickerLikeApp;
import edu.galileo.android.flickerapp.PictureDetailsActivity;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosPresenter;
import edu.galileo.android.flickerapp.likedphotos.di.LikedPicturesComponent;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.LikedPicturesAdapter;
import edu.galileo.android.flickerapp.likedphotos.ui.adapters.PictureClickListener;

public class LikedPhotosActivity extends AppCompatActivity implements LikedPhotosView, PictureClickListener {

    public static final String PICTURE_EXTRA = "extra_picture";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LikedPicturesComponent component;

    private LikedPicturesAdapter adapter;
    private LikedPhotosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_photos);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupInjection();
        setupRecycler();
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getPictures();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        FlickerLikeApp app = (FlickerLikeApp) getApplication();
        component = app.getLikedPicturesComponent(this, this, this);
        adapter = getAdapter();
        presenter = getPresenter();
    }

    @Override
    public void emptyDB() {
        String emptyDb = "You haven't liked any pictures yet.";
        Toast.makeText(this, emptyDb, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPicturesList(List<Picture> list) {
        adapter.setPictureList(list);
    }

    @Override
    public void onPictureClick(Picture picture, int position) {
        Intent intent = new Intent(this, PictureDetailsActivity.class);
        intent.putExtra(PICTURE_EXTRA, picture);
        startActivity(intent);
    }


    public LikedPicturesAdapter getAdapter() {
        return component.getAdapter();
    }

    public LikedPhotosPresenter getPresenter() {
        return component.getPresenter();
    }
}
