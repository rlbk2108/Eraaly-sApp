package com.example.eraalysapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView1 = findViewById(R.id.textView);

        String Name = getIntent().getStringExtra("name");
        textView1.setText(Name);
    }

    public void getBack(View view) {
        onBackPressed();
    }

    public void mapView(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void webView(View view) {
        WebView browser = new WebView(this);
        setContentView(browser);
        browser.loadUrl("https://iuca.kg/ru/");

        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void callMe(View view) {
        String number = "tel:+996999555302";
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
        startActivity(callIntent);
    }

    public void ListOfStudents(View view) {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
}