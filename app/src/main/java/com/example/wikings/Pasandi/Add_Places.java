package com.example.wikings.Pasandi;

import androidx.appcompat.app.AppCompatActivity;


import com.example.wikings.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class Add_Places extends AppCompatActivity {


    EditText name,provinces,description ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;

    int Image_Request_Code = 200;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        getSupportActionBar().setTitle("ADD NEW TRAVEL PLACE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        storageReference = FirebaseStorage.getInstance().getReference("Images");



        name = (EditText)findViewById(R.id.txtdata);
        provinces = (EditText)findViewById(R.id.provinces);
        description = (EditText)findViewById(R.id.description);
        imgview = (ImageView)findViewById(R.id.image_view);
        progressDialog = new ProgressDialog(Add_Places.this);// context name as per your project name
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage(View view) {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            uploadinfo uploadinfo = new uploadinfo();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Images");

                            String TempImageName = name.getText().toString().trim();
                            String pro = provinces.getText().toString().trim();
                            String des = description.getText().toString().trim();
                            uploadinfo.setImageName(TempImageName);
                            uploadinfo.setProvince(pro);
                            uploadinfo.setDescription(des);
                            uploadinfo.setImageURL(taskSnapshot.getUploadSessionUri().toString());

                            System.out.println(TempImageName);
                            System.out.println(taskSnapshot.getUploadSessionUri().toString());
                            databaseReference.push().setValue(uploadinfo);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else {

            Toast.makeText(Add_Places.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}