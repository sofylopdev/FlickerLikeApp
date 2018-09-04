package edu.galileo.android.flickerapp.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.flickerapp.FlickerLikeApp;
import edu.galileo.android.flickerapp.likedphotos.LikedPhotosActivity;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.main.MainPresenter;
import edu.galileo.android.flickerapp.main.di.MainActivityComponent;
import edu.galileo.android.flickerapp.searchresults.ui.SearchResultsActivity;

public class MainActivity extends AppCompatActivity implements MainView {


    @BindView(R.id.labelText)
    TextView labelText;
    @BindView(R.id.searchTagsEditText)
    EditText searchTagsEditText;
    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.buttonViewPhotos)
    Button buttonViewPhotos;

    private MainPresenter presenter;
    private MainActivityComponent component;

    public static final String TAGS_EXTRA = "tagToSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupInjection();
        presenter.onCreate();
    }

    private void setupInjection() {
        FlickerLikeApp app = (FlickerLikeApp) getApplication();
        component = app.getMainComponent(this, this);
        presenter = getPresenter();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.buttonSearch)
    public void onButtonSearchClicked() {
        String searchString = searchTagsEditText.getText().toString();
        presenter.search(searchString);
    }

    @OnClick(R.id.buttonViewPhotos)
    public void onButtonViewPhotosClicked() {
        Intent intent = new Intent(this, LikedPhotosActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEmptySearchString() {
        String emptySearchMsg = "Please enter a tag for the search.";
        Toast.makeText(this, emptySearchMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotEmptySearchString(String tags) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(TAGS_EXTRA, tags);
        startActivity(intent);
    }

    public MainPresenter getPresenter() {
        return component.getMainPresenter();
    }
}
