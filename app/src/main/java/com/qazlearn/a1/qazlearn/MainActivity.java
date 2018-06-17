package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity{
    Button button_lesson, button_send, button_settings, button_achievement, button_expo, button_translator;
    private static long back_pressed;
    public static enum TransitionType {
        Zoom
    }
    public static TransitionType transitionType;
    TextView username;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference data = databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView)findViewById(R.id.user_name);
        button_send = (Button)findViewById(R.id.button_send);
        button_settings = (Button)findViewById(R.id.button_settings);
        button_achievement = (Button)findViewById(R.id.button_achievement);
        button_expo = (Button)findViewById(R.id.button_expo);
        button_lesson = (Button)findViewById(R.id.button_lessons);
        button_translator = (Button)findViewById(R.id.button_translator);
        button_expo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpoTest.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });

        ButtonEffect(button_achievement);
        ButtonEffect(button_expo);
        ButtonEffect(button_send);
        ButtonEffect(button_settings);
        ButtonEffect(button_translator);
        data.child("Version").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String newversion = dataSnapshot.getValue(String.class);
                String oldversion = BuildConfig.VERSION_NAME;
                if (!oldversion.equals(newversion)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(getString(R.string.update_version))
                            .setCancelable(false)
                            .setMessage(getString(R.string.about_old_version))
                            .setNegativeButton(getString(R.string.update), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String url = "https://drive.google.com/open?id=0B_hyXEA1ewi-Tkh1SzdPQlZpUGs";
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        button_translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Latin.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        button_achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Achievement.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Support.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        if(user != null){
            data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String s = dataSnapshot.getValue(String.class);
                    String user = getString(R.string.hello) + ", " + s;
                    username.setText(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        }
        ButtonEffect(button_lesson);
        button_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                Intent intent = new Intent(MainActivity.this, Lessons.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.exit), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }
    public void ButtonEffect(Button button){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
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
    @Override
    public void onStart(){
        super.onStart();
    }
}
