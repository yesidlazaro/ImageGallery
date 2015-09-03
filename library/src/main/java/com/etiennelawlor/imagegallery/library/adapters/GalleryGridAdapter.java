package com.etiennelawlor.imagegallery.library.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.etiennelawlor.imagegallery.library.R;
import com.etiennelawlor.imagegallery.library.models.Image;
import com.etiennelawlor.imagegallery.library.util.ImageGalleryUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Yesid Lazaro on 9/21/15.
 */
public class GalleryGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
    private List<Image> mImages;
    private int mGridItemWidth;
    private int mGridItemHeight;
    private OnImageClickListener mOnImageClickListener;
    // endregion

    // region Interfaces
    public interface OnImageClickListener {
        void onImageClick(View view, int position);
    }
    // endregion

    // region Constructors
    public GalleryGridAdapter(List<Image> images) {
        mImages = images;
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_thumbnail, viewGroup, false);

        v.setLayoutParams(getGridItemLayoutParams(v));

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;

        Image image = mImages.get(position);

        if (image.isFromInternet()) {
            Picasso.with(holder.mImageView.getContext())
                    .load(image.getUrl())
                    .into(holder.mImageView);
        } else {
            Picasso.with(holder.mImageView.getContext())
                    .load(new File(image.getUrl()))
                    .into(holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {
        if (mImages != null) {
            return mImages.size();
        } else {
            return 0;
        }
    }

    // region Helper Methods
    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mOnImageClickListener = listener;
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int screenWidth = ImageGalleryUtils.getScreenWidth(view.getContext());
        int numOfColumns;
        if (ImageGalleryUtils.isInLandscapeMode(view.getContext())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        mGridItemWidth = screenWidth / numOfColumns;
        mGridItemHeight = screenWidth / numOfColumns;

        layoutParams.width = mGridItemWidth;
        layoutParams.height = mGridItemHeight;

        return layoutParams;
    }
    // endregion

    // region Inner Classes

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private FrameLayout mFrameLayout;

        public ImageViewHolder(final View view) {
            super(view);

            mImageView = (ImageView) view.findViewById(R.id.iv);
            mFrameLayout = (FrameLayout) view.findViewById(R.id.fl);

            mFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnImageClickListener != null) {
                        mOnImageClickListener.onImageClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    // endregion
}
