package com.example.magi.iwish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MAGI on 2017-10-22.
 */

public class doneAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Item> mProductList;

    public doneAdapter(Context mcontext, List<Item> mProductList) {
        this.mcontext = mcontext;
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
        //뷰 재활용

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        doneAdapter.ViewHolder viewHolder;

        if(convertView==null){

            convertView=inflater.inflate(R.layout.donelistview,null);
            viewHolder=new ViewHolder();

            viewHolder.tvCost=(TextView)convertView.findViewById(R.id.textViewcost);
            viewHolder.tvDonedate=(TextView)convertView.findViewById(R.id.textViewdonedate);
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.textViewname);
            viewHolder.imgV=(ImageView)convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else { viewHolder=(doneAdapter.ViewHolder) convertView.getTag();}

        viewHolder.tvName.setText(mProductList.get(position).getName());
        viewHolder.tvCost.setText(mProductList.get(position).getCost());
        viewHolder.tvDonedate.setText("구매날짜"+mProductList.get(position).getDonedate());
        Log.e("구매날짜:",mProductList.get(position).getDonedate());
        viewHolder.imgV.setImageResource(R.drawable.check);
        //  String uri=mProductList.get(position).getImgUri();


        return convertView;

    }


    class ViewHolder {
        ImageView imgV;
        TextView tvName, tvCost, tvMemo, tvDonedate;


    }
}

