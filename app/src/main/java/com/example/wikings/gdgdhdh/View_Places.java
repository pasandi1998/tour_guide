package com.example.wikings.gdgdhdh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikings.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class View_Places extends AppCompatActivity {
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    String userId;
    View_Places_Adapter placeViewAdapter;
    private ArrayList<uploadinfo> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__places);

        getSupportActionBar().setTitle("VIEW PLACES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Query query1 = FirebaseDatabase.getInstance().getReference("Images");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        uploadinfo savedPlaces = new uploadinfo();
                        System.out.println("OKKKKKKKK------------------------------");
                        System.out.println(dataSnapshot.child("imageURL").getValue().toString());
                        savedPlaces.setImageURL(dataSnapshot.child("imageURL").getValue().toString());
                        savedPlaces.setImageName(dataSnapshot.child("imageName").getValue().toString());
                        places.add(savedPlaces);
                        initRecyclerView();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "You haven't any saved places", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        View_Places_Adapter savedPlaceViewAdapter = new View_Places_Adapter(places,this);
        recyclerView.setAdapter(savedPlaceViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}