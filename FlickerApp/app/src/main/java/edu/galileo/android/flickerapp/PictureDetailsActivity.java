package edu.galileo.android.flickerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;
import edu.galileo.android.flickerapp.libs.di.LibsComponent;

import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.PICTURE_EXTRA;
import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.TITLE_EXTRA;

public class PictureDetailsActivity extends AppCompatActivity {

    @BindView(R.id.picture)
    ImageView pictureView;
    @BindView(R.id.pictureTitle)
    TextView pictureTitle;

    private Picture picture;

    private ImageLoader imageLoader;
    private LibsComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupInjection();

        if (getIntent() != null) {
           // picture = (Picture) getIntent().getSerializableExtra(PICTURE_EXTRA);
            String title = getIntent().getStringExtra(TITLE_EXTRA);
            String imageURL = getIntent().getStringExtra(PICTURE_EXTRA);
            pictureTitle.setText(title);
            imageLoader.load(pictureView, imageURL);
        }else {
            pictureTitle.setText(picture.getTitle());
            if (imageLoader != null)
                imageLoader.load(pictureView, picture.getImageURL());
        }
    }

    private void setupInjection() {
        FlickerLikeApp app = (FlickerLikeApp) getApplication();
        component = app.getLibsComponent(this);
        imageLoader = getImageLoader();
        picture = getPicture();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAction) {
            picture.delete();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Picture getPicture() {
        return picture;
    }

    public ImageLoader getImageLoader() {
        return component.getImageLoader();
    }
}
