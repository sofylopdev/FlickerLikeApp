package edu.galileo.android.flickerapp.searchresults.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.galileo.android.flickerapp.FlickerLikeApp;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.searchresults.SearchResultsPresenter;
import edu.galileo.android.flickerapp.searchresults.di.SearchActivityComponent;

import static edu.galileo.android.flickerapp.main.ui.MainActivity.TAGS_EXTRA;

public class SearchResultsActivity extends AppCompatActivity implements SearchResultsView, SwipeGestureListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.picture)
    ImageView pictureView;
    @BindView(R.id.pictureTitle)
    TextView pictureTitle;

    private SearchResultsPresenter presenter;
    private SearchActivityComponent component;
    private ImageLoader imageLoader;

    private Picture picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_results);
        ButterKnife.bind(this);
        setupInjection();
        setupGestureDetector();
        presenter.onCreate();

        Intent intent = getIntent();
        if (intent.getStringExtra(TAGS_EXTRA) != null) {
            presenter.loadImages(intent.getStringExtra(TAGS_EXTRA));
        }
    }

    private void setupGestureDetector() {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //This way we are connecting the events
                return gestureDetector.onTouchEvent(event);
            }
        };
        pictureView.setOnTouchListener(gestureOnTouchListener);
    }

    private void setupInjection() {
        FlickerLikeApp app = (FlickerLikeApp) getApplication();
        component = app.getSearchActivityComponent(this, this);
        presenter = getPresenter();
        imageLoader = getImageLoader();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        pictureView.setVisibility(View.VISIBLE);
        pictureTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidUIElements() {
        pictureView.setVisibility(View.GONE);
        pictureTitle.setVisibility(View.GONE);
    }



    @Override
    public void onPictureSaved() {
        String savedTextToast = "Picture saved.";
        Toast.makeText(this, savedTextToast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPictureAndTitle(Picture picture) {
        this.picture = picture;
        imageLoader.load(pictureView, picture.getImageURL());
        pictureTitle.setText(String.format("Title: %s", picture.getTitle()));
    }

    @Override
    public void onPictureError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noMorePictures(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    public ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    public SearchResultsPresenter getPresenter() {
        return component.getPresenter();
    }

    @Override
    public void onSave() {
        presenter.saveImage(picture);
    }

    @Override
    public void onDismiss() {
        presenter.getNextImage();
    }
}
