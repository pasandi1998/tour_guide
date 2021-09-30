package com.example.wikings.Shehani;


import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikings.R;

public class RatingViewHolder extends RecyclerView.ViewHolder {

    RatingBar ratingBar3;
    TextView textView2;
    View v;

    public RatingViewHolder(@NonNull View itemView) {
        super(itemView);
        ratingBar3 = itemView.findViewById(R.id.ratingBar3);
        textView2 = itemView.findViewById(R.id.textView2);
        v = itemView;
    }
}

