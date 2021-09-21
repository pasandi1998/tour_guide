package com.example.wikings.Pasandi;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wikings.MainActivity;
import com.example.wikings.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home<FirebaseAuth> extends AppCompatActivity {
    Button btn;
    EditText name,routeNumber;
    private String Name,RouteNum;
    FirebaseAuth frbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Add Saved Place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.savedName);
        routeNumber = findViewById(R.id.SavedRouteNumber);
    }

    public void addSavedPlace(View view){
        Name = name.getText().toString();
        RouteNum = routeNumber.getText().toString();


        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(RouteNum)){
            Toast.makeText(this, "Please enter route number", Toast.LENGTH_SHORT).show();
        }else {
            SavedPlace savedPlace = new SavedPlace();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Saved_Place");
            savedPlace.setName(Name);
            savedPlace.setRouteNumber(RouteNum);
            databaseReference.push().setValue(savedPlace);
            Toast.makeText(this, "Saved your favourite place successful", Toast.LENGTH_SHORT).show();
        }
    }


}