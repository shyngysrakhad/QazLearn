package com.qazlearn.a1.qazlearn.Methods;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qazlearn.a1.qazlearn.ExpoTest;
import com.qazlearn.a1.qazlearn.Lessons;
import com.qazlearn.a1.qazlearn.R;

import java.io.IOException;

public class LearnWords extends Activity implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    public static enum TransitionType {
        Zoom
    }
    public static LearnWords.TransitionType transitionType;
    int count = 0;
    TextView verbsinkazakh, verbinforeign;
    ImageView imageoption1, imageoption2, imageoption3, imageoption4, imageoption5;
    Button button, txtoption1, txtoption2, txtoption3, txtoption4, txtoption5, btnlisten, prev;
    ProgressBar progressBar;
    LinearLayout linearLayout3, linearLayout4, linearLayout5;
    int progress = 0;
    private String[] dialog1 = {"Sa'lem", "Senin' atyn' kim?", "Menin' atym S'yn'g'ys", "Sen nes'e jastasyn'?", "Men on bestemin", "Tanysqanyma qy'anys'tymyn", "Men de", "Kezdeskens'e"};
    private String[] dialog2 = {"Qalai'syn'?", "Jaqsy, rahmet!", "Sen qai'da turasyn'?", "Men qalada turamyn, pa'terde", "Men ay'ylda turamyn, jeke u'i'de", "Al sen s'e?"};
    private String[] dialog3 = {"Senin' ag'an' bar ma?", "I'a', bar", "Ol qandai' adam?", "Ol uzyn boi'ly, deneli, jaqsy jigit. Minezi aqko'n'il, jomart", "Tu'sinikti"};
    private String[] dialog4 = {"Sen tan'g'y asqa ne jedin'?", "Men tan'g'y asqa irims'ik jedim ja'ne kofe is'tim", "Sen qandai' tag'amdy jaqsy ko'resin'?","Men balyq pen s'ujyqty jaqsy ko'remin","Men jemis-ji'dekti jaqsy ko'remin, sebebi ol pai'daly"};
    private String[] dialog5 = {"Bizdin' du'kenimizge qos' keldin'iz!", "Cizde balmuzdaq bar ma?", "Bul nes'e ten'ge turady?", "Ju'z ten'ge", "Eki balmuzdaq bere alasyz ba?", "Satyp alg'anyn'yzg'a rahmet, kelip turan'yz"};
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.method_words);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        verbsinkazakh = (TextView) findViewById(R.id.verbsinkazakh);
        verbinforeign = (TextView) findViewById(R.id.verbinforeign);
        txtoption1 = (Button)findViewById(R.id.txtoption1);
        txtoption2 = (Button)findViewById(R.id.txtoption2);
        txtoption3 = (Button)findViewById(R.id.txtoption3);
        txtoption4 = (Button)findViewById(R.id.txtoption4);
        txtoption5 = (Button)findViewById(R.id.txtoption5);
        btnlisten = (Button)findViewById(R.id.btnlisten);
        prev = (Button)findViewById(R.id.prev);
        imageoption1 = (ImageView)findViewById(R.id.imageoption1);
        imageoption2 = (ImageView)findViewById(R.id.imageoption2);
        imageoption3 = (ImageView)findViewById(R.id.imageoption3);
        imageoption4 = (ImageView)findViewById(R.id.imageoption4);
        imageoption5 = (ImageView)findViewById(R.id.imageoption5);
        linearLayout3 = (LinearLayout)findViewById(R.id.linearoption3);
        linearLayout4 = (LinearLayout)findViewById(R.id.linearoption4);
        linearLayout5 = (LinearLayout)findViewById(R.id.linearoption5);
        button = (Button) findViewById(R.id.button1);
        verbsinkazakh.setText(getIntent().getStringExtra("data"));
        PicAndTranslate(verbsinkazakh);
        FinalFunction(getResources().getString(R.string.acquaintance), dialog1);
        FinalFunction(getString(R.string.dwelling), dialog2);
        FinalFunction(getString(R.string.character), dialog3);
        FinalFunction(getString(R.string.food), dialog4);
        FinalFunction(getString(R.string.shop), dialog5);
    }
    public void FinalFunction(String data, String[] array){
        if(verbsinkazakh.getText().equals(data)){
            FunctionNext(array, data);
            FunctionPrev(array);
            if(verbsinkazakh.getText().toString().trim().contains(array[0])){
                prev.setVisibility(View.GONE);
            }else{
                prev.setVisibility(View.VISIBLE);
            }
        }
        if(verbsinkazakh.getText().toString().equals("Expo")){
            Intent intent = new Intent(LearnWords.this, ExpoTest.class);
            startActivity(intent);
            transitionType = LearnWords.TransitionType.Zoom;
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            finish();
        }
    }
    public void hideprev(String[] string, Button buttonprev){
        if(verbsinkazakh.getText().toString().trim().contains(string[0])){
            prev.setVisibility(View.GONE);
        }else{
            prev.setVisibility(View.VISIBLE);
        }
    }
    public void FunctionMedia(String textoption1, String textoption2, String textoption3, String textoption4, String textoption5,
                               String inforeign, Drawable drawable1, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5){
            verbinforeign.setText(inforeign);
            txtoption1.setText(textoption1);
            txtoption2.setText(textoption2);
            txtoption3.setText(textoption3);
            txtoption4.setText(textoption4);
            txtoption5.setText(textoption5);
            imageoption1.setImageDrawable(drawable1);
            imageoption2.setImageDrawable(drawable2);
            imageoption3.setImageDrawable(drawable3);
            imageoption4.setImageDrawable(drawable4);
            imageoption5.setImageDrawable(drawable5);
            if (txtoption3.getText().toString().equals("")){
                linearLayout3.setVisibility(View.GONE);
            }else{
                linearLayout3.setVisibility(View.VISIBLE);
            }
            if (txtoption4.getText().toString().equals("")){
                linearLayout4.setVisibility(View.GONE);
            }else{
                linearLayout4.setVisibility(View.VISIBLE);
            }
            if (txtoption5.getText().toString().equals("")){
                linearLayout5.setVisibility(View.GONE);
            }else{
                linearLayout5.setVisibility(View.VISIBLE);
            }
            hideImageView(imageoption1);
            hideImageView(imageoption2);
            hideImageView(imageoption3);
            hideImageView(imageoption4);
            hideImageView(imageoption5);
    }
    public void FunctionNext(final String[] dialog, final String data){
        hideprev(dialog, prev);
        verbsinkazakh.setText(dialog[count]);
        PicAndTranslate(verbsinkazakh);
        FinalSoundButton();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress++;
                progressBar.setProgress(progress);
                progressBar.setIndeterminate(false);
                progressBar.setMax(dialog.length);
                if(count < dialog.length-1){
                    count++;
                    verbsinkazakh.setText(dialog[count]);
                    PicAndTranslate(verbsinkazakh);
                    FinalSoundButton();
                }else{
                    Intent intent = new Intent(LearnWords.this, Writing.class);
                    transitionType = LearnWords.TransitionType.Zoom;
                    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
                if(verbsinkazakh.getText().toString().contains(dialog[0])){
                    prev.setVisibility(View.GONE);
                }else{
                    prev.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void FunctionPrev(final String[] dialog){
        FinalSoundButton();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress--;
                progressBar.setProgress(progress);
                progressBar.setIndeterminate(false);
                progressBar.setMax(dialog.length);
                count--;
                verbsinkazakh.setText(dialog[count]);
                PicAndTranslate(verbsinkazakh);
                hideprev(dialog, prev);
                FinalSoundButton();
            }
        });
    }
    public void PicAndTranslate(TextView textView) {
        switch (textView.getText().toString()) {
            //TODO Здесь пишем варианты
            case "Senin' atyn' kim?":
                EachButtonSound(0, 0, 0, 0, 0);
                FunctionMedia("Сенің = " + getResources().getString(R.string.your), "ат(ың) = " + getString(R.string.name), "кім? = " + getString(R.string.who), "", "",
                        getResources().getString(R.string.whatsyourname), null, null, null, null, null);
                break;
            case "Sa'lem":
                EachButtonSound(R.raw.assalaumagaleykum, R.raw.salemetsizbe, R.raw.dialog1, 0, 0);
                FunctionMedia("Assalay'mag'alei'ky'm\n" + getResources().getString(R.string.toadults), "Sa'lemetsiz be\n" + getResources().getString(R.string.towoman), "Sa'lem\n" + getResources().getString(R.string.tofriends),
                        "", "", getResources().getString(R.string.helloindialog), getDrawable(R.drawable.grandfather), getDrawable(R.drawable.mother), getDrawable(R.drawable.boy), null, null);
                break;
            case "Men de":
                EachButtonSound(0, 0, 0, 0, 0);
                FunctionMedia("Men = " + getResources().getString(R.string.i), "de(da) = " + getResources().getString(R.string.also), "Ol da = " + getResources().getString(R.string.heisalso), "", "", getResources().getString(R.string.iamalso), null, null, null, null, null);
                break;
            case "Menin' atym S'yn'g'ys":
                EachButtonSound(R.raw.seninatynabdulik, R.raw.onynatyolzhas, R.raw.meninesimim, 0, 0);
                FunctionMedia("Senin' atyn' Abdy'lg'afy'r\n" + getResources().getString(R.string.yournameisabdulik), "Onyn' aty Oljas\n\n" + getResources().getString(R.string.hisnameisolzhas), "Menin' esimim Abdy'lg'afy'r = Menin' atym Abdy'lg'afy'r\nesim, at = " + getResources().getString(R.string.name), "", "",
                        getResources().getString(R.string.mynameisshyngys), null, null, null, null, null);
                break;
            case "Sen nes'e jastasyn'?":
                FunctionMedia("sen = " + getResources().getString(R.string.you), "nes'e? = " + getResources().getString(R.string.howmuch), "jas + tasyn', jas = " + getResources().getString(R.string.age), "Ol nes'e jasta?\n" + getResources().getString(R.string.howoldishe), "Sen nes'edesin'? = Sen nes'e jastasyn'?",
                        getResources().getString(R.string.howoldareyou), null, null, null, null, null);
                EachButtonSound(0, 0, 0, R.raw.olneshezhasta, R.raw.dialog6);
                break;
            case "Men on bestemin":
                FunctionMedia("Men = " + getResources().getString(R.string.i), "on bes + temin, on bes = 15", "Men on bestemin = Men on bes jastamyn", "Ol on bes jasta = " + getResources().getString(R.string.heis15age), "", getResources().getString(R.string.iam15), null, null, null, null, null);
                EachButtonSound(0, 0, 0, R.raw.olonbeszhasta, 0);
                break;
            case "Tanysqanyma qy'anys'tymyn":
                EachButtonSound(R.raw.tanysu, 0, R.raw.oltanyskanyna, 0, 0);
                FunctionMedia("TanysY' = " + getResources().getString(R.string.meet), "Qy'anys'ty = " + getResources().getString(R.string.glad), "Ol tanysqanyna qy'anys'ta = " + getResources().getString(R.string.heisgladtomeetyou), "", "", getResources().getString(R.string.thanksgoodluck), null, null, null, null, null);
                break;
            case "Kezdeskens'e":
                FunctionMedia("Say' bol = " + getResources().getString(R.string.goodbye), "Erten' kezdeskens'e = " + getResources().getString(R.string.seeyoutomorrow), "Say' bolyn'yz\n" + getResources().getString(R.string.toadults), "", "", getResources().getString(R.string.seeyou), null, null, getDrawable(R.drawable.grandmother), null, null);
                EachButtonSound(R.raw.saubol, R.raw.ertenkezdeskenshe, R.raw.saubolynyz, 0, 0);
                break;
            case "Qalai'syn'?":
                EachButtonSound(R.raw.kalynkalay, R.raw.kalaysiz, 0, 0, 0);
                FunctionMedia("Qalyn' qalai'?=Qalai'syn'?", "Qalai'syz?=" + getString(R.string.respectfulform), "", "", "", getString(R.string.howareyou), null, getDrawable(R.drawable.grandmother), null, null, null);
                break;
            case "Jaqsy, rahmet!":
                EachButtonSound(R.raw.jaksy, R.raw.rahmet, R.raw.zhaman, R.raw.zhamanemes, R.raw.emes);
                FunctionMedia("Jaqsy=" + getString(R.string.fine), "Rahmet=" + getString(R.string.thankyou), "Jaman=" + getString(R.string.bad), "Jaman emes=" + getString(R.string.notsobad), "emes=" + getString(R.string.not), getString(R.string.good), getDrawable(R.drawable.like), null, getDrawable(R.drawable.dislike), null, null);
                break;
            case "Sen qai'da turasyn'?":
                EachButtonSound(R.raw.sizkaydaturasiz, R.raw.menmundaturamyn, R.raw.munda, R.raw.anda, R.raw.sizkaydasiz);
                FunctionMedia("Siz qai'da turasyz?=" + getString(R.string.respectfulform), "Men munda turamyn=" + getString(R.string.ilivehere), "Munda=" + getString(R.string.here), "Anda=" + getString(R.string.there), "Siz qai'dasyz?=" + getString(R.string.whereareyou), getString(R.string.wheredoyoulive), getDrawable(R.drawable.grandmother), null, null, null, null);
                break;
            case "Men qalada turamyn, pa'terde":
                EachButtonSound(R.raw.city, R.raw.pater, R.raw.kabat, R.raw.korshy, R.raw.menturamyn);
                FunctionMedia("Qala=" + getString(R.string.city), "Pa'ter=" + getString(R.string.apartment), "Qabat=" + getString(R.string.floor), "Ko'rs'i=" + getString(R.string.neighbor), "Men turamyn=" + getString(R.string.ilive), getString(R.string.inthecity), getDrawable(R.drawable.city), getDrawable(R.drawable.ic_apartment), getDrawable(R.drawable.ic_floor), null, null);
                break;
            case "Men ay'ylda turamyn, jeke u'i'de":
                EachButtonSound(R.raw.zhekeuy, R.raw.aul, R.raw.senturasyn, R.raw.korshy, R.raw.olturady);
                FunctionMedia("Jeke u'i'=" + getString(R.string.privatehouse), "Ay'yl=" + getString(R.string.village), "Sen turasyn'=" + getString(R.string.youlive), "Ko'rs'i=" + getString(R.string.neighbor), "Ol turady=" + getString(R.string.helives), getString(R.string.inthevillage), getDrawable(R.drawable.ic_house), getDrawable(R.drawable.village), null, null, null);
                break;
            case "Al sen s'e?":
                EachButtonSound(R.raw.al, R.raw.birak, 0, 0, 0);
                FunctionMedia("Al=" + getString(R.string.but), "Al=biraq", "", "", "", getString(R.string.andyou), null, null, null, null, null);
                break;
            case "Senin' ag'an' bar ma?":
                EachButtonSound(R.raw.brother, R.raw.sister, R.raw.mother, R.raw.father, 0);
                FunctionMedia("Ag'a=" + getString(R.string.brother), "A'pke=" + getString(R.string.sister), "Ana=" + getString(R.string.mother), "A'ke=" + getString(R.string.father), "", getString(R.string.doyouhaveabrother), getDrawable(R.drawable.brother), getDrawable(R.drawable.sister), getDrawable(R.drawable.mother), getDrawable(R.drawable.father), null);
                break;
            case "I'a', bar":
                EachButtonSound(R.raw.yes, R.raw.no, R.raw.grandfather, R.raw.grandmother, 0);
                FunctionMedia("I'a'=" + getString(R.string.yes), "Joq=" + getString(R.string.no), "Ata=" + getString(R.string.grandfather), "Apa=" + getString(R.string.grandmother), "", getString(R.string.yesihave), null, null, getDrawable(R.drawable.grandfather), getDrawable(R.drawable.grandmother), null);
                break;
            case "Ol qandai' adam?":
                EachButtonSound(R.raw.which, R.raw.person, R.raw.senkandayadamsyn, R.raw.ol, R.raw.kisi);
                FunctionMedia("Qandai'?=" + getString(R.string.which), "Adam=" + getString(R.string.person), "Sen qandai' adamsyn'?=" + getString(R.string.whatmannerofmanareyou), "Ol=" + getString(R.string.he), "kisi=adam", getString(R.string.whatmannerofmanishe), null, null, null, null, null);
                break;
            case "Ol uzyn boi'ly, deneli, jaqsy jigit. Minezi aqko'n'il, jomart":
                EachButtonSound(R.raw.tall, R.raw.growth, R.raw.ashort, R.raw.man, R.raw.character);
                FunctionMedia("Uzyn=" + getString(R.string.tall), "Boi'=" + getString(R.string.growth), "Qysqa=" + getString(R.string.ashort), "Jigit=" + getString(R.string.man), "Minez=" + getString(R.string.character), getString(R.string.heisatallmuscularkindman), null, null, null, null, null);
                break;
            case "Tu'sinikti":
                EachButtonSound(R.raw.tusynyksiz, R.raw.tusynyktyemes, 0, 0, 0);
                FunctionMedia("Tu'siniksiz=" + getString(R.string.itisnotclear), "Tu'sinikti emes=Tu'siniksiz", "emes=" + getString(R.string.not), "", "", getString(R.string.itsclear), null, null, null, null, null);
                break;
            case "Sen tan'g'y asqa ne jedin'?":
                EachButtonSound(R.raw.tangyas, 0, 0, 0, 0);
                FunctionMedia("Tan'g'y as=" + getString(R.string.breakfast), "Sen ne jedin'?=" + getString(R.string.whatdidyoueat), "Ol ne jedi?=" + getString(R.string.whatdidheeat), "ne=" + getString(R.string.what), "jey'=" + getString(R.string.toeat), getString(R.string.whathaveyouhadforbreakfast), getDrawable(R.drawable.breakfast), null, null, null, null);
                break;
            case "Men tan'g'y asqa irims'ik jedim ja'ne kofe is'tim":
                EachButtonSound(R.raw.cheese, R.raw.coffee, R.raw.menistim, R.raw.senishtin, 0);
                FunctionMedia("irims'ik=" + getString(R.string.cheese), "kofe=" + getString(R.string.coffee), "Men is'tim=" + getString(R.string.idrank), "Sen is'tin'=" + getString(R.string.youdrank), "Ol is'ti=" + getString(R.string.hedrank), getString(R.string.ihadcheeseandcoffeeforbreakfast), getDrawable(R.drawable.cheese), getDrawable(R.drawable.coffee), null, null, null);
                break;
            case "Sen qandai' tag'amdy jaqsy ko'resin'?":
                EachButtonSound(0, 0, 0, R.raw.tagam, R.raw.tamak);
                FunctionMedia("Men jaqsy ko'remin=" + getString(R.string.ilike), "Sen jaqsy ko'resin'=" + getString(R.string.youlike), "Ol jaqsy ko'redi=" + getString(R.string.helikes), "tag'am=" + getString(R.string.food), "tamaq=tag'am", getString(R.string.whatkindoffooddoyoulike), null, null, null, null, null);
                break;
            case "Men balyq pen s'ujyqty jaqsy ko'remin":
                EachButtonSound(R.raw.fish, R.raw.sausage, R.raw.pen, 0, R.raw.and);
                FunctionMedia("Balyq=" + getString(R.string.fish), "S'ujyq=" + getString(R.string.sausage), "pen=" + getString(R.string.and), "ben=pen" + getString(R.string.mild), "ja'ne=" + getString(R.string.and), getString(R.string.ilikefishandsausage), getDrawable(R.drawable.fish), getDrawable(R.drawable.sausage), null, null, null);
                break;
            case "Men jemis-ji'dekti jaqsy ko'remin, sebebi ol pai'daly":
                EachButtonSound(R.raw.zhemyszhidek, R.raw.because, R.raw.healthy, 0, R.raw.harmful);
                FunctionMedia("Jemis-ji'dek=" + getString(R.string.fruit), "Sebebi=" + getString(R.string.because), "Pai'daly=" + getString(R.string.healthy), "O'i'tkeni=" + getString(R.string.because), "zi'i'andy=" + getString(R.string.harmful), getString(R.string.ilikefruit), getDrawable(R.drawable.fruit), null, null, null, null);
                break;
            case "Bizdin' du'kenimizge qos' keldin'iz!":
                EachButtonSound(0, R.raw.shop, R.raw.bizdinduken, R.raw.sauda, R.raw.discount);
                FunctionMedia("Bizdin'=" + getString(R.string.our), "Du'ken=" + getString(R.string.shop), "Bizdin' du'ken=" + getString(R.string.our) + " " + getString(R.string.shop), "Say'da=" + getString(R.string.sale), "Jen'ildik=" + getString(R.string.discount), getString(R.string.welcometoshop), null, getDrawable(R.drawable.store), null, getDrawable(R.drawable.sale), getDrawable(R.drawable.discount));
                break;
            case "Cizde balmuzdaq bar ma?":
                EachButtonSound(R.raw.icecream, R.raw.mendebalmuzdakbar, R.raw.mendebalmuzdakzhok, R.raw.sizdebalmuzdakbar, R.raw.odabalmuzdakbar);
                FunctionMedia("Balmuzdaq=" + getString(R.string.icecream), "Mende balmuzdaq bar=" + getString(R.string.ihaveanicecream), "Mende balmuzdaq joq=" + getString(R.string.ihavenoticecream), "Sizde balmuzdaq bar=" + getString(R.string.youhaveicecream), "Oda balmuzdaq bar=" + getString(R.string.hehasicecream), getString(R.string.doyouhaveanyicecream), getDrawable(R.drawable.icecream), null, null, null, null);
                break;
            case "Bul nes'e ten'ge turady?":
                EachButtonSound(R.raw.thisthat, R.raw.tenge, 0, R.raw.bulturady, 0);
                FunctionMedia("Bul=" + getString(R.string.thisthat), "Tenge=" + getString(R.string.kazakhcurrecny), "Bul qans'a tenge turady?=Bul nes'e ten'ge turady?", "Bul turady=" + getString(R.string.itcosts), "nes'e=" + getString(R.string.howmuch), getString(R.string.howmuchdoesitcost), null, getDrawable(R.drawable.tenge), null, null, null);
                break;
            case "Ju'z ten'ge":
                EachButtonSound(R.raw.hundred, R.raw.debt, R.raw.ten, 0, R.raw.threehundred);
                FunctionMedia("Ju'z=" + getString(R.string.hundred), "Qaryz=" + getString(R.string.debt), "on=" + getString(R.string.ten), "u's'=" + getString(R.string.three), "u's' ju'z=" + getString(R.string.threehundred), getString(R.string.onehundredtenge), getDrawable(R.drawable.hundred), null, getDrawable(R.drawable.ten), getDrawable(R.drawable.three), null);
                break;
            case "Eki balmuzdaq bere alasyz ba?":
                EachButtonSound(R.raw.two, R.raw.menbalmuzdakberemyn, R.raw.menbalmuzdakberealamyn, R.raw.ekibalmuzdakberiniz, R.raw.ekibalmuzdakberealasyzba);
                FunctionMedia("Eki=" + getString(R.string.two), "Men balmuzdaq beremin=" + getString(R.string.igiveicecream), "Men balmuzdaq bere alamyn=" + getString(R.string.icangiveicecream), "Eki balmuzdaq berin'iz=" + getString(R.string.imperativemood), "", getString(R.string.canyougivemetwopiece), null, null, null, null, null);
                break;
            case "Satyp alg'anyn'yzg'a rahmet, kelip turyn'yz":
                EachButtonSound(R.raw.mensatypadlim, R.raw.menkelipturamyn, R.raw.magansatypalypbershi, 0, 0);
                FunctionMedia("Men satyp aldym=" + getString(R.string.ibought), "Men kelip turamyn=" + getString(R.string.icomeagain), "Mag'an satyp alyp bers'i=" + getString(R.string.buyme), "... satyp alyp bere alasyz ba?=" + getString(R.string.canyoubuyme), "", getString(R.string.thankyouforshopping), null, null, null, null, null);
                break;
        }
    }
        public void EachButtonSound(final int media1, final int media2, final int media3, final int media4, final int media5){
        hidesoundicon(txtoption1, media1);
        hidesoundicon(txtoption2, media2);
        hidesoundicon(txtoption3, media3);
        hidesoundicon(txtoption4, media4);
        hidesoundicon(txtoption5, media5);
        txtoption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                if (media1 != 0){
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media1);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            }
        });
        txtoption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                if (media2 != 0){
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media2);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            }
        });
        txtoption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                if (media3 != 0){
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media3);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            }
        });
        txtoption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                if (media4 != 0) {
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media4);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            }
        });
        txtoption5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMP();
                if (media5 != 0){
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media5);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            }
        });
    }
    public void hidesoundicon(Button button, int i){
        if(i != 0){
            button.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.speak_on,0,0);
        }else{
            button.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.speak_off,0,0);
        }
    }
    public void hideImageView(ImageView image){
        if (image.getDrawable() == null){
            image.setVisibility(View.GONE);
        }else{
            image.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {
        final Dialog warning = new Dialog(LearnWords.this, R.style.NoTitle);
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
                Intent intent = new Intent(LearnWords.this, Lessons.class);
                startActivity(intent);
                transitionType = LearnWords.TransitionType.Zoom;
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        });
        View view = this.getWindow().getDecorView();
        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        warning.show();
    }
    public void SoundButton(final String data, final int media){
        if(verbsinkazakh.getText().toString().equals(data)){
            btnlisten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    releaseMP();
                    mediaPlayer = MediaPlayer.create(LearnWords.this, media);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(LearnWords.this);
                }
            });
        }
    }
    public void FinalSoundButton() {
        //TODO Записать голоса здесь
        SoundButton("Sa'lem", R.raw.dialog1);
        SoundButton("Sen nes'e jastasyn'?", R.raw.senneshejastasyn);
        SoundButton("Men on bestemin", R.raw.dialog8);
        SoundButton("Senin' atyn' kim?", R.raw.dialog4);
        SoundButton("Tanysqanyma qy'anys'tymyn", R.raw.dialog9);
        SoundButton("Menin' atym S'yn'g'ys", R.raw.dialog3);
        SoundButton("Men de", R.raw.dialog10);
        SoundButton("Sen nes'e jastasyn'?", R.raw.dialog3);
        SoundButton("Kezdeskens'e", R.raw.dialog12);
        SoundButton("Qalai'syn'?", R.raw.kalaysin);
        SoundButton("Jaqsy, rahmet!", R.raw.jaksyrahmet1);
        SoundButton("Sen qai'da turasyn'?", R.raw.senkaydaturasyn);
        SoundButton("Men qalada turamyn, pa'terde", R.raw.menkaladaturamyn);
        SoundButton("Men ay'ylda turamyn, jeke u'i'de", R.raw.menauldaturamyn);
        SoundButton("Al sen s'e?", R.raw.alsenshe);
        SoundButton("Senin' ag'an' bar ma?", R.raw.seninaganbarma);
        SoundButton("I'a', bar", R.raw.yabar);
        SoundButton("Ol qandai' adam?", R.raw.olkandayadam);
        SoundButton("Ol uzyn boi'ly, deneli, jaqsy jigit. Minezi aqko'n'il, jomart", R .raw.oluzunboyly);
        SoundButton("Tu'sinikti", R.raw.tusynykty);
        SoundButton("Sen tan'g'y asqa ne jedin'?", R.raw.sentangyaskanejedyn);
        SoundButton("Men tan'g'y asqa irims'ik jedim ja'ne kofe is'tim", R.raw.mentangyaskasirjanekofe);
        SoundButton("Sen qandai' tag'amdy jaqsy ko'resin'?", R.raw.senkandaytagamdyjaksykoresin);
        SoundButton("Men balyq pen s'ujyqty jaqsy ko'remin", R.raw.menbalykpensuzhykty);
        SoundButton("Men jemis-ji'dekti jaqsy ko'remin, sebebi ol pai'daly", R.raw.menjemisjaksy);
        SoundButton("Bizdin' du'kenimizge qos' keldin'iz!", R.raw.bizdindukenimizgekosh );
        SoundButton("Cizde balmuzdaq bar ma?", R.raw.sizdebalmuzdakbarma);
        SoundButton("Bul nes'e ten'ge turady?", R.raw.bulneshetengeturady);
        SoundButton("Ju'z ten'ge", R.raw.hundredtenge);
        SoundButton("Eki balmuzdaq bere alasyz ba?", R.raw.ekibalmuzdakberealasyzba );
        SoundButton("Satyp alg'anyn'yzg'a rahmet, kelip turyn'yz", R.raw.satypalganinizgarahmet);
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
}