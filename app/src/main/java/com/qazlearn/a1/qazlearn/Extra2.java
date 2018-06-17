package com.qazlearn.a1.qazlearn;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 1 on 07.04.2017.
 */

public class Extra2 extends BaseAdapter implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    private Context mContext;
    private List<Extra> mProductList;
    MediaPlayer mediaPlayer;
    ProgressBar progress;
    public Extra2(Context mContext, List<Extra> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }
    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.item_expo, null);
        TextView kazakh = (TextView)v.findViewById(R.id.expokazakh);
        TextView russian = (TextView)v.findViewById(R.id.exporussian);
        TextView english = (TextView)v.findViewById(R.id.expoenglish);
        kazakh.setText(mProductList.get(i).getKazakh());
        russian.setText(String.valueOf(mProductList.get(i).getRussian()));
        english.setText(mProductList.get(i).getEnglish());
        v.setTag(mProductList.get(i).getId());
        return v;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //button.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        mediaPlayer.start();
    }
}
