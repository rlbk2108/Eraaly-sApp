package com.example.eraalysapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    CharSequence buttonText;
    ImageButton myImageButton;

    private EditText mName;
    private ImageButton mGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName=findViewById(R.id.editTextTextPersonName);
        mGo=findViewById(R.id.imageButton3);
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

    public void Send(View view) {
        switch (view.getId()) {
            case R.id.imageButton3:
                Intent intent = new Intent(this, SecondActivity.class);
                //Создаем данные для передачи:
                intent.putExtra("name", mName.getText().toString());
                //Запускаем переход:
                startActivity(intent);
                break;
            default: break;
        }
    }
}