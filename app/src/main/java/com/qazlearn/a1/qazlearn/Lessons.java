package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qazlearn.a1.qazlearn.Methods.Dialog;
import com.qazlearn.a1.qazlearn.Methods.LearnWords;
import com.qazlearn.a1.qazlearn.Methods.Testing;

public class Lessons extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    Button lesson_family, lesson_dwelling, lesson_character, lesson_food, lesson_shop;
    Button l1, l2, l3, l4, l5, l6, l7, l8, l9;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference data = databaseReference;
    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    Button lesson_family_test, lesson_dwelling_test, lesson_character_test, lesson_food_test, lesson_shop_test;
    TextView txtlesson1, txtlesson2, txtlesson3, txtlesson4, txtlesson5;
    public static TransitionType transitionType;
    TextView wordofday;
    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference data1 = databaseReference1;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        databaseReference.keepSynced(true);
        isOnline();
        l1 = (Button)findViewById(R.id.letter1); l6 = (Button)findViewById(R.id.letter6);
        l2 = (Button)findViewById(R.id.letter2); l7 = (Button)findViewById(R.id.letter7);
        l3 = (Button)findViewById(R.id.letter3); l8 = (Button)findViewById(R.id.letter8);
        l4 = (Button)findViewById(R.id.letter4); l9 = (Button)findViewById(R.id.letter9);
        l5 = (Button)findViewById(R.id.letter5);
        Kazakhletters(l1, R.raw.kzlt1, "A'tes'", getString(R.string.rooster), R.drawable.rooster, R.raw.rooster);Kazakhletters(l6, R.raw.kzlt6, "Us'aq", getString(R.string.plane), R.drawable.airplane, R.raw.plane);
        Kazakhletters(l2, R.raw.kzlt2, "G'arys'", getString(R.string.space), R.drawable.rocket, R.raw.space);Kazakhletters(l7, R.raw.kzlt7, "U'i'rek", getString(R.string.duck), R.drawable.duck, R.raw.duck);
        Kazakhletters(l3, R.raw.kzlt3, "Qasyq", getString(R.string.spoon), R.drawable.spoon, R.raw.spoon);Kazakhletters(l8, R.raw.kzlt8, "Gay'Һartas", getString(R.string.diamond), R.drawable.jewels, R.raw.diamond);
        Kazakhletters(l4, R.raw.kzlt4, "Jan'byr", getString(R.string.rain), R.drawable.rain, R.raw.rain);Kazakhletters(l9, R.raw.kzlt9, "Irims'ik", getString(R.string.cheese),R.drawable.cheese, R.raw.cheese);
        Kazakhletters(l5, R.raw.kzlt5, "O'simdik", getString(R.string.plant),R.drawable.plant, R.raw.plant);
        wordofday = (TextView)findViewById(R.id.dayword);
        data.child("Dayword").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String word = dataSnapshot.getValue(String.class);
                wordofday.setText(word);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        txtlesson1 = (TextView)findViewById(R.id.txtlesson1);txtlesson3 = (TextView)findViewById(R.id.txtlesson3);
        txtlesson2 = (TextView)findViewById(R.id.txtlesson2);txtlesson4 = (TextView)findViewById(R.id.txtlesson4);
        lesson_family = (Button)findViewById(R.id.lesson_family);txtlesson5 = (TextView)findViewById(R.id.txtlesson5);
        lesson_character = (Button)findViewById(R.id.lesson_character);lesson_shop = (Button)findViewById(R.id.lesson_shop);
        lesson_dwelling = (Button)findViewById(R.id.lesson_dwelling);lesson_shop_test = (Button)findViewById(R.id.lesson_shop_test);
        lesson_character_test = (Button)findViewById(R.id.lesson_character_test);
        lesson_family_test = (Button)findViewById(R.id.lesson_family_test);
        lesson_dwelling_test = (Button)findViewById(R.id.lesson_dwelling_test);
        lesson_food = (Button)findViewById(R.id.lesson_food);lesson_food_test = (Button)findViewById(R.id.lesson_food_test);
        Score(txtlesson1, getResources().getString(R.string.acquaintance), lesson_family_test);
        Score(txtlesson2, getString(R.string.dwelling), lesson_dwelling_test);
        Score(txtlesson3, getString(R.string.character), lesson_character_test);
        Score(txtlesson4, getString(R.string.food), lesson_food_test);
        Score(txtlesson5, getString(R.string.shop), lesson_shop_test);
        ButtonFunctionLessons(lesson_family, getString(R.string.acquaintance), lesson_family_test);
        ButtonFunctionLessons(lesson_character, getString(R.string.character), lesson_character_test);
        ButtonFunctionLessons(lesson_dwelling, getString(R.string.dwelling), lesson_dwelling_test);
        ButtonFunctionLessons(lesson_food, getString(R.string.food), lesson_food_test);
        ButtonFunctionLessons(lesson_shop, getString(R.string.shop), lesson_shop_test);
    }
    public void Score(final TextView textView, final String lesson, final Button button){
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot1) {
                data1.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String doen = dataSnapshot.getValue(String.class);
                        if(dataSnapshot1.child("Exam").child(doen).child(lesson).exists()){
                            data.child("Exam").child(doen).child(lesson).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String test = dataSnapshot.getValue(String.class);
                                    String testresult = textView.getText() + "\nExam" + "(" + test + ")";
                                    textView.setText(testresult);
                                    button.setBackground(getDrawable(R.drawable.ic_checked));
                                    button.setVisibility(View.VISIBLE);
                                    button.setEnabled(true);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            data.child("Exam").child(doen).child(lesson).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String test = dataSnapshot.getValue(String.class);
                                    String testresult = textView.getText() + "\nExam" + "(" + test + ")";
                                    textView.setText(testresult);
                                    button.setBackground(getDrawable(R.drawable.ic_checked));
                                    button.setVisibility(View.VISIBLE);
                                    button.setEnabled(true);
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
                        }else{
                            String defaultstring = textView.getText().toString();
                            textView.setText(defaultstring);
                        }
                        if (dataSnapshot1.child("Lessons").child(doen).child(lesson).exists()){
                            data.child("Lessons").child(doen).child(lesson).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String done = dataSnapshot.getValue(String.class);
                                    String strresult = textView.getText() + "\n" + done;
                                    textView.setText(strresult);
                                    //TODO Изменено, нужно проверить
                                    button.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            data.child("Lessons").child(doen).child(lesson).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String done = dataSnapshot.getValue(String.class);
                                    String strresult = textView.getText() + "\n" + done;
                                    textView.setText(strresult);
                                    //TODO Изменено, нужно проверить
                                    button.setVisibility(View.VISIBLE);
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
                        }else{
                            button.setVisibility(View.GONE);
                        }
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
    public void Alpha(Button button){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
    public void Kazakhletters(Button button, final int media, final String string, final String stringtranslate, final int image, final int example){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                mediaPlayer = MediaPlayer.create(Lessons.this, media);
                mediaPlayer.start();
                if (mediaPlayer == null)
                    return;
                mediaPlayer.setOnCompletionListener(Lessons.this);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View view2 = (LayoutInflater.from(Lessons.this)).inflate(R.layout.dialogforletters, null);
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(Lessons.this);
                alertbuilder.setView(view2);
                final ImageView letterimage = (ImageView)view2.findViewById(R.id.letterimage);
                final TextView original = (TextView)view2.findViewById(R.id.exampleoriginal);
                final TextView translate = (TextView)view2.findViewById(R.id.exampletranslate);
                alertbuilder.setCancelable(true);
                final Button transcriptionbutton = (Button)view2.findViewById(R.id.btntranscription);
                transcriptionbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        releaseMP();
                        mediaPlayer = MediaPlayer.create(Lessons.this, example);
                        mediaPlayer.start();
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.setOnCompletionListener(Lessons.this);
                    }
                });
                letterimage.setImageDrawable(getDrawable(image));
                original.setText(string);
                translate.setText(stringtranslate);
                android.app.Dialog dialog = alertbuilder.create();
                dialog.show();
                return true;
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
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Lessons.this, MainActivity.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.offline_mode), Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }
    public void ButtonFunctionLessons(Button button, final String data, Button test){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lessons.this, Dialog.class);
                intent.putExtra("data", data);
                startActivity(intent);
                AnimationActivity();
            }
        });
        Alpha(button);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lessons.this, Testing.class);
                intent.putExtra("data", data);
                startActivity(intent);
                AnimationActivity();
            }
        });
        Alpha(test);
    }
    public void AnimationActivity(){
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
}
