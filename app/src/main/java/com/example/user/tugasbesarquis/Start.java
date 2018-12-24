package com.example.user.tugasbesarquis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.tugasbesarquis.Common.Common;
import com.example.user.tugasbesarquis.Model.Pertanyaan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;

public class Start extends AppCompatActivity {
    
    Button btnPlay;
    
    FirebaseDatabase database;
    DatabaseReference pertanyaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        database = FirebaseDatabase.getInstance();
        pertanyaan = database.getReference("Pertanyaan");
        
        loadPertanyaan(Common.categoryId);

        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadPertanyaan(String categoryId) {
        //clear list jika didapat pertanyaan lama
        if (Common.pertanyaanList.size()>0)
            Common.pertanyaanList.clear();

        pertanyaan.orderByChild("CategorId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Pertanyaan tanya = postSnapshot.getValue(Pertanyaan.class);
                            Common.pertanyaanList.add(tanya);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        //random list
        Collections.shuffle(Common.pertanyaanList);
    }
}
