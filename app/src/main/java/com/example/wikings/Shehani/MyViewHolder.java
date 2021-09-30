package com.example.wikings.Shehani;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikings.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textViewPlace, textViewProvince, textViewdescription;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.single_hotel_img_view);
        textViewPlace = itemView.findViewById(R.id.text_hotel_name);
        textViewProvince = itemView.findViewById(R.id.text_province);
        v = itemView;
    }
}

