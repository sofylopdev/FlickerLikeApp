package edu.galileo.android.flickerapp.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import edu.galileo.android.flickerapp.libs.interfaces.ImageLoaderInterface;

public class ImageLoader implements ImageLoaderInterface{

    private RequestManager requestManager;

    public ImageLoader(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public void load(ImageView imageView, String URL) {

        requestManager
                .load(URL)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }
}
