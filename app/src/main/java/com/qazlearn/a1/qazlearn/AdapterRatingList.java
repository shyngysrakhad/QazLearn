package com.qazlearn.a1.qazlearn;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterRatingList extends BaseAdapter{
    private Context mContext;
    private List<Rating> mProductList;
    public AdapterRatingList(Context mContext, List<Rating> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_ratinglist, null);
        TextView tvName = (TextView)v.findViewById(R.id.user_name_list);
        TextView tvPrice = (TextView)v.findViewById(R.id.country_list);
        TextView tvDescription = (TextView)v.findViewById(R.id.point_list);
        tvName.setText(mProductList.get(position).getName());
        tvPrice.setText(String.valueOf(mProductList.get(position).getPrice()));
        tvDescription.setText(mProductList.get(position).getDescription());
        v.setTag(mProductList.get(position).getId());
        return v;
    }
}
