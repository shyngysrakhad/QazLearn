package com.qazlearn.a1.qazlearn.Methods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Testing extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    ProgressBar progressBar, progressQuestions;
    RadioButton rd1, rd2, rd3, rd4;
    Button buttoncheck;
    TextView textView2, countofquestions;
    public static enum TransitionType {
        Zoom
    }
    RadioGroup group;
    MediaPlayer mediaPlayer;
    public static Testing.TransitionType transitionType;
    String[][] choices1 = {
            {"Salem", "Sa'lem", "Pri'vet", "Sa'la'm"},
            {"Sen nes'edesin'?", "Senin' atyn' kim?", "Sen nes'e jastasyn'?", "Men on bestemin"},
            {"Menin' Oljas", "Men Oljas jastamyn", "Men on bestemin", "Menin' atym Oljas"},
            {"Menin' atym Oljas", "Men de", "Men on bestemin", "Kezdeskens'e"},
            {"Kezdestik", "Men on bestemin", "Tanysqanyma qy'anys'tymyn", "Men de"},
            {"Tanysqanyma qy'anamyn", "Tanysqanyma qy'anys'tymyn", "Tanysy' qy'anys'ty", "Men de"}
    };
    String[] correct1 = {"Sa'lem", "Senin' atyn' kim?", "Menin' atym Oljas", "Men on bestemin", "Tanysqanyma qy'anys'tymyn", "Men de"};
    String[][] choices2 = {
            {"Qalai'syn'?", "Qalai'?", "Sa'lem", "Qalai'syn?"},
            {"Jaqsy", "Jaman emes", "Jaqsy, rahmet!", "Men on bestemin"},
            {"Men qai'da turamyn?", "Sen qai'da turasyn'?", "Ol qai'da turady?", "Sen turasyn'?"},
            {"Pa'ter", "Jeke u'i'", "Ko'rs'i", "Qabat"},
            {"Ay'yl", "Qala", "Ko'rs'i", "Qabat"},
            {"Ay'yl", "Qala", "Ko'rs'i", "Qabat"},
            {"Pa'ter", "Jer u'i'", "Ko'rs'i", "Qabat"},
            {"Pa'ter", "Jer u'i'", "Ko'rs'i", "Qabat"},
            {"Pa'ter", "Jer u'i'", "Ko'rs'i", "Qabat"}
    };
    String[] correct2 = {"Qalai'syn'?","Men on bestemin","Sen qai'da turasyn'?","Ko'rs'i","Ay'yl", "Qala","Pa'ter","Qabat","Jer u'i'"};
    String[][] choices3 = {
            {"Ag'a","A'pke","Uzyn","Jaqsy" },
            {"Ag'a","A'pke","Uzyn","Jaqsy" },
            {"Ag'a","A'pke","Uzyn","Jaqsy" },
            {"Ag'a","A'pke","Uzyn","Jaqsy"},
            {"Qysqa","Minez", "Ana","A'ke" },
            {"Qysqa","Minez", "Ana","A'ke" },
            {"Qysqa","Minez", "Ana","A'ke" },
            {"Qysqa","Minez", "Ana","A'ke"},
            {"Qysqa","Uzyn", "Minez","Boi'"}
    };
    String[] correct3 = {"Ag'a","A'pke","Uzyn","Jaqsy","Qysqa","Minez", "Ana","A'ke","Boi'"};
    String[][] choices4 = {
            {"Tan'g'y as","Tamaq","Pai'daly","Men jaqsy ko'remin" },
            {"Tan'g'y as","Tamaq","Pai'daly","Men jaqsy ko'remin" },
            {"Tan'g'y as","Tamaq","Pai'daly","Men jaqsy ko'remin" },
            {"Tan'g'y as","Tamaq","Pai'daly","Men jaqsy ko'remin"},
            {"S'ujyq","Balyq", "Kofe","Sebebi" },
            {"S'ujyq","Balyq", "Kofe","Sebebi" },
            {"S'ujyq","Balyq", "Kofe","Sebebi" },
            {"S'ujyq","Balyq", "Kofe","Sebebi"},
            {"Ol jaqsy ko'resin'","Ol jaqsy", "Ol jaqsy ko'remin","Ol jaqsy ko'redi"}
    };
    String[] correct4 = {"Men jaqsy ko'remin","Tan'g'y as","Tamaq","Pai'daly","Sebebi","Kofe", "Balyq","S'ujyq","Ol jaqsy ko'redi"};
    String[][] choices5 = {
            {"Du'ken", "Balmuzdaq", "Say'da", "Jen'ildik"},
            {"Du'ken", "Balmuzdaq", "Say'da", "Jen'ildik"},
            {"Du'ken", "Balmuzdaq", "Say'da", "Jen'ildik"},
            {"Du'ken", "Balmuzdaq", "Say'da", "Jen'ildik"},
            {"Eki balmuzdaq bere alasyz ba?","Eki balmuzdaq bere alasyz?", "U's' balmuzdaq bere alasyz ba?","Eki balmuzdaq berin'iz"},
            {"Bul ten'ge turady?","Bul nes'e ten'ge turady?", "Bul nes'e ten'ge tur?","Sen nes'e ten'ge turady?"},
            {"Ju'z","U's'", "Eki","U's' ju'z"},
    };
    String[] correct5 = {"Balmuzdaq","Jen'ildik","Du'ken","Say'da","Eki balmuzdaq bere alasyz ba?","Bul nes'e ten'ge turady?", "Eki"};
    int questions = 0;
    int i = -1;
    int progress = 0;
    int progresslength = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    final DatabaseReference database = databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_testing);
        String t = getString(R.string.to_translate) + ">";
        String[] question1 = {getString(R.string.howtogreetinkazakh), getString(R.string.testwhatisyourname), getString(R.string.testmynameisolzhas), getString(R.string.testhowoldareyou) ,getString(R.string.testiamgladtoseeyou), getString(R.string.testiamalso)};
        String[] question2 = {t+getString(R.string.howareyou), getString(R.string.whatisthewronganswerfor)+" "+getString(R.string.howareyou), t+getString(R.string.wheredoyoulive), t+getString(R.string.neighbor),t+getString(R.string.village),t+getString(R.string.city),t+getString(R.string.apartment) ,t + getString(R.string.floor), t+getString(R.string.privatehouse)};
        String[] question3 = {t+getString(R.string.brother), t+getString(R.string.sister), t+getString(R.string.tall), t+getString(R.string.kind), t+getString(R.string.ashort), t+getString(R.string.character), t+getString(R.string.mother), t+getString(R.string.father), t+getString(R.string.growth)};
        String[] question4 = {t+getString(R.string.ilike), t+getString(R.string.breakfast), t+getString(R.string.food), t+getString(R.string.healthy), t+getString(R.string.because), t+getString(R.string.coffee), t+getString(R.string.fish), t+getString(R.string.sausage), t+getString(R.string.helikes)};
        String[] question5 = {t+getString(R.string.icecream), t+getString(R.string.discount), t+getString(R.string.shop), t+getString(R.string.sale), t+getString(R.string.canyougivemetwopiece), t+getString(R.string.howmuchdoesitcost), t+getString(R.string.two)};
        textView2 = (TextView)findViewById(R.id.textView41);
        rd1 = (RadioButton)findViewById(R.id.rd1);
        rd2 = (RadioButton)findViewById(R.id.rd2);
        rd3 = (RadioButton)findViewById(R.id.rd3);
        rd4 = (RadioButton)findViewById(R.id.rd4);
        countofquestions = (TextView)findViewById(R.id.countofquestions);
        progressBar = (ProgressBar)findViewById(R.id.progressBar7);
        progressQuestions = (ProgressBar)findViewById(R.id.progressBar5);
        buttoncheck = (Button)findViewById(R.id.button6);
        group = (RadioGroup)findViewById(R.id.group);
        buttoncheck.setEnabled(false);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = i;
                if(id == R.id.rd1){
                    rd2.setChecked(false);
                    rd3.setChecked(false);
                    rd4.setChecked(false);
                    buttoncheck.setClickable(true);
                    buttoncheck.setEnabled(true);
                }
                else{
                    buttoncheck.setClickable(true);
                    buttoncheck.setEnabled(true);
                }
            }
        });
        textView2.setText(getIntent().getStringExtra("data"));
        //TODO Сюда добавяем экзамены
        FinalFunction(getString(R.string.acquaintance), question1, choices1, correct1);
        FinalFunction(getString(R.string.dwelling), question2, choices2, correct2);
        FinalFunction(getString(R.string.character),question3, choices3, correct3);
        FinalFunction(getString(R.string.food), question4, choices4, correct4);
        FinalFunction(getString(R.string.shop), question5, choices5, correct5);
    }
    public void FinalFunction(String data, String[] question1, String[][] choices, String[] correct){
        if(textView2.getText().toString().equals(data)){
            Array(choices, question1);
            TheLastFunction(question1, choices, correct);
        }
    }
    public void Array(String[][] array, String[] question){
        rd1.setText(array[questions][0]);
        rd2.setText(array[questions][1]);
        rd3.setText(array[questions][2]);
        rd4.setText(array[questions][3]);
        textView2.setText(question[questions]);
        countofquestions.setText("0" + "/" + question.length);
    }
    public void Function(final String[] question, final String [][] choices, String[] correctanswer){
        buttoncheck = (Button)findViewById(R.id.button6);
        progresslength++;
        progressQuestions.setMax(question.length);
        progressQuestions.setProgress(progresslength);
        progressQuestions.setIndeterminate(false);
        i++;
        RadioButton answer = (RadioButton)findViewById(group.getCheckedRadioButtonId());
        if(answer.getText().toString().equals(correctanswer[i])){
            releaseMP();
            mediaPlayer = MediaPlayer.create(Testing.this, R.raw.answer_is_true);
            mediaPlayer.start();
            if (mediaPlayer == null)
                return;
            mediaPlayer.setOnCompletionListener(Testing.this);
            progress++;
            progressBar.setMax(question.length);
            progressBar.setProgress(progress);
            progressBar.setIndeterminate(false);
            String count = String.valueOf(progress) + "/" + String.valueOf(question.length);
            countofquestions.setText(count);
            final Dialog true_ans = new Dialog(Testing.this, R.style.NoTitle);
            true_ans.setContentView(R.layout.dialog_true);
            true_ans.setCancelable(false);
            Button button = (Button)true_ans.findViewById(R.id.next);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(questions<question.length-1){
                        questions++;
                        rd1.setText(choices[questions][0]);
                        rd2.setText(choices[questions][1]);
                        rd3.setText(choices[questions][2]);
                        rd4.setText(choices[questions][3]);
                        textView2.setText(question[questions]);
                        group.clearCheck();
                        true_ans.dismiss();
                    }else{
                        Intent intent = new Intent(Testing.this, Lessons.class);
                        database.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String done = dataSnapshot.getValue(String.class);
                                database.child("Exam").child(done).child(getIntent().getStringExtra("data")).setValue(countofquestions.getText());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                        true_ans.dismiss();
                    }
                    buttoncheck.setEnabled(false);
                }
            });
            View view1 = Testing.this.getWindow().getDecorView();
            view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
            true_ans.show();
        }else{
            releaseMP();
            mediaPlayer = MediaPlayer.create(Testing.this, R.raw.wrong);
            mediaPlayer.start();
            if (mediaPlayer == null)
                return;
            mediaPlayer.setOnCompletionListener(Testing.this);
            final Dialog false_answer = new Dialog(Testing.this, R.style.NoTitle);
            false_answer.setContentView(R.layout.dialog_wrong);
            false_answer.setCancelable(false);
            final TextView textView = (TextView)false_answer.findViewById(R.id.ans);
            textView.setText(correctanswer[i]);
            textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            Button button = (Button)false_answer.findViewById(R.id.next);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(questions<question.length-1){
                        questions++;
                        rd1.setText(choices[questions][0]);
                        rd2.setText(choices[questions][1]);
                        rd3.setText(choices[questions][2]);
                        rd4.setText(choices[questions][3]);
                        textView2.setText(question[questions]);
                        group.clearCheck();
                        false_answer.dismiss();
                    }else{
                        Intent intent = new Intent(Testing.this, Lessons.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        database.child("Users_name").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String done = dataSnapshot.getValue(String.class);
                                database.child("Exam").child(done).child(getIntent().getStringExtra("data")).setValue(countofquestions.getText());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        startActivity(intent);
                        finish();
                        false_answer.dismiss();
                    }
                    buttoncheck.setEnabled(false);

                }
            });
            View view1 = Testing.this.getWindow().getDecorView();
            view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
            false_answer.show();
        }
    }
    public void TheLastFunction(final String[] question, final String[][] choices, final String[] correct){
        buttoncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Function(question, choices, correct);
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
        final Dialog warning = new Dialog(Testing.this, R.style.NoTitle);
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
                Intent intent = new Intent(Testing.this, Lessons.class);
                startActivity(intent);
                transitionType = Testing.TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        View view1 = Testing.this.getWindow().getDecorView();
        view1.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
}