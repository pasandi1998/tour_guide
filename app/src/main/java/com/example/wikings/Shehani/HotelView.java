package com.example.wikings.Shehani;
import com.example.wikings.R;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class HotelView extends AppCompatActivity {
    ImageView imageView;
    EditText pl_name,pl_type,pl_price,pl_address,pl_description,pl_phone;
    Button plant_delete_btn,update;
    private String Name,description,Address,Province,Phone,Price,plantImgUrl;

    DatabaseReference databaseReference,dataReference;
    StorageReference  storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_item_view);

        imageView=findViewById(R.id.single_plant_item_img_view);
        pl_name=findViewById(R.id.text_plant_item_name);
        pl_type=findViewById(R.id.provinces);
        pl_price=findViewById(R.id.price);
        pl_address=findViewById(R.id.address);
        pl_phone=findViewById(R.id.phone);
        pl_description=findViewById(R.id.description);
        update = findViewById(R.id.update);
        plant_delete_btn=findViewById(R.id.plant_delete_button);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Hotels");


        String plantKey=getIntent().getStringExtra("plantKey");
        dataReference=FirebaseDatabase.getInstance().getReference().child("Hotels").child(plantKey);
        storageReference= FirebaseStorage.getInstance().getReference().child("HotelImages").child(plantKey+".jpg");


        databaseReference.child(plantKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Name=snapshot.child("Name").getValue().toString();
                    description=snapshot.child("Description").getValue().toString();
                    Province=snapshot.child("Province").getValue().toString();
                    Address=snapshot.child("Address").getValue().toString();
                    Phone=snapshot.child("Phone").getValue().toString();
                    Price=snapshot.child("Price").getValue().toString();
                    plantImgUrl=snapshot.child("ImageUrl").getValue().toString();

                    Picasso.get().load(plantImgUrl).into(imageView);
                    pl_name.setText(Name);
                    pl_price.setText(Price);
//                    pl_type.setText(Province);
                    pl_address.setText(Address);
                    pl_phone.setText(Phone);
                    pl_description.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        plant_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskDeleteOption();
                diaBox.show();

            }
        });
    }

    private AlertDialog AskDeleteOption()
    {
        AlertDialog DeleteDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dataReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        startActivity(new Intent(getApplicationContext(),PlantManagerUserInterface.class));
                                    }
                                });
                                Toast.makeText(HotelView.this, "Plant Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return DeleteDialogBox;
    }

    public void update(){
        Name = pl_name.getText().toString().trim();
        Address = pl_address.getText().toString();
        Phone = pl_phone.getText().toString();
        Price = pl_price.getText().toString();
        description = pl_description.getText().toString();

        if(Name.isEmpty())
        {
            pl_name.setError("Please Enter valid Plant Name...");
            return;
        }
        if(Province.equals("Select plant type"))
        {
            Toast.makeText(HotelView.this, "Please Select Plant Type...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Address.isEmpty())
        {
            pl_address.setError("Enter a price");
            return;
        }
        if(Phone.isEmpty() || Phone.length()!= 10)
        {
            pl_phone.setError("Please Enter valid Plant Name...");
            return;
        }
        if(Price.isEmpty())
        {
            pl_price.setError("Please Enter valid Plant Name...");
            return;
        }
        if(description.isEmpty())
        {
            pl_description.setError("Please Enter valid Plant Name...");
            return;
        }else {
            double price = Double.parseDouble(Price);
            double phn = Double.parseDouble(Phone);
            System.out.println(Name);
            Map<String,Object> user = new HashMap<>();
            user.put("Name",Name);
            user.put("Description",description);
            user.put("Province",Province);
            user.put("Address",Address);
            user.put("Phone",phn);
            user.put("Price",price);
            user.put("ImageUrl",plantImgUrl);

            dataReference.updateChildren(user);
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ManagerUserInterface.class));

        }
    }

}