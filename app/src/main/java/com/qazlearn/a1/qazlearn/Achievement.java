package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class Achievement extends Activity{

    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    ListView courses, exams;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> examlist = new ArrayList<>();
    public static TransitionType transitionType;
    TextView skill;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference database = databaseReference;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> exam;
    Button rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        courses = (ListView)findViewById(R.id.courseList);
        skill = (TextView)findViewById(R.id.skill);
        rating = (Button)findViewById(R.id.rating);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Achievement.this, Ratinglist.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        exams = (ListView)findViewById(R.id.examlist);
        exam = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, examlist);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
        courses.setAdapter(arrayAdapter);
        exams.setAdapter(exam);
        database.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String username = dataSnapshot.getValue(String.class);
                database.child("Lessons").child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = (int) (dataSnapshot.getChildrenCount()*5);
                        skill.setText(String.valueOf(i));
                        database.child("Rating").child(username).setValue(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                database.child("Lessons").child(username).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        list.add(dataSnapshot.getKey());
                        arrayAdapter.notifyDataSetChanged();
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

                database.child("Exam").child(username).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        examlist.add(dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                        exam.notifyDataSetChanged();
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
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Achievement.this, MainActivity.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
}
