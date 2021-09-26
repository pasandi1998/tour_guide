package Nipuna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wikings.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Addd extends AppCompatActivity
{
    EditText edit_name,edit_namee,edit_nameee,edit_nameeee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addd);
    }



    public void process(android.view.View view)
    {
        {
            edit_name=(EditText)findViewById(R.id.edit_name);
            edit_namee=(EditText)findViewById(R.id.edit_namee);
            edit_nameee=(EditText)findViewById(R.id.edit_nameee);
            edit_nameeee=(EditText)findViewById(R.id.edit_nameeee);

            String roll=edit_name.getText().toString().trim();
            String city=edit_namee.getText().toString().trim();
            String name=edit_nameee.getText().toString().trim();
            String province=edit_nameeee.getText().toString().trim();

            dataholder obj=new dataholder(city,name,province);

            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference node=db.getReference("Add Event");

            node.child(roll).setValue(obj);

            edit_name.setText("");
            edit_namee.setText("");
            edit_nameee.setText("");
            edit_nameeee.setText("");
            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
        }
    }
}