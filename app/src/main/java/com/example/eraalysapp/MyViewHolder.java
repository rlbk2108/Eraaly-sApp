package com.example.eraalysapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView personPhoto;
    TextView personName, personGroup;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        personName = itemView.findViewById(R.id.person_name);
        personGroup = itemView.findViewById(R.id.major);
        personPhoto = itemView.findViewById(R.id.person_photo);
    }
}
