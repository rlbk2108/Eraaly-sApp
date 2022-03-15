package com.example.eraalysapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    RVadapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        List<Student> students = new ArrayList<Student>();

        students.add(new Student("Эраалы Касымбеков", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Чынгыз Рыскелдиев", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Исламов Исламиддин", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Оморов Эсентур", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Рысбеков Азамат", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Токоев Рысбек", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Иоанн Исаков", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Арсений Радченко", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Азат Асанов", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Турсуналиева Айдайым", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Алина Аманбекова", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Анарбекова Асель", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Лобанов Александр", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Дженалиев Асылбек", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Мааразыков Дастан", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Билал Касымбеков", "LAW-120", R.drawable.no_avatar));
        students.add(new Student("Нуртегин Ниязбеков", "IT-120", R.drawable.no_avatar));
        students.add(new Student("Кымбат Базарбаева", "BA-120", R.drawable.no_avatar));
        students.add(new Student("Амин Касымбеков", "CHN-120", R.drawable.no_avatar));
        students.add(new Student("Эраалы Касымбеков", "IT-120", R.drawable.no_avatar));

        RecyclerView rv = findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RVadapter(getApplicationContext(), students));

        TextView countPlace = findViewById(R.id.textView3);
        int count = students.size();
        countPlace.setText(Integer.toString(count));

    }

    public void getBack(View view) {
        onBackPressed();
    }

}