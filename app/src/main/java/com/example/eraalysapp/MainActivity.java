package com.example.eraalysapp;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Looper.prepare;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
    private SharedPreferences shp;
    private EditText editSave;
    private MediaController controller;

    private final String save_key = "save_key";
    private static final int Pick_image = 1;
    private static final int Pick_video = 2;
    public static final int RequestPermissionCode = 3;
    private static String audioFileName = null;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Random random;
    ImageView imagePreview;
    VideoView videoPreview;
    MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer;
    private FloatingActionButton floatingActionButton1,
            floatingActionButton2,
            floatingActionButton3,
            clearTextField,
            startRecord,
            stopRecord,
            playRecorded;
    private ImageButton imageButton, videoClose;
    private Button save, get, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioFileName = getExternalCacheDir().getAbsolutePath();
        audioFileName += "/audiorecordtest.3gp";

        floatingActionButton1 = findViewById(R.id.floatingActionButton);
        floatingActionButton2 = findViewById(R.id.floatingActionButton3);
        floatingActionButton3 = findViewById(R.id.floatingActionButton4);
        videoClose = findViewById(R.id.button9);
        imageButton = findViewById(R.id.imageButton2);
        imagePreview = findViewById(R.id.imagePreview);
        videoPreview = findViewById(R.id.videoView);
        startRecord = findViewById(R.id.record);
        stopRecord = findViewById(R.id.stop);
        playRecorded = findViewById(R.id.play);
        clearTextField = findViewById(R.id.floatingActionButton2);

        save = findViewById(R.id.button3);
        get = findViewById(R.id.button7);
        delete = findViewById(R.id.button8);

        random = new Random();

        if (this.controller == null) {
            this.controller = new MediaController(MainActivity.this);
        }
        controller.setAnchorView(videoPreview);
        videoPreview.setMediaController(controller);
        init();


        startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(checkPermission()) {
                try {
                    MediaRecorderReady();
                    myAudioRecorder.prepare();

                    Toast.makeText(MainActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                    startRecord.setEnabled(false);
                    stopRecord.setEnabled(true);
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    }
                } else {
                    requestPermission();
                }
                myAudioRecorder.start();
            }
        });

        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAudioRecorder.stop();
                stopRecord.setEnabled(false);
                playRecorded.setEnabled(true);
                startRecord.setEnabled(true);

                Toast.makeText(MainActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });
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

    public void textClear(View view){
        editSave.setText("");
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

    public void startAudio(View view) {
        editSave.setVisibility(View.INVISIBLE);
        get.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        clearTextField.setVisibility(View.INVISIBLE);

        startRecord.setVisibility(View.VISIBLE);
        stopRecord.setVisibility(View.VISIBLE);
        playRecorded.setVisibility(View.VISIBLE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                imagePreview.setImageBitmap(captureImage);
                Toast.makeText(this, "Image taken!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 2){
            Uri videoUri = data.getData();
            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoURI(videoUri);

            videoClose.setVisibility(View.VISIBLE);

            floatingActionButton1.setVisibility(View.INVISIBLE);
            floatingActionButton2.setVisibility(View.INVISIBLE);
            floatingActionButton3.setVisibility(View.INVISIBLE);
            imageButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Video captured!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPicture(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
        startActivityForResult(intent, Pick_image);
    }

    public void getVideo(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, Pick_video);
    }


    public void closeVideo(View view) {
        videoPreview.setVisibility(View.INVISIBLE);
        videoClose.setVisibility(View.INVISIBLE);
        floatingActionButton1.setVisibility(View.VISIBLE);
        floatingActionButton2.setVisibility(View.VISIBLE);
        floatingActionButton3.setVisibility(View.VISIBLE);
        imageButton.setVisibility(View.VISIBLE);
    }


    private void MediaRecorderReady() {
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setOutputFile(audioFileName);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void startPlay(View view) throws IllegalArgumentException, SecurityException, IllegalStateException {
            stopRecord.setEnabled(false);
            startRecord.setEnabled(true);
            playRecorded.setEnabled(true);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioFileName);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            Toast.makeText(MainActivity.this, "Recording Playing",
                    Toast.LENGTH_SHORT).show();
        }


    public void stopAudio(View view) {
        myAudioRecorder.stop();
        stopRecord.setEnabled(false);
        playRecorded.setEnabled(true);
        startRecord.setEnabled(true);

        Toast.makeText(MainActivity.this, "Recording Completed",
                Toast.LENGTH_LONG).show();
    }
}
    


