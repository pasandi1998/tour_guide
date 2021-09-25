//package com.example.wikings.Shehani;
//import com.example.wikings.R;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
//import com.example.wikings.Shehani.Model.Hotel;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.squareup.picasso.Picasso;
//
//public class Customer_view_place_item extends AppCompatActivity {
//    DrawerLayout drawerLayout;
//    RecyclerView cusPlantRecycleView;
//    EditText cusSearchPlant;
//
//    FirebaseRecyclerOptions<Hotel> options;
//    FirebaseRecyclerAdapter<Hotel, MyViewHolder> adapter;
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_customer_view_plant_item);
//
//        databaseReference= FirebaseDatabase.getInstance().getReference().child("Places");
//
//        cusSearchPlant=findViewById(R.id.cus_plant_search);
//        cusPlantRecycleView=findViewById(R.id.cus_plant_recycleview);
//        cusPlantRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        cusPlantRecycleView.setHasFixedSize(true);
//
//        drawerLayout=findViewById(R.id.drawerLayout);
//
//
//        loadData("");
//        cusSearchPlant.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.toString()!=null){
//                    loadData(s.toString());
//                }else{
//                    loadData("");
//                }
//
//            }
//        });
//    }
//
//
//
//    private void loadData(String data) {
//
//        Query query=databaseReference.orderByChild("Province").startAt(data).endAt(data+"\uf8ff");
//
//        options=new FirebaseRecyclerOptions.Builder<Hotel>().setQuery(query, Hotel.class).build();
//        adapter=new FirebaseRecyclerAdapter<Hotel, MyViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Hotel model) {
//                holder.textViewPlace.setText(model.getPlace());
//                holder.textViewProvince.setText(model.getProvince());
//                holder.textViewdescription.setText(String.valueOf(model.getdescription()));
//                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
//
//            }
//
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_view,parent,false);
//                return new MyViewHolder(v);
//            }
//        };
//        adapter.startListening();
//        cusPlantRecycleView.setAdapter(adapter);
//    }
//}