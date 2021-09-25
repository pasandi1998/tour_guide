package com.example.wikings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wikings.gdgdhdh.Add_Places;
import com.example.wikings.gdgdhdh.View_Places;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go1(View view){
        startActivity(new Intent(getApplicationContext(), Add_Places.class));
    }
    public void go2(View view){
        startActivity(new Intent(getApplicationContext(), View_Places.class));
    }
}