package com.example.APBook;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FillScreenImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_screen_image_activity);
        String url = getIntent().getStringExtra("url");
        ImageView imageView = findViewById(R.id.full_screen_image);
        Glide.with(getApplicationContext())
                .load(url)
                .into(imageView);
    }

}
