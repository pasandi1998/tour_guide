package com.example.wikings.Malithi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import com.example.wikings.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity
{
    EditText s2,s3,s4,s5,s6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }
    public void process(View view) {



        s2=(EditText)findViewById(R.id.s2);
        s3=(EditText)findViewById(R.id.s3);
        s4=(EditText)findViewById(R.id.s4);
        s5=(EditText)findViewById(R.id.s5);
        s6=(EditText)findViewById(R.id.s6);




        String firstname=s2.getText().toString().trim();
        String lastname=s3.getText().toString().trim();
        String mobileNo=s4.getText().toString().trim();
        String email=s5.getText().toString().trim();
        String password=s6.getText().toString().trim();

        PersonDetails obj=new PersonDetails(lastname,mobileNo,email,password);

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference node=db.getReference("Person Details");

        node.child(firstname).setValue(obj);

        s2.setText("");
        s3.setText("");
        s4.setText("");
        s5.setText("");
        s6.setText("");
        Toast.makeText(getApplicationContext(),"Value Inserted",Toast.LENGTH_LONG).show();






    }
}
