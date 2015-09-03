package com.etiennelawlor.imagegallery.library.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.etiennelawlor.imagegallery.library.R;
import com.etiennelawlor.imagegallery.library.adapters.GalleryGridAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.etiennelawlor.imagegallery.library.models.Image;
import com.etiennelawlor.imagegallery.library.util.ImageGalleryUtils;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryGridAdapter.OnImageClickListener {

    // region Member Variables
    private ArrayList<Image> mImages;
    private PaletteColorType mPaletteColorType;
    private GalleryGridAdapter mImageGalleryGridAdapter;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_gallery);

        bindViews();

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                mImages= extras.getParcelableArrayList("images");
                mPaletteColorType = (PaletteColorType) extras.get("palette_color_type");
            }
        }

        setUpRecyclerView();
    }
    // endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setUpRecyclerView();
    }

    // region ImageGalleryAdapter.OnImageClickListener Methods
    @Override
    public void onImageClick(View view, int position) {
        Intent intent = new Intent(GalleryActivity.this, ImagePagerGalleryActivity.class);
        intent.putParcelableArrayListExtra("images", mImages);
        intent.putExtra("position", position);
        if(mPaletteColorType != null){
            intent.putExtra("palette_color_type", mPaletteColorType);
        }

        startActivity(intent);
    }
    // endregion

    // region Helper Methods
    private void bindViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpRecyclerView(){
        int numOfColumns;
        if(ImageGalleryUtils.isInLandscapeMode(this)){
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(GalleryActivity.this, numOfColumns));
        mImageGalleryGridAdapter = new GalleryGridAdapter(mImages);
        mImageGalleryGridAdapter.setOnImageClickListener(this);

        mRecyclerView.setAdapter(mImageGalleryGridAdapter);
    }
    // endregion
}
