package edu.galileo.android.flickerapp.likedphotos.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import edu.galileo.android.flickerapp.R;
import edu.galileo.android.flickerapp.entities.Picture;
import edu.galileo.android.flickerapp.libs.ImageLoader;

public class LikedPicturesAdapter extends RecyclerView.Adapter<LikedPicturesAdapter.LikedPicturesViewHolder> {

    private List<Picture> pictureList;
    private PictureClickListener listener;
    private ImageLoader imageLoader;

    public LikedPicturesAdapter(List<Picture> pictureList, PictureClickListener listener, ImageLoader imageLoader) {
        this.pictureList = pictureList;
        this.listener = listener;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public LikedPicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.liked_pictures_item,
                parent,
                false);

        return new LikedPicturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedPicturesViewHolder holder, int position) {
        String imgUrl = pictureList.get(position).getImageURL();
        imageLoader.load(holder.image, imgUrl);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public void setPictureList(List<Picture> list){
        pictureList = list;
        notifyDataSetChanged();
    }

    public class LikedPicturesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        public LikedPicturesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onPictureClick(v, getAdapterPosition());
        }
    }
}
