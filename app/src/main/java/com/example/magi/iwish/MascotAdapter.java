package com.example.magi.iwish;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MAGI on 2017-10-24.
 */

public class MascotAdapter extends BaseAdapter {
    private Context mcontext;
    //private List<Mascot> mProductList;
    private ArrayList<Mascot> listm;

    public MascotAdapter(Context context, List<Mascot> mProductList) {
        mcontext=context;
        listm=new ArrayList<Mascot>();
        Resources res=context.getResources();

        String[] titles=res.getStringArray(R.array.titles);
        String[] descriptions=res.getStringArray(R.array.descriptions);
        int[] images={R.drawable.m1 ,R.drawable.m2,R.drawable.m3,R.drawable.m4,R.drawable.m5,R.drawable.m6,R.drawable.m7,R.drawable.m8,R.drawable.m9};

        SharedPreferences cat;
        cat=context.getSharedPreferences("cat",MODE_PRIVATE);

        String a1,a2,a3,a4,a5,a6,a7,a8,a9;
        if(cat.getString("cat1","").equals("yes")){
            a1="구매완료";
        }else{
            a1=3000+"별";
        }
        if(cat.getString("cat2","").equals("yes")){
            a2 ="구매완료";
        }else{
            a2 =3000+"별";
        }
        if(cat.getString("cat3","").equals("yes")){
            a3 ="구매완료";
        }else{
            a3 =3000+"별";
        }
        if(cat.getString("cat4","").equals("yes")){
            a4 ="구매완료";
        }else{
            a4 =3000+"별";
        }
        if(cat.getString("cat5","").equals("yes")){
            a5 ="구매완료";
        }else{
            a5 =3000+"별";
        }
        if(cat.getString("cat6","").equals("yes")){
            a6 ="구매완료";
        }else{
            a6 =3000+"별";
        }
        if(cat.getString("cat7","").equals("yes")){
            a7 ="구매완료";
        }else{
            a7 =4000+"별";
        }
        if(cat.getString("cat8","").equals("yes")){
            a8 ="구매완료";
        }else{
            a8 =4000+"별";
        }
        if(cat.getString("cat9","").equals("yes")){
            a9 ="구매완료";
        }else{
            a9 =4000+"별";
        }

        String[] cost={a1,a2,a3,a4,a5,a6,a7,a8,a9};


        for(int m=0; m<9;m++){
            listm.add(new Mascot(titles[m],descriptions[m],images[m], cost[m]) );
        }

    }

    @Override
    public int getCount() {
        return listm.size();
    }

    @Override
    public Object getItem(int position) {
        return listm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MascotAdapter.ViewHolder viewHolder;

        View row=inflater.inflate(R.layout.custommascot,parent,false);
        TextView title= (TextView) row.findViewById(R.id.txt_name);
        TextView descriptioin= (TextView) row.findViewById(R.id.txt_memo);
        TextView cost= (TextView) row.findViewById(R.id.txt_cost);
        ImageView image=(ImageView)row.findViewById(R.id.imageView);

        Mascot temp=listm.get(position);
        title.setText(temp.title);
        descriptioin.setText(temp.description);


        cost.setText(temp.cost);

//        cost.setText(String.valueOf(temp.cost)+"별");
        image.setImageResource(temp.image);
        return row;
    }

    public class ViewHolder {
        ImageView imgV;
        TextView txtname, txtcost,txtmemo;
    }
}
