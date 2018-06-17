package com.qazlearn.a1.qazlearn.Methods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qazlearn.a1.qazlearn.Lessons;
import com.qazlearn.a1.qazlearn.R;

import java.util.Locale;

public class Listening extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    final String TAG = "States";
    public static enum TransitionType {
        Zoom
    }
    public static TransitionType transitionType;
    Button player;
    private int count2 = -1;
    private String[] lesson1answers = {"SA'LEM", "ONYN' ATY OLJAS", "SEN NES'EDESIN'?", "MEN ON BESTEMIN", "TANYSQANYMA QY'ANYS'TYMYN","MEN DE"};
    private String[] lesson2answers = {"QALAI'SYN'?", "JAQSY", "SEN QAI'DA TURASYN'?", "MEN AY'YLDA TURAMYN, JEKE U'I'DE", "MEN QALADA TURAMYN, PA'TERDE", "JEKE U'I'", "PA'TER", "KO'RS'I","QALAI'SYN'?", "JAQSY", "SEN QAI'DA TURASYN'?", "MEN AY'YLDA TURAMYN, JEKE U'I'DE", "MEN QALADA TURAMYN, PA'TERDE", "JEKE U'I'", "PA'TER", "KO'RS'I"};
    private String[] lesson3answers = {"OL QANDAI' ADAM?", "OL UZYN BOI'LY, DENELI, JAQSY JIGIT. MINEZI AQKO'N'IL, JOMART", "TU'SINIKTI", "QYSQA", "TU'SINIKSIZ"};
    private String[] lesson4answers = {"TAN'G'Y AS", "SEN TAN'G'Y ASQA NE JEDIN'?", "MEN TAN'G'Y ASQA SYR JEDIM JA'NE KOFE IS'TIM", "SEN QANDAI' TAG'AMDY JAQSY KO'RESIN'?", "MEN BALYQ PEN JUJYQTY JAQSY KO'REMIN", "PAI'DALY"};
    private String[] lesson5answers = {"BIZDIN' DU'KENIMIZGE QOS' KELDIN'IZ!", "BALMUZDAQ", "BUL NES'E TEN'GE TURADY?", "U'S' JU'Z", "EKI BALMUZDAQ BERE ALASYZ BA?", "JEN'ILDIK", "SAY'DA"};
    EditText ed;
    private int count = 0;
    Button sound;
    ProgressBar progressBar;
    int progress = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference data = databaseReference;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_listening);
        player = (Button)findViewById(R.id.checktheanswer);
        sound = (Button)findViewById(R.id.sound);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        ed = (EditText) findViewById(R.id.editTextforSound);
        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    hideSoftKeyboard(Listening.this);
                }
                return false;
            }
        });
        sound.setText(getIntent().getStringExtra("data"));
        FinalFunction(getResources().getString(R.string.acquaintance), lesson1answers);
        FinalFunction(getString(R.string.dwelling), lesson2answers);
        FinalFunction(getString(R.string.character), lesson3answers);
        FinalFunction(getString(R.string.food), lesson4answers);
        FinalFunction(getString(R.string.shop), lesson5answers);
        SoundButton();
    }
    public void Id(final String[] answer1, final String value){
        sound.setText(answer1[count]);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count2 < answer1.length) {
                    count2++;
                    progress++;
                    progressBar.setProgress(progress);
                    progressBar.setIndeterminate(false);
                    progressBar.setMax(answer1.length);
                    if (ed.getText().toString().toUpperCase(Locale.getDefault()).equals(answer1[count2])) {
                        releaseMP();
                        mediaPlayer = MediaPlayer.create(Listening.this, R.raw.answer_is_true);
                        mediaPlayer.start();
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.setOnCompletionListener(Listening.this);
                        final Dialog true_answer = new Dialog(Listening.this, R.style.NoTitle);
                        true_answer.setContentView(R.layout.dialog_true);
                        true_answer.setCancelable(false);
                        Button button = (Button) true_answer.findViewById(R.id.next);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (count < answer1.length - 1) {
                                    count++;
                                    sound.setText(answer1[count]);
                                    true_answer.dismiss();
                                    ed.getText().clear();
                                    SoundButton();
                                } else {
                                    Intent intent = new Intent(Listening.this, Lessons.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String s = dataSnapshot.getValue(String.class);
                                            data.child("Lessons").child(s).child(value).setValue(getString(R.string.lessonmastered));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    finish();
                                    true_answer.dismiss();
                                    ed.getText().clear();
                                }
                            }
                        });
                        View view1 = Listening.this.getWindow().getDecorView();
                        view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        true_answer.show();
                    } else {
                        releaseMP();
                        mediaPlayer = MediaPlayer.create(Listening.this, R.raw.wrong);
                        mediaPlayer.start();
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.setOnCompletionListener(Listening.this);
                        final Dialog false_answer = new Dialog(Listening.this, R.style.NoTitle);
                        false_answer.setContentView(R.layout.dialog_wrong);
                        false_answer.setCancelable(false);
                        final TextView textView = (TextView) false_answer.findViewById(R.id.ans);
                        textView.setText(answer1[count2]);
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        Button button = (Button) false_answer.findViewById(R.id.next);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (count < answer1.length - 1) {
                                    count++;
                                    sound.setText(answer1[count]);
                                    false_answer.dismiss();
                                    ed.getText().clear();
                                    SoundButton();
                                } else {
                                    Intent intent = new Intent(Listening.this, Lessons.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    data.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String s = dataSnapshot.getValue(String.class);
                                            data.child("Lessons").child(s).child(value).setValue(getString(R.string.lessonmastered));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    finish();
                                    false_answer.dismiss();
                                    ed.getText().clear();
                                }
                            }
                        });
                        View view1 = Listening.this.getWindow().getDecorView();
                        view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        false_answer.show();
                    }
                } else {
                    Intent intent = new Intent(Listening.this, Lessons.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void SoundFunction(final String data, final int i){
        if(sound.getText().toString().trim().equals(data)) {
            sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    releaseMP();
                    mediaPlayer = MediaPlayer.create(Listening.this, i);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(Listening.this);
                }
            });
        }
    }
    public void FinalFunction(String data, final String[] answer1){
        if (sound.getText().toString().equals(data)){
            Id(answer1, data);
        }
    }
    public void SoundButton(){
        // TODO Здесь голоса записываются
        SoundFunction("SA'LEM", R.raw.dialog1);
        SoundFunction("JAQSY", R.raw.jaksy);
        SoundFunction("ONYN' ATY OLJAS", R.raw.onynatyolzhas);
        SoundFunction("SEN NES'EDESIN'?", R.raw.dialog6);
        SoundFunction("MEN ON BESTEMIN", R.raw.dialog8);
        SoundFunction("MEN DE", R.raw.dialog10);
        SoundFunction("QALAI'SYN'?", R.raw.kalaysin);
        SoundFunction("SEN QAI'DA TURASYN'?", R.raw.senkaydaturasyn);
        SoundFunction("MEN QALADA TURAMYN, PA'TERDE", R.raw.menkaladaturamyn);
        SoundFunction("MEN AY'YLDA TURAMYN, JEKE U'I'DE", R.raw.menauldaturamyn);
        SoundFunction("JEKE U'I'", R.raw.zhekeuy);
        SoundFunction("PA'TER", R.raw.pater);
        SoundFunction("KO'RS'I", R.raw.korshy);
        SoundFunction("OL QANDAI' ADAM?", R.raw.olkandayadam);
        SoundFunction("OL UZYN BOI'LY, DENELI, JAQSY JIGIT. MINEZI АҚКӨҢІЛ, ЖОМАРТ", R.raw.oluzunboyly);
        SoundFunction("TU'SINIKTI", R.raw.tusynykty);
        SoundFunction("QYSQA", R.raw.ashort);
        SoundFunction("TU'SINIKSIZ", R.raw.tusynyksiz);
        SoundFunction("TANYSQANYMA QY'ANYS'TYMYN", R.raw.dialog9);
        SoundFunction("TAN'G'Y AS", R.raw.tangyas);
        SoundFunction("SEN TAN'G'Y ASQA NE JEDIN'?", R.raw.sentangyaskanejedyn);
        SoundFunction("SEN QANDAI' TAG'AMDY JAQSY KO'RESIN'?", R.raw.senkandaytagamdyjaksykoresin);
        SoundFunction("MEN BALYQ PEN S'UJYQTY JAQSY KO'REMIN", R.raw.menbalykpensuzhykty);
        SoundFunction("PAI'DALY", R.raw.healthy);
        SoundFunction("BIZDIN' DU'KENIMIZGE QOS' KELDIN'IZ!", R.raw.bizdindukenimizgekosh);
        SoundFunction("BALMUZDAQ", R.raw.icecream);
        SoundFunction("BUL NES'E TEN'GE TURADY?", R.raw.bulneshetengeturady);
        SoundFunction("U'S' JU'Z", R.raw.threehundred);
        SoundFunction("EKI BALMUZDAQ BERE ALASYZ BA?", R.raw.ekibalmuzdakberealasyzba);
        SoundFunction("JEN'ILDIK", R.raw.discount);
        SoundFunction("SAY'DA", R.raw.sauda);
        SoundFunction("MEN TAN'G'Y ASQA SYR JEDIM JA'NE KOFE IS'TIM", R.raw.mentangyaskasirjanekofe);
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
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity: onStart()");
        final Dialog warning = new Dialog(Listening.this, R.style.NoTitle);
        warning.setContentView(R.layout.dialog_true);
        warning.setCancelable(false);
        TextView textView = (TextView)warning.findViewById(R.id.warning);
        textView.setText(getString(R.string.listening));
        Button button = (Button)warning.findViewById(R.id.next);
        warning.setCancelable(true);
        button.setText(getString(R.string.listenandwrite));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.dismiss();
            }
        });
        View view1 = Listening.this.getWindow().getDecorView();
        view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
    @Override
    public void onBackPressed() {
        final Dialog warning = new Dialog(Listening.this, R.style.NoTitle);
        warning.setContentView(R.layout.dialog_warning);
        warning.setCancelable(true);
        TextView textView = (TextView)warning.findViewById(R.id.warning);
        textView.setText(getString(R.string.suretosignout) + " " + getString(R.string.thewholeprogresswillbelost));
        Button yes = (Button)warning.findViewById(R.id.yes);
        Button no = (Button)warning.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warning.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Listening.this, Lessons.class);
                startActivity(intent);
                transitionType = TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        View view1 = Listening.this.getWindow().getDecorView();
        view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
