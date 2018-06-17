package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ratinglist extends Activity{
    ListView ratinglist;
    private AdapterRatingList adapter;
    private List<Rating> mProductList;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference database = databaseReference.child("Rating");
    int ic;
    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    public static TransitionType transitionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratinglist);
        ratinglist = (ListView)findViewById(R.id.ratinglist);
        mProductList = new ArrayList<>();
        adapter = new AdapterRatingList(getApplicationContext(), mProductList);
        ratinglist.setAdapter(adapter);
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
                mProductList.add(new Rating(1,dataSnapshot.getKey(),"Kazakhstan",dataSnapshot.getValue().toString()));
                Collections.sort(mProductList, new CustomComparator());
                Collections.reverse(mProductList);
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
        ratinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), getString(R.string.userinplace) + " "+String.valueOf(i+1) + " "+getString(R.string.place),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Ratinglist.this, Achievement.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
    public class CustomComparator implements Comparator<Rating> {
        @Override
        public int compare(Rating product, Rating t1) {
            return product.getGot()-t1.getGot();
        }
    }
}
