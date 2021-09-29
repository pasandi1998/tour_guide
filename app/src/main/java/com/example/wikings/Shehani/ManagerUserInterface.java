package com.example.wikings.Shehani;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikings.R;
import com.example.wikings.Shehani.Model.Hotel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ManagerUserInterface extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText searchPlant;
    RecyclerView plantRecycleView;
    FloatingActionButton plant_floating_button;

    FirebaseRecyclerOptions<Hotel> options;
    FirebaseRecyclerAdapter<Hotel,MyViewHolder>adapter;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_manager_user_interface);

        setTitle("Tour Guide");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Hotels");

        searchPlant=findViewById(R.id.plant_search);
        plantRecycleView=findViewById(R.id.plant_recycleview);
        plant_floating_button=findViewById(R.id.add_floating_btn);
        plantRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        plantRecycleView.setHasFixedSize(true);

//        drawerLayout=findViewById(R.id.drawerLayout);
//        navigationView=findViewById(R.id.side_nav2);
//        toolbar=findViewById(R.id.toolbar);




//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();




        plant_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddHotel.class));
            }
        });

        loadData("");
        searchPlant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    loadData(s.toString());
                }else{
                    loadData("");
                }
            }
        });
    }

    private void loadData(String data) {

        Query query=databaseReference.orderByChild("Name").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Hotel>().setQuery(query, Hotel.class).build();
        adapter=new FirebaseRecyclerAdapter<Hotel, MyViewHolder>(options) {
            @SuppressLint("RecyclerView")
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder,int position, @NonNull Hotel model) {
                holder.textViewPlace.setText(model.getName());
                holder.textViewProvince.setText(model.getProvince());
                holder.textViewdescription.setText(String.valueOf(model.getPhone()));
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ManagerUserInterface.this, HotelView.class);
                        intent.putExtra("plantKey",getRef(position).getKey());
                        startActivity(intent);
                   }
              });
            }

           @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_view,parent,false);
              return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        plantRecycleView.setAdapter(adapter);
    }



}