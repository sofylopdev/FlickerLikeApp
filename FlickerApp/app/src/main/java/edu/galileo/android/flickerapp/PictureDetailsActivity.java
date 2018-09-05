package edu.galileo.android.flickerapp;

import android.content.Intent;
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

import static edu.galileo.android.flickerapp.likedphotos.ui.LikedPhotosActivity.PICTURE_EXTRA;

public class PictureDetailsActivity extends AppCompatActivity {

    @BindView(R.id.picture)
    ImageView pictureView;
    @BindView(R.id.pictureTitle)
    TextView pictureTitle;

    private Picture picture;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupInjection();

        Intent intent = getIntent();
        picture = (Picture) intent.getSerializableExtra(PICTURE_EXTRA);

        pictureTitle.setText(picture.getTitle());
        if (imageLoader != null)
            imageLoader.load(pictureView, picture.getImageURL());
    }

    private void setupInjection() {
        FlickerLikeApp app = (FlickerLikeApp) getApplication();
        imageLoader = app.getLibsComponent(this).getImageLoader();
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
}
