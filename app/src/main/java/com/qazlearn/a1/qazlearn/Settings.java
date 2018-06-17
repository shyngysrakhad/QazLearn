package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Settings extends Activity {
    EditText password, confirm, username, lastname;
    Button update, delete, signout, verify, change;
    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    public static TransitionType transitionType;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference data = databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String stringusername, stringlastname, strpassword, strconfirmation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        change = (Button) findViewById(R.id.buttonchange);
        password = (EditText)findViewById(R.id.editText9);
        username = (EditText)findViewById(R.id.nameofuser);
        stringusername = username.getText().toString();
        lastname = (EditText)findViewById(R.id.lastname);
        stringlastname = lastname.getText().toString();
        data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String user = dataSnapshot.getValue(String.class);
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Users_database").child(user).child("Lastname").exists()){
                            data.child("Users_database").child(user).child("Lastname").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String stringlastname = dataSnapshot.getValue(String.class);
                                    lastname.setText(stringlastname);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Users_database").child(user).child("Name").exists()){
                            data.child("Users_database").child(user).child("Name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String name = dataSnapshot.getValue(String.class);
                                    username.setText(name);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
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
        confirm  = (EditText)findViewById(R.id.editText10);
        strpassword = password.getText().toString();
        strconfirmation = confirm.getText().toString();
        delete   = (Button)findViewById(R.id.button4);
        signout  = (Button)findViewById(R.id.button3);
        verify   = (Button)findViewById(R.id.confirm);
        if(user.isEmailVerified()){
            verify.setVisibility(View.GONE);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
            builder.setTitle(getString(R.string.accountconfirmation))
                    .setCancelable(false)
                    .setMessage(getString(R.string.confirmbyclicking))
                    .setNegativeButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification();
                    verify.setVisibility(View.VISIBLE);
                }
            });
        }
        update = (Button)findViewById(R.id.button2);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(Settings.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                if (lastname.getText().toString().equals("") && username.getText().toString().equals("")) {
                    progressDialog.dismiss();
                    Toast.makeText(Settings.this, getString(R.string.fillthefield), Toast.LENGTH_SHORT).show();
                }else{
                    data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String namedata = dataSnapshot.getValue(String.class);
                            data.child("Users_database").child(namedata).child("Name").setValue(username.getText().toString());
                            data.child("Users_database").child(namedata).child("Lastname").setValue(lastname.getText().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(Settings.this, getString(R.string.successful), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(Settings.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                    if (confirm.getText().toString().equals(password.getText().toString())){
                        if (!confirm.getText().toString().equals("")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                            builder.setTitle(getString(R.string.updating))
                                    .setCancelable(false)
                                    .setMessage(getString(R.string.suretosignout) + getString(R.string.yourdatawillbeupdate))
                                    .setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            progressDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            user.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        progressDialog.dismiss();
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                                                        builder.setTitle(getString(R.string.passwordhasbeenchanged))
                                                                .setCancelable(false)
                                                                .setMessage(getString(R.string.restartapp))
                                                                .setNegativeButton("Ок",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {
                                                                                FirebaseAuth.getInstance().signOut();
                                                                                Intent intent = new Intent(Settings.this, Login.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        });
                                                        AlertDialog alert = builder.create();
                                                        alert.show();
                                                    }else{
                                                        progressDialog.dismiss();
                                                        Toast.makeText(getApplicationContext(), "Oops, there is problem", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Settings.this, getString(R.string.fillthefield), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Settings.this, getString(R.string.passwordsdontmatch), Toast.LENGTH_SHORT).show();
                    }
                }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle(getString(R.string.signout))
                        .setCancelable(true)
                        .setMessage(getString(R.string.suretosignout))
                        .setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(Settings.this, Registration.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle(getString(R.string.deletingaccount))
                        .setCancelable(true)
                        .setMessage(getString(R.string.suretosignout) + getString(R.string.thewholeprogresswillbedeleteed))
                        .setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.child("Users_name").child(user.getUid()).removeValue();
                                if(user != null){
                                    user.delete();
                                    Toast.makeText(getApplicationContext(), getString(R.string.theaccountdeleted), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Settings.this, Registration.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
        transitionType = TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
}
