package com.example.eraalysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParsingActivity extends AppCompatActivity {
    TextView textView;
    Button getText;
    String words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        getText = (Button)findViewById(R.id.button10);
        textView = (TextView)findViewById(R.id.textView5);
        getText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewThread().execute();
            }
        });
    }


    public void getBack(View view) {
        onBackPressed();
    }

    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...params) {
            Document doc;
            try {
                doc = Jsoup.connect("https://iuca.kg/ru/sovet-popechitelej/").get();
                //words = doc.text();

                words = String.valueOf(doc.select(".entry-header"));
            } catch(Exception e){e.printStackTrace();}
            return null;
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(words);
            Toast.makeText(ParsingActivity.this, "Text loaded", Toast.LENGTH_SHORT).show();
        }
    }

}