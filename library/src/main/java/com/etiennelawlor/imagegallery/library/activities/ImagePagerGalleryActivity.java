package com.etiennelawlor.imagegallery.library.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.etiennelawlor.imagegallery.library.R;
import com.etiennelawlor.imagegallery.library.adapters.GalleryPagerAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.etiennelawlor.imagegallery.library.models.Image;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerGalleryActivity extends AppCompatActivity {

    // region Member Variables
    private GalleryPagerAdapter mFullScreenImageGalleryAdapter;
    private List<Image> mImages;
    private int mPosition;
    private PaletteColorType mPaletteColorType;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    // endregion

    // region Listeners
    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(position);

                setActionBarTitle(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_image_gallery);

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
                mPosition = extras.getInt("position");
            }
        }

        setUpViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mViewPagerOnPageChangeListener);
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

    // region Helper Methods
    private void bindViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpViewPager(){
        ArrayList<Image> images = new ArrayList<>();

        for(Image image : mImages){
            images.add(image);
        }

        mFullScreenImageGalleryAdapter = new GalleryPagerAdapter(images, mPaletteColorType);
        mViewPager.setAdapter(mFullScreenImageGalleryAdapter);
        mViewPager.addOnPageChangeListener(mViewPagerOnPageChangeListener);
        mViewPager.setCurrentItem(mPosition);

        setActionBarTitle(mPosition);
    }

    private void setActionBarTitle(int position) {
        if (mViewPager != null && mImages.size() > 1) {
            int totalPages = mViewPager.getAdapter().getCount();
            getSupportActionBar().setTitle(String.format("%d of %d", (position + 1), totalPages));
        }
    }
    // endregion
}
