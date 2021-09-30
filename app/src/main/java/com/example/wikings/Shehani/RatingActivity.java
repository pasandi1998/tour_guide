package com.example.wikings.Shehani;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikings.R;
import com.example.wikings.Shehani.Model.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {
    private EditText edtReview;
    private RatingBar ratingBar2;
    private RecyclerView rv_ratings;
    private Button submitButton;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Hotel Ratings");

        String hotelKey = getIntent().getStringExtra("hotelKey");

        ratingBar2 = findViewById(R.id.ratingBar2);
        edtReview = findViewById(R.id.editTextTextPersonName);
        rv_ratings = findViewById(R.id.rv_ratings);
        submitButton = findViewById(R.id.button);

        ratingBar2.setNumStars(5);
        ratingBar2.setStepSize(1);
        ratingBar2.setRating(5);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ratings").child(hotelKey);

        loadData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = edtReview.getText().toString().trim();

                if (review.isEmpty()) {
                    edtReview.setError("Please Enter review...");
                    edtReview.requestFocus();
                    return;
                }


                addReview(ratingBar2.getRating(), review);
            }
        });

        //setup recycle view
        rv_ratings.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_ratings.setHasFixedSize(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addReview(float startCount, String review) {
        final String key = databaseReference.push().getKey();
        HashMap hashMap = new HashMap();
        hashMap.put("startCount", startCount);
        hashMap.put("review", review);
        databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                displayAlert();
            }
        });
    }

    private void displayAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Review submitted")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ratingBar2.setNumStars(5);
                        edtReview.setText("");
                    }
                }).show();
    }

    private void loadData() {
        FirebaseRecyclerOptions<Rating> options = new FirebaseRecyclerOptions.Builder<Rating>().setQuery(databaseReference, Rating.class).build();
        FirebaseRecyclerAdapter<Rating, RatingViewHolder> adapter = new FirebaseRecyclerAdapter<Rating, RatingViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RatingViewHolder holder, int position, @NonNull Rating model) {
                holder.ratingBar3.setRating(model.getStartCount());
                holder.textView2.setText(model.getReview());
            }


            @NonNull
            @Override
            public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_view, parent, false);
                return new RatingViewHolder(v);
            }
        };

        adapter.startListening();
        rv_ratings.setAdapter(adapter);
    }
}
