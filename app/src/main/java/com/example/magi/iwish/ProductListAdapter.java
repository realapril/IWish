package com.example.magi.iwish;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MAGI on 2017-10-14.
 */

public class ProductListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Item> mProductList;

    public ProductListAdapter(Context mcontext, List<Item> mProductList) {
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
        ProductListAdapter.ViewHolder viewHolder;

        if(convertView==null){

            convertView=inflater.inflate(R.layout.customlistview,null);
            viewHolder=new ViewHolder();
            viewHolder.imgV=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.tvCost=(TextView)convertView.findViewById(R.id.txt_cost);
            viewHolder.tvMemo=(TextView)convertView.findViewById(R.id.txt_memo);
            viewHolder.tvDate=(TextView)convertView.findViewById(R.id.txt_date);
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.txt_name);
            viewHolder.imgV.setImageURI(Uri.parse(mProductList.get(position).getImgUri()));
            convertView.setTag(viewHolder);
        }else { viewHolder=(ProductListAdapter.ViewHolder) convertView.getTag();}

        viewHolder.tvName.setText(mProductList.get(position).getName());
        viewHolder.tvCost.setText(mProductList.get(position).getCost());
        viewHolder.tvMemo.setText(mProductList.get(position).getMemo());
        viewHolder.tvDate.setText(mProductList.get(position).getDate());
      //  String uri=mProductList.get(position).getImgUri();
        viewHolder.imgV.setImageURI(Uri.parse(mProductList.get(position).getImgUri()));


        //문제:겟리졸버는 activity에서만 사용가능
        //Bitmap bmp=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        return convertView;

//원형------------------------------------------------------------------------
//
//        View v=View.inflate(mcontext,R.layout.customlistview,null);
//        TextView tvName=(TextView)v.findViewById(R.id.txt_name);
//        TextView tvCost=(TextView)v.findViewById(R.id.txt_cost);
//        TextView tvMemo=(TextView)v.findViewById(R.id.txt_memo);
//        TextView tvDate=(TextView)v.findViewById(R.id.txt_date);
//        ImageView imgV=(ImageView)v.findViewById(R.id.imageView);
//
//        tvName.setText(mProductList.get(position).getName());
//        tvCost.setText(mProductList.get(position).getCost());
//        tvMemo.setText(mProductList.get(position).getMemo());
//        tvDate.setText(mProductList.get(position).getDate());


//        Uri imguri=Uri.parse(mProductList.get(position).getImgUri());
//        Log.e("유알아이전달",imguri.toString());

  //     imgV.setImageURI(imguri);
//
//        //item id를 태그걸어저장
//        v.setTag(mProductList.get(position).getId());
//        return v;
    }


    class ViewHolder {
        ImageView imgV;
        TextView tvName, tvCost, tvMemo, tvDate;


    }
}
