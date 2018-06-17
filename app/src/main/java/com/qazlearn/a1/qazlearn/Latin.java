package com.qazlearn.a1.qazlearn;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class Latin extends AppCompatActivity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{
    EditText cyril;
    TextView latin;
    TabHost.TabSpec tabSpec;
    TabHost tabHost;
    ListView listView;
    String[] array = {"Aa", "A'a'"};
    ArrayAdapter<String> adapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latin);
        listView = (ListView) findViewById(R.id.list_alphabet);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, array);
        listView.setAdapter(adapter);
        latin = (TextView)  findViewById(R.id.text_latin);
        cyril = (EditText)  findViewById(R.id.text_cyrillic);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        cyril.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String b = cyril.getText().toString();
                String c = b.replace("а", "a").replace("ә", "a'").replace("б", "b")
                        .replace("д", "d").replace("е", "e").replace("ф", "f").replace("г", "g")
                        .replace("ғ", "g'").replaceAll("[һх]", "h").replace("і", "i").replaceAll("[ий]", "i'")
                        .replace("ж", "j").replace("к", "k").replace("л", "l").replace("м", "m")
                        .replace("н", "n").replace("ң", "n'").replace("о", "o").replace("ө", "o'")
                        .replace("п", "p").replace("қ", "q").replace("р", "r")
                        .replace("с", "s").replaceAll("[шщ]", "s'").replace("ч", "c'").replace("т", "t")
                        .replace("ұ", "u'").replace("ү", "u'").replace("в", "v").replace("ы", "y")
                        .replace("у", "y'").replace("з", "z").replaceAll("[ъь]", "")
                        .replace("А", "A").replace("Ә", "A'").replace("Б", "B")
                        .replace("Д", "D").replace("Е", "E").replace("Ф", "F").replace("Г", "G")
                        .replace("Ғ", "G'").replace("Х", "H").replace("І", "I").replaceAll("[ИЙ]", "I'")
                        .replace("Ж", "J").replace("К", "K").replace("Л", "L").replace("М", "M")
                        .replace("Н", "N").replace("Ң", "N'").replace("О", "O").replace("Ө", "O'")
                        .replace("П", "P").replace("Қ", "Q").replace("Р", "R")
                        .replace("С", "S").replaceAll("[ШЩ]", "S'").replace("Ч", "C'").replace("Т", "T")
                        .replace("Ұ", "U'").replace("Ү", "U'").replace("В", "V").replace("Ы", "Y")
                        .replace("У", "Y'").replace("З", "Z");
                latin.setText(c);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        latin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied text", latin.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Latin.this, "Успешно скопировано", Toast.LENGTH_SHORT).show();
            }
        });
        tabHost.setup();
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Конвертер");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Алфавит");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTabByTag("tag1");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                letterSound(position, "Aa", R.raw.letter1);
                letterSound(position, "A'a'", R.raw.letter2);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogExample(i,"Aa",R.raw.adam,R.drawable.man,"Adam","Человек");
                DialogExample(i,"A'a'",R.raw.grandmom,R.drawable.grandmother,"A'je","Бабушка");
                return false;
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
    public void DialogExample(int position, String post,final int example, int image, String string, String stringtranslate){
        if (listView.getItemAtPosition(position).toString().equals(post)){
            View view2 = (LayoutInflater.from(Latin.this)).inflate(R.layout.dialogforletters, null);
            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(Latin.this);
            alertbuilder.setView(view2);
            final ImageView letterimage = (ImageView)view2.findViewById(R.id.letterimage);
            final TextView original = (TextView)view2.findViewById(R.id.exampleoriginal);
            final TextView translate = (TextView)view2.findViewById(R.id.exampletranslate);
            alertbuilder.setCancelable(true);
            final Button transcriptionbutton = (Button)view2.findViewById(R.id.btntranscription);
            transcriptionbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    releaseMP();
                    mediaPlayer = MediaPlayer.create(Latin.this, example);
                    mediaPlayer.start();
                    if (mediaPlayer == null)
                        return;
                    mediaPlayer.setOnCompletionListener(Latin.this);
                }
            });
            letterimage.setImageDrawable(getDrawable(image));
            original.setText(string);
            translate.setText(stringtranslate);
            android.app.Dialog dialog = alertbuilder.create();
            dialog.show();
        }
    }
    public void letterSound(int position, String post, int example) {
        if (listView.getItemAtPosition(position).toString().equals(post)) {
            releaseMP();
            mediaPlayer = MediaPlayer.create(Latin.this, example);
            mediaPlayer.start();
            if (mediaPlayer == null)
                return;
            mediaPlayer.setOnCompletionListener(Latin.this);
        }
    }
}
