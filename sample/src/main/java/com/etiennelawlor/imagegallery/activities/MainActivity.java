package com.etiennelawlor.imagegallery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.etiennelawlor.imagegallery.R;
import com.etiennelawlor.imagegallery.library.activities.GalleryActivity;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.etiennelawlor.imagegallery.library.models.Image;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by etiennelawlor on 8/20/15.
 */
public class MainActivity extends AppCompatActivity {

    // region Listeners
    @OnClick(R.id.view_gallery_btn)
    public void onViewGalleryButtonClicked(final View v) {
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);

        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image(true,"https://images.unsplash.com/photo-1437422061949-f6efbde0a471?q=80&fm=jpg&w=500&h=500"));
        images.add(new Image(true,"https://images.unsplash.com/photo-1437422061949-f6efbde0a471?q=80&fm=jpg&w=500&h=500"));
        images.add(new Image(true,"https://images.unsplash.com/photo-1429152937938-07b5f2828cdd?q=80&fm=jpg&w=500&h=500"));
        images.add(new Image(false,"/storage/emulated/0/Twnel Images/65505cce-65b2-4135-8dd6-43e71cf8f1f0.jpeg"));
        images.add(new Image(false,"/storage/emulated/0/Twnel Images/3743e1c3-55b6-45a2-9365-57c5c6ec551e.jpeg"));

        intent.putParcelableArrayListExtra("images", images);
        // optionally set background color using Palette
        intent.putExtra("palette_color_type", PaletteColorType.VIBRANT);

        startActivity(intent);
    }
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }
    // endregion
}
