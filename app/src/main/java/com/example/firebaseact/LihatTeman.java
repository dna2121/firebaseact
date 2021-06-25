package com.example.firebaseact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatTeman extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Teman> dataTeman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_teman);

        //inisiasi untuk recycler view beserta komponen
        recyclerView = findViewById(R.id.rv_utama);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //inisialisasi dan mengambil firebase db reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //mengambil data teman dari firebase realtime db
        databaseReference.child("Teman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot) {
                //saat ada data baru masukkan datanya ke arraylist
                dataTeman = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    //saat ada data baru masukkan datanya ke arraylist
                    Teman teman = noteDataSnapshot.getValue(Teman.class);
                    teman.setKode(noteDataSnapshot.getKey());

                    dataTeman.add(teman);
                }

                adapter = new AdapterTeman(dataTeman, LihatTeman.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public  void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails() + " " +error.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return  new Intent(activity, LihatTeman.class);
    }
}