package com.qazlearn.a1.qazlearn.Methods;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qazlearn.a1.qazlearn.Lessons;
import com.qazlearn.a1.qazlearn.R;

public class Dialog extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    ImageView image, image2;
    Button button, previous;
    TextView dialog1, dialog2, dialog_name;
    int i = -1;
    int q = 0;
    ProgressBar progressBar;
    public static enum TransitionType {
        Zoom
    }
    public static Dialog.TransitionType transitionType;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquaintance);
        final String[] dlga1 = {"Sa'lem\n" + getResources().getString(R.string.hello), "", "Menin' atym S'yn'g'ys\n" + getResources().getString(R.string.mynameisshyngys), "Senin' atyn' kim?\n" + getResources().getString(R.string.whatsyourname), "", "Sen nes'edesin'?\n" + getResources().getString(R.string.howoldareyou), "", "Men on bestemin\n" + getResources().getString(R.string.iam15), "Tanysqanyma qy'anys'tymyn\n" + getResources().getString(R.string.thanksgoodluck), "", "Kezdeskens'e\n" + getResources().getString(R.string.seeyou), ""};
        final String[] dlgb1 = {"", "Sa'lem\n" + getResources().getString(R.string.hello), "", "", "Menin' atym Abdy'lg'afy'r\n" + getResources().getString(R.string.mynameisabdulik), "", "Men on altydamyn, al sen s'e?\n" + getResources().getString(R.string.iam16andyou), "", "", "Men de\n" + getResources().getString(R.string.iamalso), "", "Kezdeskens'e\n" + getResources().getString(R.string.seeyou)};
        final String[] dlga2 = {"Sa'lem\n" + getString(R.string.hello), "", "Qalai'syn'?\n" + getString(R.string.howareyou),"", "", "Jaqsy, rahmet\n" + getString(R.string.good), "Sen qai'da turasyn'?\n" + getString(R.string.wheredoyoulive), "", "", "Men ay'ylda turamyn, jeke u'i'de\n"+getString(R.string.inthevillage),"", "Sen de\n"+getString(R.string.youarealso)};
        final String[] dlgb2 = {"", "Sa'lem\n" + getString(R.string.hello), "", "Jaqsy, rahmet!\n" + getString(R.string.good), "O'zin' qalai'syn'?\n"+getString(R.string.andyou), "", "","Men qalada turamyn, pa'terde\n"+getString(R.string.inthecity), "Al sen s'e?\n" + getString(R.string.andyou), "", "Jarai'dy, kelip tur\n"+getString(R.string.comein), ""};
        final String[] dlga3 = {"Sa'lem\n"+getString(R.string.hello), "", "Senin' ag'an' bar ma?\n"+getString(R.string.doyouhaveabrother),"", "Ol qandai' adam?\n"+getString(R.string.whatmannerofmanishe),"", "Tu'sinikti\n"+getString(R.string.itsclear)};
        final String[] dlgb3 = {"", "Sa'lem\n"+getString(R.string.hello),"","I'a', bar\n"+getString(R.string.yesihave), "", "Ol uzyn boi'ly, deneli, jaqsy jigit. Minezi aqko'n'il, jomart\n"+getString(R.string.heisatallmuscularkindman), ""};
        final String[] dlga4 = {"Sa'lem\n"+getString(R.string.hello), "", "Sen tan'g'y asqa ne jedin'?\n"+getString(R.string.whathaveyouhadforbreakfast), "", "", "Men tan'g'y asqa tek qana s'ai' is'tim\n"+getString(R.string.ihadonlyteaforbreakfast), "Sen qandai' tag'amdy jaqsy ko'resin'?\n"+getString(R.string.whatkindoffooddoyoulike), "", "", "Men jemis-ji'dekti jaqsy ko'remin, sebebi ol pai'daly\n"+getString(R.string.ilikefruit)};
        final String[] dlgb4 = {"", "Sa'lem\n"+getString(R.string.hello), "", "Men tan'g'y asqa irims'ik jedim ja'ne kofe is'tim\n"+getString(R.string.ihadcheeseandcoffeeforbreakfast), "Al sen s'e?\n"+getString(R.string.andyou), "", "", "Men balyq pen s'ujyqty jaqsy ko'remin\n"+getString(R.string.ilikefishandsausage), "Al sen s'e?\n"+getString(R.string.andyou), ""};
        final String[] dlga5 = {"Bizdin' du'kenimizge qos' keldin'iz!\n"+getString(R.string.welcometoshop), "","A'ri'ne\n"+getString(R.string.ofcourse), "","Ju'z ten'ge\n"+getString(R.string.onehundredtenge), "","Minekei'\n"+getString(R.string.hereyouare), "", "Satyp alg'anyn'yzg'a rahmet, kelip turan'yz\n"+getString(R.string.thankyouforshopping)};
        final String[] dlgb5 = {"", "Sizde balmuzdaq bar ma?\n"+getString(R.string.doyouhaveanyicecream), "","Bul nes'e ten'ge turady?\n"+getString(R.string.howmuchdoesitcost), "", "Eki balmuzdaq bere alasyz ba?\n"+getString(R.string.canyougivemetwopiece), "","Rahmet\n"+getString(R.string.thankyou), ""};
        final String[] expo1 = {"Sa'lem, Abdy'lg'afy'r\n"+getString(R.string.hello), "","Sen EXPO ko'rmesine qatysasyn' ba?\n"+getString(R.string.doyoutakeapartinexpo), "", "Men de\n"+getString(R.string.iamalso), "Men Pavlodardanmyn. Sen qai' qaladansyn'?\n"+getString(R.string.iamfrompavlodar), "", "Ko'riskens'e\n"+getString(R.string.seeyou), ""};
        final String[] expo2 = {"", "Sa'lem, S'yn'g'ys\n"+getString(R.string.hello),"", "A'ri'ne, al sen s'e?\n"+getString(R.string.ofcourseandyou), "", "", "Al men Almatydanmyn\n"+getString(R.string.iamfromalmaty), "", "Ko'riskens'e\n"+getString(R.string.seeyou)};
        image2 = (ImageView) findViewById(R.id.imageView19);
        image = (ImageView)  findViewById(R.id.imageView16);
        dialog_name = (TextView)findViewById(R.id.dialog_name);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        button = (Button) findViewById(R.id.btncheck);
        previous = (Button)findViewById(R.id.previous);
        dialog1 = (TextView) findViewById(R.id.dialog1);
        dialog2 = (TextView) findViewById(R.id.dialog2);
        TranslateAnimation anim1 = new TranslateAnimation(400, 10, 50, 50);
        TranslateAnimation anim = new TranslateAnimation(-400,  1, 50, 50);
        anim1.setDuration(1000);
        anim.setDuration(1000);
        anim1.setFillAfter(true);
        anim.setFillAfter(true);
        image2.setAnimation(anim1);
        image.setAnimation(anim);
        previous.setVisibility(View.GONE);
        dialog_name.setText(getIntent().getStringExtra("data"));
        FinalFunction(getString(R.string.acquaintance), dlga1, dlgb1, "Tanysy'\n" + getString(R.string.acquaintance));
        FinalFunction(getString(R.string.dwelling), dlga2, dlgb2, "Sen qai'da turasyn'?\n"+getString(R.string.wheredoyoulive));
        FinalFunction(getString(R.string.character), dlga3, dlgb3, "Ol qandai' adam?\n"+getString(R.string.whatmannerofmanishe));
        FinalFunction(getString(R.string.food), dlga4, dlgb4, "Tag'am\n"+getString(R.string.food));
        FinalFunction(getString(R.string.shop), dlga5, dlgb5, "Du'kende\n"+getString(R.string.intheshop));
        FinalFunction("Expo", expo1, expo2, "Expo 2017");
        //TODO добавить аудио для EXPO
    }
    public void FinalFunction(String data, String[] array1, String[] array2, String naming){
        if(dialog_name.getText().equals(data)){
            ButtonFunction(array1, array2, data, naming);
            ButtonPrevious(array1, array2, array1);
        }
    }
    public void MainFunctionNext(String[] strdialog1, String[] strdialog2, String data){
        q++;
        i++;
        progressBar.setProgress(q);
        progressBar.setIndeterminate(false);
        progressBar.setMax(strdialog1.length+1);
        if (i < strdialog1.length) {
            dialog1.setText(strdialog1[i]);
            dialog2.setText(strdialog2[i]);
            ChatImage1(dialog1);
            ChatImage2(dialog2);
            Dialog1();
            Dialog2();
        }else{
            Intent intent = new Intent(Dialog.this, LearnWords.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
        if(dialog1.getText().toString().trim().contains(strdialog1[0])){
            previous.setVisibility(View.GONE);
        }else{
            previous.setVisibility(View.VISIBLE);
        }
        button.setEnabled(false);
        previous.setEnabled(false);
    }
    public void MainFunctionPrevious(String[] strdialog1, String[] strdialog2, String[] data){
        q--;
        i--;
        progressBar.setProgress(q);
        progressBar.setIndeterminate(false);
        progressBar.setMax(strdialog1.length+1);
        dialog1.setText(strdialog1[i]);
        dialog2.setText(strdialog2[i]);
        ChatImage2(dialog2);
        ChatImage1(dialog1);
        Dialog1();
        Dialog2();
        if(dialog1.getText().toString().trim().contains(data[0])){
            previous.setVisibility(View.GONE);
        }else{
            previous.setVisibility(View.VISIBLE);
        }
        previous.setEnabled(false);
        button.setEnabled(false);
    }
    public void FunctionForDialog(TextView textView, String data, int i){
        if(textView.getText().toString().trim().contains(data)){
            releaseMP();
            mediaPlayer = MediaPlayer.create(this, i);
            mediaPlayer.start();
            if (mediaPlayer == null)
                return;
            mediaPlayer.setOnCompletionListener(this);
        }
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
    public void Dialog2() {
        FunctionForDialog(dialog2, "Sa'lem\n", R.raw.dialog2); FunctionForDialog(dialog2, "Jaqsy, rahmet!", R.raw.jaksyrahmet1);
        FunctionForDialog(dialog2, "Menin' atym Abdy'lg'afy'r\n", R.raw.dialog5); FunctionForDialog(dialog2, "O'zin' qalai'syn'?", R.raw.ozinkalaysin);
        FunctionForDialog(dialog2, "Men on altydamyn, al sen s'e?\n", R.raw.dialog7); FunctionForDialog(dialog2, "Men qalada turamyn, pa'terde", R.raw.menkaladaturamyn);
        FunctionForDialog(dialog2, "Men de\n", R.raw.dialog10); FunctionForDialog(dialog2, "Al sen s'e?", R.raw.alsenshe);
        FunctionForDialog(dialog2, "Kezdeskens'e\n", R.raw.dialog12); FunctionForDialog(dialog2, "Jarai'dy, kelip tur", R.raw.jaraydikeliptur);
        FunctionForDialog(dialog2, "I'a', bar\n", R.raw.yabar); FunctionForDialog(dialog2, "Ol uzyn boi'ly, deneli, jaqsy jigit. Minezi aqko'n'il, jomart\n", R.raw.oluzunboyly);
        FunctionForDialog(dialog2, "Cizde balmuzdaq bar ma?", R.raw.sizdebalmuzdakbarma); FunctionForDialog(dialog2, "Bul nes'e ten'ge turady?", R.raw.bulneshetengeturady);
        FunctionForDialog(dialog2, "Eki balmuzdaq bere alasyz ba?", R.raw.ekibalmuzdakberealasyzba); FunctionForDialog(dialog2, "Rahmet", R.raw.thanks);
        FunctionForDialog(dialog2, "Men tan'g'y asqa irims'ik jedim ja'ne kofe is'tim", R.raw.mentangyaskasirjanekofe); FunctionForDialog(dialog2, "Men balyq pen s'ujyqty jaqsy ko'remin", R.raw.menbalykpensuzhykty);
        FunctionForDialog(dialog2, "A'ri'ne, al sen s'e?\n", R.raw.arinealsenshe); FunctionForDialog(dialog2, "Al men Almatydanmyn\n", R.raw.iamfromalmaty);
        FunctionForDialog(dialog2, "Ko'riskens'e\n", R.raw.koriskenshe2);FunctionForDialog(dialog2, "Sa'lem, S'yn'g'ys\n", R.raw.salemshyn);
    }
    public void Dialog1(){
        FunctionForDialog(dialog1, "Sa'lem\n", R.raw.dialog1); FunctionForDialog(dialog1, "Qalai'syn'?", R.raw.kalaysin);
        FunctionForDialog(dialog1, "Menin' atym S'yn'g'ys\n", R.raw.dialog3); FunctionForDialog(dialog1, "Jaqsy, rahmet", R.raw.jaksyrahmet2);
        FunctionForDialog(dialog1, "Senin' atyn' kim?\n", R.raw.dialog4); FunctionForDialog(dialog1, "Sen qai'da turasyn'?", R.raw.senkaydaturasyn);
        FunctionForDialog(dialog1, "Sen nes'edesin'?\n", R.raw.dialog6); FunctionForDialog(dialog1, "Men ay'ylda turamyn, jeke u'i'de", R.raw.menauldaturamyn);
        FunctionForDialog(dialog1, "Men on bestemin\n", R.raw.dialog8); FunctionForDialog(dialog1, "Sen de", R.raw.sende);
        FunctionForDialog(dialog1, "Tanysqanyma qy'anys'tymyn", R.raw.dialog9); FunctionForDialog(dialog1, "Senin' ag'an' bar ma?\n", R.raw.seninaganbarma);
        FunctionForDialog(dialog1, "Kezdeskens'e\n", R.raw.dialog11); FunctionForDialog(dialog1, "Ol qandai' adam?\n", R.raw.olkandayadam);
        FunctionForDialog(dialog1, "Tu'sinikti\n", R.raw.tusynykty); FunctionForDialog(dialog1, "Bizdin' du'kenimizge qos' keldin'iz!", R.raw.bizdindukenimizgekosh);
        FunctionForDialog(dialog1, "A'ri'ne", R.raw.ofcourse); FunctionForDialog(dialog1, "Ju'z ten'ge", R.raw.hundredtenge);
        FunctionForDialog(dialog1, "Sen tan'g'y asqa ne jedin'?", R.raw.sentangyaskanejedyn); FunctionForDialog(dialog1, "Men tan'g'y asqa tek qana s'ai' is'tim", R.raw.mentangyaskatekkana);
        FunctionForDialog(dialog1, "Satyp alg'anyn'yzg'a rahmet, kelip turan'yz", R.raw.satypalganinizgarahmet); FunctionForDialog(dialog1, "Minekei'", R.raw.hereyouare);
        FunctionForDialog(dialog1, "Sen qandai' tag'amdy jaqsy ko'resin'?", R.raw.senkandaytagamdyjaksykoresin); FunctionForDialog(dialog1, "Men jemis-ji'dekti jaqsy ko'remin, sebebi ol pai'daly", R.raw.menjemisjaksy);
        FunctionForDialog(dialog1, "Sen EXPO ko'rmesine qatysasyn' ba?\n", R.raw.doyoutakeapartexpo); FunctionForDialog(dialog1, "Men Pavlodardanmyn. Sen qai' qaladansyn'?\n", R.raw.iamfrompavlodar);
        FunctionForDialog(dialog1, "Ko'riskens'e\n", R.raw.koriskenshe);FunctionForDialog(dialog1, "Men de\n", R.raw.mende);
        FunctionForDialog(dialog1, "Sa'lem, Abdy'lg'afy'r\n", R.raw.salemabdulik);
    }
    public void ButtonFunction(final String[] strdialog1, final String[] strdialog2, final String data, String naming){
        dialog_name.setText(naming);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFunctionNext(strdialog1, strdialog2, data);
            }
        });
    }
    public void ButtonPrevious(final String[] strdialog1, final String[] strdialog2, final String[] string){
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFunctionPrevious(strdialog1, strdialog2, string);
            }
        });
    }
    public void ChatImage2(TextView textView){
        if(!textView.getText().toString().equals("")){
            textView.setBackgroundResource(R.drawable.dialog);
            image2.setBackgroundResource(R.drawable.dialog_anim2);
            AnimationDrawable animation2 = (AnimationDrawable) image2.getBackground();
            animation2.start();
        }else{
            textView.setBackgroundResource(0);
        }
    }
    public void ChatImage1(TextView textView){
        if(!textView.getText().toString().equals("")){
            textView.setBackgroundResource(R.drawable.dialog1);
            image.setBackgroundResource(R.drawable.dialog_anim1);
            AnimationDrawable animation = (AnimationDrawable) image.getBackground();
            animation.start();
        }else{
            textView.setBackgroundResource(0);
        }
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        image.setBackground(getDrawable(R.drawable.boy1));
        image2.setBackground(getDrawable(R.drawable.boy11));
        previous.setEnabled(true);
        button.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
    @Override
    public void onStart(){
        super.onStart();
        final android.app.Dialog warning = new android.app.Dialog(Dialog.this, R.style.NoTitle);
        warning.setContentView(R.layout.dialog_true);
        warning.setCancelable(true);
        TextView textView = (TextView)warning.findViewById(R.id.warning);
        textView.setText(getString(R.string.dialog));
        Button button = (Button)warning.findViewById(R.id.next);
        button.setText(getString(R.string.lestlearn));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.dismiss();
            }
        });
        View view = this.getWindow().getDecorView();
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
    @Override
    public void onBackPressed() {
        final android.app.Dialog warning = new android.app.Dialog(Dialog.this, R.style.NoTitle);
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
                Intent intent = new Intent(Dialog.this, Lessons.class);
                startActivity(intent);
                transitionType = Dialog.TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        View view = this.getWindow().getDecorView();
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
}