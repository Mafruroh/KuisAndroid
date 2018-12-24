package com.example.user.tugasbesarquis;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.tugasbesarquis.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Playing extends AppCompatActivity implements View.OnClickListener  {
    final static long INTERVAL = 1000; //1dtk
    final static long TIMEOUT = 5000; //7dtk

    CountDownTimer mCountDown;

    int index=0,score=0,iniPertanyaan=0, totalPertanyaan, jawabanBenar;


    ProgressBar progressBar;
    ImageView pertanyaan_gambar;
    Button btnA,btnB,btnC,btnD;
    TextView txtScore,txtPertanyaanNum,pertanyaan_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


        //view
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtPertanyaanNum = (TextView) findViewById(R.id.txtTotalPertanyaan);
        pertanyaan_text = (TextView) findViewById(R.id.pertanyaan_text);
        pertanyaan_gambar = (ImageView) findViewById(R.id.pertanyaan_gambar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnA = (Button) findViewById(R.id.btnJawabanA);
        btnB = (Button) findViewById(R.id.btnJawabanB);
        btnC = (Button) findViewById(R.id.btnJawabanC);
        btnD = (Button) findViewById(R.id.btnJawabanD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mCountDown.cancel();
        if (index < totalPertanyaan) //daftar pertanyaan
        {
            Button clickedButton = (Button) view;
            if (clickedButton.getText().equals(Common.pertanyaanList.get(index).getJawabanBenar()))
            {
                //memilih jawaban benar
                score+=10;
                jawabanBenar++;
                showPertanyaan(index);
            }
            else
            {
                //memilih jawaban yg salah
                Intent intent = new Intent(this,Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SKOR", score);
                dataSend.putInt("TOTAL", totalPertanyaan);
                dataSend.putInt("BENAR", jawabanBenar);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d",score));
        }


    }

    private void showPertanyaan(int index) {
        if (index <totalPertanyaan)
        {
            iniPertanyaan++;
            txtPertanyaanNum.setText(String.format("%d / %d", iniPertanyaan,totalPertanyaan));
            progressBar.setProgress(0);
            int progressValue=0;
            if (Common.pertanyaanList.get(index).getIsImageQuestion().equals("true"))
            {
                //jika ini gambar
                Picasso.with(getBaseContext())
                        .load(Common.pertanyaanList.get(index).getJawaban())
                        .into(pertanyaan_gambar);
                pertanyaan_gambar.setVisibility(View.VISIBLE);
                pertanyaan_text.setVisibility(View.INVISIBLE);
            }
            else
            {
                //jika pertanyaan text makan image di invisiblekan
                pertanyaan_text.setText(Common.pertanyaanList.get(index).getJawaban());
                pertanyaan_gambar.setVisibility(View.INVISIBLE);
                pertanyaan_text.setVisibility(View.VISIBLE);
            }
            btnA.setText(Common.pertanyaanList.get(index).getJawabanA());
            btnB.setText(Common.pertanyaanList.get(index).getJawabanB());
            btnC.setText(Common.pertanyaanList.get(index).getJawabanC());
            btnD.setText(Common.pertanyaanList.get(index).getJawabanD());

            mCountDown.start(); //start timer
        }
        else
        {
            //ini untuk jika pertanyaan selesai
            Intent intent = new Intent(this,Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SKOR", score);
            dataSend.putInt("TOTAL", totalPertanyaan);
            dataSend.putInt("BENAR", jawabanBenar);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        totalPertanyaan = Common.pertanyaanList.size();
        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long minicec) {
                int progressValue = 0;
                progressBar.setProgress(progressValue);
                progressValue++;

            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showPertanyaan(++index);

            }
        };
        showPertanyaan(index);
    }
}
