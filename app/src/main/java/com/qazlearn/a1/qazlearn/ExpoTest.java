package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpoTest extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    ListView ratinglist;
    private Extra2 adapter;
    private List<Extra> mProductList;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference database = databaseReference.child("Expo2017");
    int ic;
    MediaPlayer mediaPlayer;
    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    public static TransitionType transitionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expo_test);
        ratinglist = (ListView)findViewById(R.id.listqqq);
        mProductList = new ArrayList<>();
        adapter = new Extra2(getApplicationContext(), mProductList);
        ratinglist.setAdapter(adapter);
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot under: dataSnapshot.getChildren()){
                    for (final DataSnapshot extra: under.getChildren()){
                        mProductList.add(new Extra(1, dataSnapshot.getKey(), under.getKey(), extra.getKey(),extra.getValue(String.class)));
                        adapter.notifyDataSetChanged();
                        ratinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                releaseMP();
                                try {
                                    String s = mProductList.get(i).getUri();
                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(s);
                                    mediaPlayer.prepareAsync();
                                    mediaPlayer.setOnPreparedListener(ExpoTest.this);
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (mediaPlayer == null)
                                    return;
                                mediaPlayer.setOnCompletionListener(ExpoTest.this);
                            }
                        });
                    }

                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ExpoTest.this, MainActivity.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }

}
