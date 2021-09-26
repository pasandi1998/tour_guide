package Nipuna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wikings.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class addEvent extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 1;
    private EditText eventName, placeName, province, city, name, phone, price, description;
    private ImageView backBtn, pickEventImg;
    private Button choose, submit;

    private StorageTask storageTask;
    private ProgressDialog progressDialog;

    Uri imgUri;
    boolean IsImageAdded = false;

    DatabaseReference dbRef;
    StorageReference storeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }
}

