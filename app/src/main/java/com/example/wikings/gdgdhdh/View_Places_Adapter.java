package com.example.wikings.gdgdhdh;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wikings.R;

import java.util.ArrayList;

public class View_Places_Adapter extends RecyclerView.Adapter<View_Places_Adapter.ViewHolder> {
    ArrayList<uploadinfo> savedPlaces = new ArrayList<>();
    private Context mContext;

    public View_Places_Adapter(ArrayList<uploadinfo> savedPlaces, Context mContext) {
        this.savedPlaces = savedPlaces;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_place,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(savedPlaces.get(position).getImageName());

//            Picasso.get().load(savedPlaces.get(position).getImageURL())
//                    .into(holder.imageView);
//        Glide.with(holder.imageView.getContext()).load(savedPlaces.get(position).getImageURL()).into(holder.imageView);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Add_Places.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RelativeLayout parentLayout;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.img);
            parentLayout = itemView.findViewById(R.id.parentL);


        }
    }


}