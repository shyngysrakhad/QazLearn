package com.qazlearn.a1.qazlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Registration extends Activity {
    public static enum TransitionType {
        Zoom
    }
    public static TransitionType transitionType;
    EditText name, mail, password;
    Button registration, login;
    private static long back_pressed;
    TextView exist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference data = databaseReference.child("Users_database");
    ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        isOnline();
        exist = (TextView)findViewById(R.id.existing);
        exist.setVisibility(View.GONE);
        name = (EditText)findViewById(R.id.edit_name);
        mail = (EditText)findViewById(R.id.txtEmailRegistration);
        password = (EditText)findViewById(R.id.txtPasswordRegistration);
        registration = (Button)findViewById(R.id.registration);
        login = (Button)findViewById(R.id.tologin);
        final Spinner countyspinner = (Spinner)findViewById(R.id.countryspinner);
        final ArrayList<String> countries = new ArrayList<>();
        countries.add(getResources().getString(R.string.selectyourcountry));
        List age = new ArrayList<Integer>();
        age.add(getResources().getString(R.string.selectage));
        for (int i = 1; i <=100; i++){
            age.add(Integer.toString(i));
        }
        Locale[] locales = Locale.getAvailableLocales();
        ArrayAdapter<Integer> ageadapter = new ArrayAdapter<Integer>(Registration.this, android.R.layout.simple_spinner_item, age);
        ageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner agespinner = (Spinner)findViewById(R.id.agespinner);
        agespinner.setAdapter(ageadapter);
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(Registration.this,
                android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countyspinner.setAdapter(countryAdapter);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || mail.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.fillthefield), Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    progressDialog = ProgressDialog.show(Registration.this, getResources().getString(R.string.loading), getResources().getString(R.string.wait), true);
                    doSomethingWhenProgressNotShown();
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChild(name.getText().toString())){
                                exist.setVisibility(View.GONE);
                                firebaseAuth.createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.successful), Toast.LENGTH_LONG);
                                            toast.show();
                                            Intent intent = new Intent(Registration.this, Login.class);
                                            startActivity(intent);
                                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            assert user != null;
                                            final DatabaseReference userid = databaseReference.child("Users_database").child(name.getText().toString());
                                            userid.child("Username").setValue(name.getText().toString());
                                            userid.child("Email").setValue(mail.getText().toString());
                                            userid.child("Password").setValue(password.getText().toString());
                                            userid.child("Age").setValue(agespinner.getSelectedItem().toString());
                                            userid.child("Country").setValue(countyspinner.getSelectedItem().toString());
                                            final DatabaseReference id  = databaseReference.child("Users_name").child(user.getUid());
                                            id.setValue(name.getText().toString());
                                            progressDialog.dismiss();
                                            exist.setVisibility(View.GONE);
                                            name.setBackgroundResource(R.drawable.edittext);
                                        } else {
                                            progressDialog.dismiss();
                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }
                                });
                            }else{
                                progressDialog.dismiss();
                                doSomethingWhenProgressNotShown();
                                exist.setVisibility(View.VISIBLE);
                                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                                shape.getPaint().setColor(Color.RED);
                                shape.getPaint().setStyle(Paint.Style.STROKE);
                                name.setBackground(shape);
                                exist.setVisibility(View.VISIBLE);
                                name.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                         exist.setVisibility(View.GONE);
                                        name.setBackgroundResource(R.drawable.edittext);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_NEXT){
                    mail.isSelected();
                }
                return false;
            }
        });
        mail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_NEXT){
                    password.isSelected();
                }
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideSoftKeyboard(Registration.this);
                }
                return false;
            }
        });
    }
    private void doSomethingWhenProgressNotShown() {
        if (progressDialog != null && progressDialog.isShowing()) {
            exist.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doSomethingWhenProgressNotShown();
                }
            }, 500);
        }

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.exit) + "\n" + getResources().getString(R.string.notregistered), Toast.LENGTH_LONG).show();
        }
        back_pressed = System.currentTimeMillis();
        transitionType = Registration.TransitionType.Zoom;
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
            builder.setTitle(getString(R.string.nointernetconnection))
                    .setMessage(getString(R.string.internet_connection_retry))
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.retry),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    isOnline();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
    }
}
