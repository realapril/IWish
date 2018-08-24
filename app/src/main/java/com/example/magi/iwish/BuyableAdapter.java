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

public class BuyableAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<Buyable> listm;

    public BuyableAdapter(Context context, List<Buyable> mProductList) {
        mcontext=context;
        listm=new ArrayList<Buyable>();
        Resources res=context.getResources();
        String[] titles=res.getStringArray(R.array.titles2);
        String[] descriptions=res.getStringArray(R.array.descriptions2);
        int[] images={R.drawable.skill1, R.drawable.skill2};
        SharedPreferences skill;
        skill=context.getSharedPreferences("click",MODE_PRIVATE);
        String a1,a2;
        if(skill.getString("skill1","").equals("yes")){
            a1="구매완료";
        }else{
            a1=5000+"별";
        }
        if(skill.getString("skill2","").equals("yes")){
            a2 ="구매완료";
        }else{
            a2 =8000+"별";
        }
        String[] cost={a1,a2};
        for(int m=0; m<2;m++){
            listm.add(new Buyable(titles[m],descriptions[m],images[m], cost[m]) );
        }
    }

    @Override
    public int getCount() {        return listm.size();    }

    @Override
    public Object getItem(int position) {return listm.get(position);    }

    @Override
    public long getItemId(int position) {return position;    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.custommascot,parent,false);
        TextView title= (TextView) row.findViewById(R.id.txt_name);
        TextView descriptioin= (TextView) row.findViewById(R.id.txt_memo);
        TextView cost= (TextView) row.findViewById(R.id.txt_cost);
        ImageView image=(ImageView)row.findViewById(R.id.imageView);

        Buyable temp=listm.get(position);
        title.setText(temp.title);
        descriptioin.setText(temp.description);


        cost.setText(temp.cost);

//        cost.setText(String.valueOf(temp.cost)+"별");
        image.setImageResource(temp.image);
        return row;
    }
}
