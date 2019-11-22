package com.knickglobal.yeloopp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.adapters.DeviceImagesAdapter;
import com.knickglobal.yeloopp.adapters.DeviceVideosAdapter;
import com.knickglobal.yeloopp.utils.Common;
import com.knickglobal.yeloopp.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DeviceImagesActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 100;

    private Activity activity;

    private RecyclerView deviceImagesRC;

    private List<String> imagePaths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_images);

        init();

        checkPermission();

    }

    private void init() {
        activity = DeviceImagesActivity.this;

        Button imagesBtn = findViewById(R.id.imagesBtn);
        imagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagePaths.size() > 0) {
                    startActivity(new Intent(activity, NewPostActivity.class)
                            .putExtra("imagesList", (ArrayList<String>) imagePaths));
                    finish();
                } else {
                    Common.showToast(activity, "Please select atleast on image.");
                }
            }
        });

        deviceImagesRC = findViewById(R.id.deviceImagesRC);
        deviceImagesRC.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        deviceImagesRC.addItemDecoration(new SpacesItemDecoration(3, 2, true));
    }

    void showVideos() {
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String data = MediaStore.Video.Media.DATA;

        ArrayList<String> mediaList = Common.getAllMedia(activity, projection, uri, data);
        for (int i = 0; i < mediaList.size(); i++) {
            Log.d("Beant", "showVideos: " + mediaList.get(i));
        }

        deviceImagesRC.setAdapter(new DeviceImagesAdapter(activity, mediaList,
                new DeviceImagesAdapter.ImagesListener() {
                    @Override
                    public void onImageSelect(String path) {
                        imagePaths.add(path);
                    }

                    @Override
                    public void onImageUnSelect(String path) {
                        imagePaths.remove(path);
                    }
                }));
    }

    private void checkPermission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) &&
                    (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            showVideos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        showVideos();
                    } else {
                        Toast.makeText(this, "The app was not allowed to read or" +
                                " write to your storage. Hence, it cannot function properly. " +
                                "Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}