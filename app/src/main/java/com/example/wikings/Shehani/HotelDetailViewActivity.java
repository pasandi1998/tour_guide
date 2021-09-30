package com.example.wikings.Shehani;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wikings.R;
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

public class HotelDetailViewActivity extends AppCompatActivity {
    ImageView imageView;
    EditText pl_name, pl_price, pl_address, pl_description, pl_phone;
    Spinner pl_type;
    Button delete_btn, update;
    private String Name, description, Address, Province, Phone, Price, plantImgUrl;

    DatabaseReference databaseReference, dataReference, ratingDatabaseReference;
    StorageReference storageReference;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Hotel Details");

        String hotelKey = getIntent().getStringExtra("hotelKey");

        imageView = findViewById(R.id.img_hotel);
        pl_name = findViewById(R.id.text_hotel_name);
        pl_type = findViewById(R.id.province);
        pl_price = findViewById(R.id.price);
        pl_address = findViewById(R.id.address);
        pl_phone = findViewById(R.id.phone);
        pl_description = findViewById(R.id.description);
        delete_btn = findViewById(R.id.btn_delete);
        update = findViewById(R.id.btn_update);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotels");
        ratingDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ratings").child(hotelKey);

        dataReference = FirebaseDatabase.getInstance().getReference().child("Hotels").child(hotelKey);
        storageReference = FirebaseStorage.getInstance().getReference().child("HotelImages").child(hotelKey + ".jpg");


        databaseReference.child(hotelKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Name = snapshot.child("Name").getValue().toString();
                    description = snapshot.child("Description").getValue().toString();
                    Province = snapshot.child("Province").getValue().toString();
                    Address = snapshot.child("Address").getValue().toString();
                    Phone = snapshot.child("Phone").getValue().toString();
                    Price = snapshot.child("Price").getValue().toString();
                    plantImgUrl = snapshot.child("ImageUrl").getValue().toString();

                    Picasso.get().load(plantImgUrl).into(imageView);
                    pl_name.setText(Name);
                    pl_price.setText(Price);
                    Log.e("Province", "Province > " + Province);
                    pl_type.setSelection(getIndex(Province));
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

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskDeleteOption();
                diaBox.show();
            }
        });

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(HotelDetailViewActivity.this, RatingActivity.class);
                    intent.putExtra("hotelKey", hotelKey);
                    startActivity(intent);
                }
                return true;
            }
        });

        ratingDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int ratingCount = 0;
                float totalStars = 0;
                for (DataSnapshot dSnapshot : snapshot.getChildren()) {
                    Float startCount = dSnapshot.child("startCount").getValue(Float.class);
                    ratingCount++;
                    totalStars = totalStars + startCount;
                }


                if(ratingCount == 0){
                    ratingBar.setRating(5);
                }else{
                    ratingBar.setRating(totalStars / ratingCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private int getIndex(String myString) {
        final String[] array = getResources().getStringArray(R.array.province_list);
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private AlertDialog AskDeleteOption() {
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
                                    }
                                });
                                Toast.makeText(HotelDetailViewActivity.this, "Hotel Deleted", Toast.LENGTH_SHORT).show();
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

    public void update() {
        Name = pl_name.getText().toString().trim();
        Address = pl_address.getText().toString();
        Phone = pl_phone.getText().toString();
        Price = pl_price.getText().toString();
        description = pl_description.getText().toString();
        String Province = pl_type.getSelectedItem().toString();


        if (Name.isEmpty()) {
            pl_name.setError("Please Enter valid hotel Name...");
            pl_name.requestFocus();
            return;
        }
        if (Province.equals("Select province")) {
            Toast.makeText(HotelDetailViewActivity.this, "Please Select province...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Address.isEmpty()) {
            pl_address.setError("Enter a address");
            pl_address.requestFocus();
            return;
        }
        if (Phone.isEmpty() || Phone.length() != 10) {
            pl_phone.setError("Please Enter valid phone number...");
            pl_phone.requestFocus();
            return;
        }
        if (Price.isEmpty()) {
            pl_price.setError("Please Enter valid price...");
            pl_price.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            pl_description.setError("Please Enter valid description...");
            pl_description.requestFocus();
            return;
        } else {
            double price = Double.parseDouble(Price);

            System.out.println(Name);

            Map<String, Object> user = new HashMap<>();
            user.put("Name", Name);
            user.put("Description", description);
            user.put("Province", Province);
            user.put("Address", Address);
            user.put("Phone", Phone);
            user.put("Price", price);
            user.put("ImageUrl", plantImgUrl);

            dataReference.updateChildren(user);
            displayAlert();
        }
    }

    private void displayAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Hotel details updated")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

}