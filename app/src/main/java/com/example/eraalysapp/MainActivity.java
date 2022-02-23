package com.example.eraalysapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    CharSequence buttonText;
    ImageButton myImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onMyButtonClick(View view) {
        myButton = findViewById(R.id.button1); // получаем нужную кнопку по ID
        myButton.setText("Kasymbekov"); // изменяем текст кнопки

        // выводим сообщение
        Toast.makeText(this, "I changed it to my last name!", Toast.LENGTH_LONG).show();
    }


    public void onButtonClick(View view) {
        myImageButton = findViewById(R.id.imageButton1); // получаем нужную кнопку по ID
        buttonText = myButton.getText(); // получаем текст кнопки
        if (buttonText == "Kasymbekov") {
            myButton.setText("Eraaly");
            Toast.makeText(this, "Now the text is my first name.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You need to click other button and try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}