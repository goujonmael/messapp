package com.example.messapp2;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView imageView = findViewById(R.id.fullScreenImageView);
        String photoUrl = getIntent().getStringExtra("photoUrl");

        int resourceId = getResources().getIdentifier(photoUrl, "drawable", getPackageName());
        imageView.setImageResource(resourceId);
    }
}
