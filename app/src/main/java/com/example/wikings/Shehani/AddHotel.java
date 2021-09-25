package com.example.wikings.Shehani;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHotel extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_IMAGE = 101;
    private EditText name, address,phone,description,price;
    private Spinner addMenuType;
    private ImageView addPlantImage;
    private TextView textImgProgress;
    private ProgressBar imgProgressBar;
    private Button addPlantButton;

    Uri addImageUri, dbIMG;
    boolean isImageAdded = false;

    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        addMenuType = findViewById(R.id.dropdown_add_plant_type);
        addPlantImage = findViewById(R.id.add_plant_image);
        textImgProgress = findViewById(R.id.text_image_progress);
        imgProgressBar = findViewById(R.id.image_progress_bar);
        addPlantButton = findViewById(R.id.btn_plant_add);

        textImgProgress.setVisibility(View.GONE);
        imgProgressBar.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotels");
        storageReference = FirebaseStorage.getInstance().getReference().child("HotelImages");


    }

    @Override
    protected void onResume() {
        super.onResume();

        addPlantImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_ADD_IMAGE);
            }
        });

        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String Name = name.getText().toString().trim();
                 String Province = addMenuType.getSelectedItem().toString();
                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
                String Price =price.getText().toString();
                String Description = description.getText().toString();



                Pattern pattern = Pattern.compile("[^a-zA-Z] ");
                Matcher matcher = pattern.matcher(Name);
                boolean isStringContainsSpecialCharacter = matcher.find();

//                if(isStringContainsSpecialCharacter || TextUtils.isEmpty(Place))
                if(Name.isEmpty())
                {
                    name.setError("Please Enter valid Hotel Name...");
                    return;
                }
                 if(Province.equals("Select Hotel type"))
                {
                    Toast.makeText(AddHotel.this, "Please Select Hotel Type...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Address.isEmpty())
                {
                    address.setError("Please Enter a price");
                    return;
                }
                 if(Phone.isEmpty() || Phone.length()!= 10)
                {
                    phone.setError("Please Enter valid Contact number..");
                    return;
                }
                 if(Price.isEmpty())
                {
                    price.setError("Please Enter price...");
                    return;
                }
                 if(Description.isEmpty())
                {
                    description.setError("Please Enter description...");
                    return;
                }
                if(isImageAdded==false)
                {
                    Toast.makeText(AddHotel.this, "Please select image....", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Double phone=0.0,price=0.0;
                    try{
                        phone = Double.parseDouble(Phone);
                        price = Double.parseDouble(Price);
                    }catch(Exception e) {

                    }
                    addPlant(Name,Province,Address,phone,price,Description);
                }



            }
        });


    }

    private void addPlant(String Name,String Province,String Address,Double Phone,Double Price,String Description) {
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
                                startActivity(new Intent(getApplicationContext(), ManagerUserInterface.class));
                                Toast.makeText(AddHotel.this, "Plant Item Added", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_IMAGE && data != null) {
            addImageUri = data.getData();
            isImageAdded = true;
            addPlantImage.setImageURI(addImageUri);
        }
    }
}
