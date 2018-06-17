package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Activity {
    EditText email, password;
    FirebaseAuth firebaseAuth;
    Button login, create, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
        email = (EditText)findViewById(R.id.txtEmailLogin);
        password = (EditText)findViewById(R.id.txtPasswordLogin);
        login = (Button)findViewById(R.id.btnLogin);
        reset = (Button)findViewById(R.id.resetpassword);
        create = (Button)findViewById(R.id.createaccount);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillthefield), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    final ProgressDialog progressDialog = ProgressDialog.show(Login.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.successful), Toast.LENGTH_LONG);
                                toast.show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view2 = (LayoutInflater.from(Login.this)).inflate(R.layout.dialogwithfield, null);
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(Login.this);
                alertbuilder.setView(view2);
                final EditText email = (EditText)view2.findViewById(R.id.emailforerset);

                alertbuilder.setCancelable(true)
                        .setPositiveButton(getString(R.string.reset), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final ProgressDialog progressDialog = ProgressDialog.show(Login.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, getString(R.string.resetpassword), Toast.LENGTH_SHORT).show();
                                        }else{
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                Dialog dialog = alertbuilder.create();
                dialog.show();
            }
        });
    }
}
