package com.example.user.tugasbesarquis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.tugasbesarquis.Common.Common;
import com.example.user.tugasbesarquis.Interface.ItemClickListener;
import com.example.user.tugasbesarquis.Interface.RankingCallback;
import com.example.user.tugasbesarquis.Model.PertanyaanScore;
import com.example.user.tugasbesarquis.Model.Ranking;
import com.example.user.tugasbesarquis.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder>adapter;

    FirebaseDatabase database;
    DatabaseReference pertanyaanScore,rankingTbl;

    int sum=0;

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database =FirebaseDatabase.getInstance();
        pertanyaanScore = database.getReference("Pertanyaan_Score");
        rankingTbl = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking,container,false);

        //init view
        rankingList = (RecyclerView)myFragment.findViewById(R.id.rankingList);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        //implement callback
        updateScore(Common.currentUser.getUsername(), new RankingCallback<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                //update tabel ranking
                rankingTbl.child(ranking.getUserName())
                        .setValue(ranking);
               // showRanking();
            }
        });
        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingTbl.orderByChild("skor")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {
                viewHolder.txt_nama.setText(model.getUserName());
                viewHolder.txt_skor.setText(String.valueOf(model.getScore()));

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);
        return  myFragment;
    }




    private void updateScore(final String username, final RankingCallback<Ranking>callback) {
        pertanyaanScore.orderByChild("user").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren())
                        {
                            PertanyaanScore tanya = data.getValue(PertanyaanScore.class);
                            sum+=Integer.parseInt(tanya.getScore());
                        }
                        Ranking ranking = new Ranking(username,sum);
                        callback.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
