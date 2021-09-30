package com.example.wikings.Shehani;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wikings.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddHotelActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_IMAGE = 101;
    private EditText name, address, phone, description, price;
    private Spinner addMenuType;
    private ImageView addHotelImage;
    private TextView textImgProgress;
    private ProgressBar imgProgressBar;
    private Button addHotelButton;

    Uri addImageUri;
    boolean isImageAdded = false;

    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Add Hotel");

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        addMenuType = findViewById(R.id.dropdown_add_hotel_type);
        addHotelImage = findViewById(R.id.add_hotel_image);
        textImgProgress = findViewById(R.id.text_image_progress);
        imgProgressBar = findViewById(R.id.image_progress_bar);
        addHotelButton = findViewById(R.id.btn_hotel_add);

        textImgProgress.setVisibility(View.GONE);
        imgProgressBar.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotels");
        storageReference = FirebaseStorage.getInstance().getReference().child("HotelImages");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        addHotelImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_ADD_IMAGE);
            }
        });

        addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Province = addMenuType.getSelectedItem().toString();
                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
                String Price = price.getText().toString();
                String Description = description.getText().toString();

                if (Name.isEmpty()) {
                    name.setError("Please Enter valid Hotel Name...");
                    name.requestFocus();
                    return;
                }

                if (Province.equals("Select Hotel type")) {
                    Toast.makeText(AddHotelActivity.this, "Please Select Hotel Type...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Address.isEmpty()) {
                    address.setError("Please Enter a address");
                    address.requestFocus();
                    return;
                }

                if (Phone.isEmpty() || Phone.length() != 10) {
                    phone.setError("Please Enter valid Contact number..");
                    phone.requestFocus();
                    return;
                }

                if (Price.isEmpty()) {
                    price.setError("Please Enter price...");
                    price.requestFocus();
                    return;
                }

                if (Description.isEmpty()) {
                    description.setError("Please Enter description...");
                    description.requestFocus();
                    return;
                }

                if (isImageAdded == false) {
                    Toast.makeText(AddHotelActivity.this, "Please select image....", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Double price = 0.0;
                    try {
                        price = Double.parseDouble(Price);
                    } catch (Exception e) {

                    }
                    addHotel(Name, Province, Address, Phone, price, Description);
                }


            }
        });

    }

    private void addHotel(String Name, String Province, String Address, String Phone, Double Price, String Description) {
        textImgProgress.setVisibility(View.VISIBLE);
        imgProgressBar.setVisibility(View.VISIBLE);

        final String key = databaseReference.push().getKey();
        storageReference.child(key + ".jpg").putFile(addImageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (snapshot.getBytesTransferred() * 100) / snapshot.getTotalByteCount();
                imgProgressBar.setProgress((int) progress);
                textImgProgress.setText(progress + "%");
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                textImgProgress.setVisibility(View.INVISIBLE);
                imgProgressBar.setVisibility(View.INVISIBLE);

                storageReference.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("Name", Name);
                        hashMap.put("Price", Price);
                        hashMap.put("Province", Province);
                        hashMap.put("Address", Address);
                        hashMap.put("Phone", Phone);
                        hashMap.put("Description", Description);
                        hashMap.put("ImageUrl", uri.toString());

                        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                displayAlert();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });


                    }
                });


            }
        });
    }

    private void displayAlert() {
        new AlertDialog.Builder(this)
                .setMessage("New hotel added")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), HotelListActivity.class));
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_IMAGE && data != null) {
            addImageUri = data.getData();
            isImageAdded = true;
            addHotelImage.setImageURI(addImageUri);
        }
    }
}
