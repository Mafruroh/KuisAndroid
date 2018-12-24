package com.example.user.tugasbesarquis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.tugasbesarquis.Common.Common;
import com.example.user.tugasbesarquis.Model.PertanyaanScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {

    Button btnCobaLagi;
    TextView txtHasilScore, getTxtHasilPertanyan;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference pertanyaan_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        pertanyaan_score = database.getReference("Pertanyaan_Score");

        //membutuhkan tabel baru untuk prtanyaan_score krna firebase itu adalah database realtime jadi tidak support
        //untuk membuat multile query seperti jika kita get scor dari user by category dan membutuhkan select * from
        //pertanyaan_score where user ="uroh" dan categoryId="01" tapi di firebase itu tidak bisa, jadi dibutuhkan menggunakan
        //user dan categori KEY (contoh:uroh_01 dan value scorenya :user_category, user, category
        txtHasilScore = (TextView) findViewById(R.id.txtTotalScore);
        getTxtHasilPertanyan = (TextView) findViewById(R.id.txtTotalPertanyaan);
        progressBar = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnCobaLagi = (Button) findViewById(R.id.btnCobaLagi);

        btnCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Done.this,Home.class);
                startActivity(intent);
                finish();
            }
        });

        //mendapatkan data dari bundle dan set to view
        Bundle extra = getIntent().getExtras();
        if (extra !=null)
        {
            int score = extra.getInt("SKOR");
            int totalPertanyaan = extra.getInt("TOTAL");
            int jawabanBenar = extra.getInt("BENAR");

            txtHasilScore.setText(String.format("SKOR : %d", score));
            getTxtHasilPertanyan.setText(String.format("BERHASIL : %d / %d", jawabanBenar, totalPertanyaan));

            progressBar.setMax(totalPertanyaan);
            progressBar.setProgress(jawabanBenar);

            //uplod point darii db
            pertanyaan_score.child(String.format("%s_%s", Common.currentUser.getUsername(),
                                                            Common.categoryId))
                    .setValue(new PertanyaanScore(String.format("%s_%s", Common.currentUser.getUsername(),
                            Common.categoryId),
                            Common.currentUser.getUsername(),
                            String.valueOf(score)));
        }

    }
}
