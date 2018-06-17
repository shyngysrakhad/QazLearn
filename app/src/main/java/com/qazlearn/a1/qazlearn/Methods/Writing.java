package com.qazlearn.a1.qazlearn.Methods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qazlearn.a1.qazlearn.Lessons;
import com.qazlearn.a1.qazlearn.R;

import java.util.Locale;

public class Writing extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    String[] task1answers = {"SA'LEM", "SENIN' ATYN' KIM?", "MENIN' ATYM OLJAS", "MEN ON BESTEMIN", "TANYSQANYMA QY'ANYS'TYMYN", "OL DA", "SAY' BOL"};
    String[] task2answers = {"QALAI'SYN'?", "JAQSY", "SEN QAI'DA TURASYN'?", "MEN AY'YLDA TURAMYN, JEKE U'I'DE", "MEN QALADA TURAMYN, PA'TERDE", "JEKE U'I'", "PA'TER"};
    String[] task3answers = {"SENIN' AG'AN' BAR MA?", "I'A', BAR", "OL QANDAI' ADAM?", "OL UZYN BOI'LY, DENELI, JAQSY JIGIT. MINEZI AQKO'N'IL, JOMART", "TU'SINIKTI", "QYSQA"};
    String[] task4answers = {"TAN'G'Y AS", "SEN TAN'G'Y ASQA NE JEDIN'?", "MEN TAN'G'Y ASQA IRIMS'IK JEDIM JA'NE KOFE IS'TIM", "SEN QANDAI' TAG'AMDY JAQSY KO'RESIN'?", "MEN BALYQ PEN S'UJYQTY JAQSY KO'REMIN", "PAI'DALY", "OL JAQSY KO'REDI", "SEN JAQSY KO'RESIN'"};
    String[] task5answers = {"BIZDIN' DU'KENIMIZGE QOS' KELDIN'IZ!", "BALMUZDAQ", "BUL NES'E TEN'GE TURADY?", "U'S'", "EKI BALMUZDAQ BERE ALASYZ BA?", "JEN'ILDIK", "SAY'DA"};
    TextView wordsorphareses;
    EditText writekazakh;
    Button btncheck;
    int i = 0;
    private int answers = -1;
    int progress = 0;
    ProgressBar mProgress;
    public static enum TransitionType {
        Zoom
    }
    public static com.qazlearn.a1.qazlearn.Methods.LearnWords.TransitionType transitionType;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);
        String[] task1acquaintance = {getString(R.string.helloindialog), getString(R.string.whatsyourname), getString(R.string.mynameisolzhas), getString(R.string.iam15), getString(R.string.thanksgoodluck), getString(R.string.heisalso), getString(R.string.goodbye)};
        String[] task2dwelling = {getString(R.string.howareyou),getString(R.string.fine), getString(R.string.wheredoyoulive), getString(R.string.inthevillage), getString(R.string.inthecity), getString(R.string.privatehouse), getString(R.string.apartment)};
        String[] task3whatmanner = {getString(R.string.doyouhaveabrother), getString(R.string.yesihave), getString(R.string.whatmannerofmanishe), getString(R.string.heisatallmuscularkindman),getString(R.string.itsclear),getString(R.string.ashort), getString(R.string.character)};
        String[] task4food = {getString(R.string.breakfast), getString(R.string.whathaveyouhadforbreakfast), getString(R.string.ihadcheeseandcoffeeforbreakfast), getString(R.string.whatkindoffooddoyoulike), getString(R.string.ilikefishandsausage), getString(R.string.healthy), getString(R.string.helikes), getString(R.string.youlike)};
        String[] task5shop = {getString(R.string.welcometoshop), getString(R.string.icecream),getString(R.string.howmuchdoesitcost),getString(R.string.three),getString(R.string.canyougivemetwopiece),getString(R.string.discount),getString(R.string.sale)};
        mProgress = (ProgressBar)findViewById(R.id.progressBar4);
        wordsorphareses = (TextView)findViewById(R.id.wordsorphareses);
        writekazakh = (EditText)findViewById(R.id.writekazakh);
        btncheck = (Button)findViewById(R.id.btncheck);
        wordsorphareses.setText(getIntent().getStringExtra("data"));
        FinalFunction(getResources().getString(R.string.acquaintance), task1acquaintance, task1answers);
        FinalFunction(getString(R.string.dwelling),task2dwelling, task2answers);
        FinalFunction(getString(R.string.character), task3whatmanner, task3answers);
        FinalFunction(getString(R.string.food),task4food, task4answers);
        FinalFunction(getString(R.string.shop), task5shop, task5answers);
    }
    public void FinalFunction(String data, final String[] task, final String[] answer){
        if(wordsorphareses.getText().toString().equals(data)){
            MainFunction(task, answer, data);
        }
    }
    public void MainFunction(final String[] task, final String[] answer, final String listening){
        wordsorphareses.setText(task[i]);
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answers<task.length){
                    answers++;
                    progress++;
                    mProgress.setProgress(progress);
                    mProgress.setIndeterminate(false);
                    mProgress.setMax(task1answers.length);
                    if (writekazakh.getText().toString().toUpperCase(Locale.getDefault()).equals(answer[answers])){
                        releaseMP();
                        mediaPlayer = MediaPlayer.create(Writing.this, R.raw.answer_is_true);
                        mediaPlayer.start();
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.setOnCompletionListener(Writing.this);
                        final Dialog true_ans = new Dialog(Writing.this, R.style.NoTitle);
                        true_ans.setContentView(R.layout.dialog_true);
                        true_ans.setCancelable(false);
                        Button button = (Button)true_ans.findViewById(R.id.next);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(i<answer.length-1){
                                    i++;
                                    wordsorphareses.setText(task[i]);
                                    true_ans.dismiss();
                                    writekazakh.getText().clear();
                                }else{
                                    Intent intent = new Intent(Writing.this, Listening.class);
                                    intent.putExtra("data", listening);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    finish();
                                    true_ans.dismiss();
                                    writekazakh.getText().clear();
                                }
                            }
                        });
                        true_ans.show();
                    }else{
                        releaseMP();
                        mediaPlayer = MediaPlayer.create(Writing.this, R.raw.wrong);
                        mediaPlayer.start();
                        if (mediaPlayer == null)
                            return;
                        mediaPlayer.setOnCompletionListener(Writing.this);
                        final Dialog false_answer = new Dialog(Writing.this, R.style.NoTitle);
                        false_answer.setContentView(R.layout.dialog_wrong);
                        false_answer.setCancelable(false);
                        final TextView textView = (TextView)false_answer.findViewById(R.id.ans);
                        textView.setText(answer[answers]);
                        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                        Button button = (Button)false_answer.findViewById(R.id.next);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(i<answer.length-1){
                                    i++;
                                    wordsorphareses.setText(task[i]);
                                    false_answer.dismiss();
                                    writekazakh.getText().clear();
                                }else{
                                    Intent intent = new Intent(Writing.this, Listening.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    intent.putExtra("data", listening);
                                    startActivity(intent);
                                    finish();
                                    false_answer.dismiss();
                                    writekazakh.getText().clear();
                                }
                            }
                        });
                        false_answer.show();
                    }
                }else{
                    Intent intent = new Intent(Writing.this, Listening.class);
                    startActivity(intent);
                }
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
        final Dialog warning = new Dialog(Writing.this, R.style.NoTitle);
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
                Intent intent = new Intent(Writing.this, Lessons.class);
                startActivity(intent);
                transitionType = LearnWords.TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        warning.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        final Dialog warning = new Dialog(Writing.this, R.style.NoTitle);
        warning.setContentView(R.layout.dialog_true);
        warning.setCancelable(false);
        TextView textView = (TextView)warning.findViewById(R.id.warning);
        textView.setText(getString(R.string.thismethodeducationiswritingwritethetranslationinKazakh));
        Button button = (Button)warning.findViewById(R.id.next);
        warning.setCancelable(true);
        button.setText(getString(R.string.letswrite));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.dismiss();
            }
        });
        warning.show();
    }
}
