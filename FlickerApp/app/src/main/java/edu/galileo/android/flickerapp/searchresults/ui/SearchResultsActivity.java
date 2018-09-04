package edu.galileo.android.flickerapp.searchresults.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.galileo.android.flickerapp.R;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.pictureTitle)
    TextView pictureTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_results);
        ButterKnife.bind(this);
    }
}
