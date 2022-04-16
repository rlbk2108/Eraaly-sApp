package com.example.eraalysapp;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity{
    private SharedPreferences shp;
    private EditText editSave;
    private final String save_key = "save_key";
    public static final String MyPREFERENCES = "MyPre";
    Bitmap bitmap;
    Camera camera;
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private CameraManager mCameraManager = null;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void onClickSave(View view) {
        SharedPreferences.Editor edit = shp.edit();
        edit.putString(save_key, editSave.getText().toString());
        edit.apply();
        Toast.makeText(MainActivity.this, "Text Saved", Toast.LENGTH_SHORT).show();
    }

    public void onClickGet(View view) {
        editSave.setText(shp.getString(save_key, "empty"));
        Toast.makeText(MainActivity.this, "Text Loaded", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        shp = getSharedPreferences("dataRow", MODE_PRIVATE);
        editSave = findViewById(R.id.editTextTextPersonName);
        editSave.setText(shp.getString(save_key, "Data is empty"));
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void onClickDelete(View view) {
        SharedPreferences.Editor edit = shp.edit();
        edit.clear();
        edit.apply();
        Toast.makeText(MainActivity.this, "Text Deleted", Toast.LENGTH_SHORT).show();
    }

    public void openCamera(View view) {
        if (hasCameraPermission()) {
            enableCamera();
        } else {
            requestPermission();
        }
    }
}


