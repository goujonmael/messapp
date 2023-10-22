package com.example.messapp2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements PhotoAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<String> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        photoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            photoList.add("photo");
        }

        photoAdapter = new PhotoAdapter(photoList, this);
        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onItemClick(String photoUrl) {
        // Launch a new activity or dialog to display the image in full size
        Intent intent = new Intent(this, FullScreenImageActivity.class);
        intent.putExtra("photoUrl", photoUrl);
        startActivity(intent);
    }
}
