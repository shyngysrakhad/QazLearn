package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Support extends Activity {
    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    TextView tvratetheapp, tvsharetheapp, tvcontact;
    Button imratetheapp, imsharetheapp, imcontact;
    public static TransitionType transitionType;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference data = databaseReference;
    DatabaseReference data1 = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        imcontact = (Button) findViewById(R.id.imcontacthedev);
        tvratetheapp = (TextView)findViewById(R.id.tvratetheapp);
        tvsharetheapp = (TextView)findViewById(R.id.tvsharetheapp);
        imsharetheapp = (Button)findViewById(R.id.imsharetheapp);
        tvcontact = (TextView)findViewById(R.id.tvcontacthedev);
        tvsharetheapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(view);
            }
        });
        imsharetheapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(view);
            }
        });
        imcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contactus();
            }
        });
        tvcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contactus();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Support.this, MainActivity.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
    public void Contactus(){
        View view2 = (LayoutInflater.from(Support.this)).inflate(R.layout.dialogwithfield, null);
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(Support.this);
        alertbuilder.setView(view2);
        final EditText email = (EditText)view2.findViewById(R.id.emailforerset);
        email.setHint(getString(R.string.typetodevelopers));
        alertbuilder.setCancelable(true)
                .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String username = dataSnapshot.getValue(String.class);
                                data.child("Report").child(username).child("User").setValue(email.getText().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(Support.this, getString(R.string.youmessagewassent), Toast.LENGTH_SHORT).show();
                    }
                });
        Dialog dialog = alertbuilder.create();
        dialog.show();
    }
    @Override
    public void onStart(){
        super.onStart();
        data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String username = dataSnapshot.getValue(String.class);
                data1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Report").child(username).child("Response").exists()) {
                            data1.child("Report").child(username).child("Response").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String response = dataSnapshot.getValue(String.class);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Support.this);
                                    builder.setTitle(getResources().getString(R.string.response))
                                            .setMessage(response)
                                            .setCancelable(false)
                                            .setPositiveButton(getString(R.string.nevershow), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    data1.child("Report").child(username).child("Response").removeValue();
                                                }
                                            })
                                            .setNegativeButton("Ок",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else{

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
    public void share(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.advertising)+"\n"+"https://drive.google.com/open?id=0B_hyXEA1ewi-Tkh1SzdPQlZpUGs");
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
    }
}
