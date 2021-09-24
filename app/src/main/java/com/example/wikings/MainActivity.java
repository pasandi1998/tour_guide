package com.example.wikings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wikings.Pasandi.Add_Places;
import com.example.wikings.Pasandi.Home;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go(View view){
        startActivity(new Intent(getApplicationContext(), Add_Places.class));
    }
}