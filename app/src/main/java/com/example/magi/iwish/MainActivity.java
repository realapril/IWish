package com.example.magi.iwish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
//extends activity??

    private Button btn_new, btn_search, btn_lineup, btn_countwish,btn_main;
    private String JS_name, JS_cost,JS_date, JS_id, JS_memo,JS_imgUri,JS_donedate;
    private ListView  lvitem;
    private List<Item> mitemlist,newitemlist;
    private ProductListAdapter wisadapt;
    private doneAdapter donadapt;
    private int counter=0; private double cal;
    private SharedPreferences savedWish,completewish ,scroll, star;
    private  Comparator<Item> noDesc;
    private int pos, i;
    volatile boolean activityStopped = false;
    private Animation slide;
    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_new = (Button) findViewById(R.id.btn_new);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_lineup = (Button) findViewById(R.id.btn_lineup);
        btn_countwish = (Button) findViewById(R.id.btn_countwish);
        btn_main=(Button)findViewById(R.id.Btn_Main);


        lvitem = (ListView) findViewById(R.id.listview);
        mitemlist = new ArrayList<>();

        savedWish = getSharedPreferences("DataFile", Context.MODE_PRIVATE);
        completewish = getSharedPreferences("completeDataFile", Context.MODE_PRIVATE);
        star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);

        slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown);
        lvitem.startAnimation(slide);

        gestureObject=new GestureDetectorCompat(this, new LearnGesture());
        //리스트뷰눌럿을때

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent godetail = new Intent(MainActivity.this, Detail.class);
                newitemlist=new ArrayList<Item>();

                JSONObject DatatoDetail = new JSONObject();
                String name = mitemlist.get(position).getName();
                String cost = mitemlist.get(position).getCost();
                String date = mitemlist.get(position).getDate();
                String ID = mitemlist.get(position).getId();
                String Memo = mitemlist.get(position).getMemo();
                String imgUri=mitemlist.get(position).getImgUri();
                String coun=String.valueOf(counter);
                try {
                    DatatoDetail.put("id", ID);
                    DatatoDetail.put("name", name);
                    DatatoDetail.put("cost", cost);
                    DatatoDetail.put("date", date);
                    DatatoDetail.put("memo", Memo);
                    DatatoDetail.put("imageUri",imgUri);
                    DatatoDetail.put("count",coun);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String Total = DatatoDetail.toString();
                //   Log.e("디테일로보내는정보", Total);
                godetail.putExtra("DataofI", Total);

                pos = lvitem.getFirstVisiblePosition();
                startActivity(godetail);
            }
        });
//리스트뷰 롱클릭
        float mDownX;
        int mSwipeSlop = -1;
        lvitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, final View arg1, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("위시 관리");

                if(cal==0){
//                    Log.e("카운터 홀",cal+"");
                    CharSequence bought[] = new CharSequence[] {"위시 구입 완료!"};
                    builder.setItems(bought, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date da = Calendar.getInstance().getTime();
                            Date currentTime = new Date();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(currentTime);SimpleDateFormat show=new SimpleDateFormat("MM월 dd일 yyyy"); String showtime=show.format(da);
                            if(which==0){
                                JSONObject Datapack = new JSONObject();
                                String name = mitemlist.get(position).getName();
                                String cost = mitemlist.get(position).getCost();
                                String date = mitemlist.get(position).getDate();
                                String ID = mitemlist.get(position).getId();
                                String Memo = mitemlist.get(position).getMemo();
                                String imgUri=mitemlist.get(position).getImgUri();
                                try {
                                    Datapack.put("id", ID);
                                    Datapack.put("name", name);
                                    Datapack.put("cost", cost);
                                    Datapack.put("date", date);
                                    Datapack.put("memo", Memo);
                                    Datapack.put("imageUri",imgUri);
                                    Datapack.put("donedate", showtime);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String Total = Datapack.toString();
                                Log.e("구매완료로 보내는정보", Total);
                                SharedPreferences.Editor editor=completewish.edit();
                                editor.putString(ID,Total);
                                editor.commit();

                                savedWish.edit().remove(ID).commit();
                                removeListItem(arg1,position);
                                wisadapt.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.show();
                }
                else{
                    //            Log.e("카운터짝", cal+"");
                    CharSequence cancel[] = new CharSequence[] {"위시 구입 취소"};
                    builder.setItems(cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0){
                                JSONObject Datapack = new JSONObject();
                                String name = mitemlist.get(position).getName();
                                String cost = mitemlist.get(position).getCost();
                                String date = mitemlist.get(position).getDate();
                                String ID = mitemlist.get(position).getId();
                                String Memo = mitemlist.get(position).getMemo();
                                String imgUri=mitemlist.get(position).getImgUri();
                                String donedate=mitemlist.get(position).getDonedate();
                                try {
                                    Datapack.put("id", ID);
                                    Datapack.put("name", name);
                                    Datapack.put("cost", cost);
                                    Datapack.put("date", date);
                                    Datapack.put("memo", Memo);
                                    Datapack.put("imageUri",imgUri);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String Total = Datapack.toString();
                                //  Log.e("구매완료취소시 보내는정보", Total);
                                SharedPreferences.Editor editor=savedWish.edit();
                                editor.putString(ID,Total);
                                editor.commit();

                                completewish.edit().remove(ID).commit();
                                mitemlist.remove(position);
                                donadapt.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });

//카테고리전환
        btn_countwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                Log.e("카운터",Integer.toString(counter));
                cal=(counter)%2;
                if(cal==0){
                    Log.e("카운터버튼 짝",cal+"");
                    getsavewish();
                }
                else{
                    Log.e("카운터버튼홀", cal+"");
                    getdonewish();
                }
            }
        }
        );

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
//메인 누르면 맨위로
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvitem.setSelectionAfterHeaderView();
            }
        });

        btn_lineup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CharSequence colors[] = new CharSequence[] {"최근 위시부터", "예전 위시부터","이름 순서", "이름 역순", "낮은 가격순","높은 가격순" };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("정렬방식");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item2.getId().compareTo(item1.getId());
                                }
                            };
                        }
                        else if(which==1){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item1.getId().compareTo(item2.getId());
                                }
                            };
                        }
                        else if(which==2){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item1.getName().compareTo(item2.getName());
                                }
                            };
                        }
                        else if(which==3){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item2.getName().compareTo(item1.getName());
                                }
                            };
                        }
                        else if(which==4){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item1.getCost().compareTo(item2.getCost());
                                }
                            };
                        }
                        else if(which==5){
                            noDesc = new Comparator<Item>() {
                                @Override
                                public int compare(Item item1, Item item2) {
                                    return item2.getCost().compareTo(item1.getCost());
                                }
                            };
                        }
                        Collections.sort(mitemlist, noDesc);
                        wisadapt.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

    }
    //

    protected void removeListItem(View rowView, final int position) {
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_cat_right);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mitemlist.remove(position);
                wisadapt.notifyDataSetChanged();
//                animation.cancel();//이건왜잇지
            }
        },1000);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            if(event2.getX()>event1.getX()){
                //왼->오 스와이핑
                Intent intent=new Intent(MainActivity.this,Cat.class);
                //finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);

            }else if(event2.getX()<event1.getX()){
                //오->왼 스와이핑
                Intent intent=new Intent(MainActivity.this,NewWish.class);
                //finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("OnResume","메인액티비티켜짐");
        if(mitemlist!=null){
            //       Log.e("onResume counter",Integer.toString(counter));
            cal=(counter)%2;
            if(cal==0){
                Log.e("카운터 짝",cal+"");
                getsavewish();
            }
            else{
                Log.e("카운터홀", cal+"");
                getdonewish();
            }            }else{

        }

        scroll = getSharedPreferences("scrollFile", 0);

        pos=scroll.getInt("sc",0);

        lvitem.setSelection(pos);
        Log.e("메인 온resume",pos+"");

        activityStopped = false;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateThread();
            }
        };

        if(i==1){
        }else{  i=star.getInt("star",i);}

        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (!activityStopped) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("메인","온pause들어감");
        pos = lvitem.getFirstVisiblePosition();
        //전환시 포스는잡히는데 이게 onresume으로 값이안감;
        scroll = getSharedPreferences("scrollFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=scroll.edit();
        editor.putInt("sc",pos);
        editor.commit();

        activityStopped = true;
        star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);;
        SharedPreferences.Editor edit=star.edit();
        edit.putInt("star",i);
        edit.commit();
        Log.e("저장별",i+"");
    }

    @Override
    protected void onStop() {
        super.onStop();
        star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);;
        SharedPreferences.Editor edit=star.edit();
        edit.putInt("star",i);
        edit.commit();
        Log.e("온스탑저장별",i+"");
    }

    public void moveToNewWish(){
        Intent goNewwish=new Intent(this, NewWish.class);
        startActivityForResult(goNewwish,1);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public void getsavewish(){

        mitemlist.clear();btn_countwish.setText("현재 위시 0개");
        //키자마자 저장된 쉐어드 데이터 가져옴

        Map<String, ?> allEntries = savedWish.getAll();
        Set<String> keys = allEntries.keySet();
        for (String key : keys) {
            //      Log.e("저장된벨류", key);
        }

        wisadapt = new ProductListAdapter(getApplicationContext(), mitemlist);
        lvitem.setAdapter(wisadapt);
        lvitem.setAnimation(slide);

//쉐어드p
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //         Log.e("저장된모든쉐어드p", entry.getKey() + ": " + entry.getValue().toString());
            String a = entry.getKey().toString();
            String b = (savedWish.getString(a, ""));
//            Log.e("키목록", a);
            Log.e("저장된 위시 스트링목록", b);
            try {
                JSONObject jsobject = new JSONObject(b);

                JS_name = jsobject.optString("name").toString();
                JS_cost = jsobject.optString("cost").toString();
                JS_memo = jsobject.optString("memo").toString();
                JS_date = jsobject.optString("date").toString();
                JS_id = jsobject.optString("id").toString();
                JS_imgUri=jsobject.optString("img").toString();
                Item tmp = new Item(JS_name, JS_cost, JS_memo, JS_date, JS_id,JS_imgUri,JS_donedate);

                HashMap<String, Item> data = new HashMap<String, Item>();
                data.put(a, tmp);
                Item go=data.get(a);
                if(go!=null){
                    mitemlist.add(go);
                    Comparator<Item> noDesc = new Comparator<Item>() {
                        @Override
                        public int compare(Item item1, Item item2) {
                            //역순
                            return item2.getId().compareTo(item1.getId());
                        }
                    };
                    Collections.sort(mitemlist, noDesc);

                    if(mitemlist!=null){
                        btn_countwish.setText("현재 위시 "+mitemlist.size()+"개");
                    }else if(mitemlist.size()==0){ btn_countwish.setText("현재 위시 0개");}
                    wisadapt.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void  getdonewish(){
        mitemlist.clear();
        btn_countwish.setText("완료 위시 0개");



        Map<String, ?> doneEntries = completewish.getAll();
        Set<String> allkeys = doneEntries.keySet();
        for (String key : allkeys) {
            //      Log.e("저장된다른sP", key);
        }

        for (Map.Entry<String, ?> entry : doneEntries.entrySet()) {
            Log.e("저장된모든쉐어드p", entry.getKey() + ": " + entry.getValue().toString());
            String a = entry.getKey().toString();
            String b = (completewish.getString(a, ""));
            //     Log.e("새키목록", a);
            Log.e("완료위시 목록", b);
            try {
                JSONObject jsobject = new JSONObject(b);

                JS_name = jsobject.optString("name").toString();
                JS_cost = jsobject.optString("cost").toString();
                JS_memo = jsobject.optString("memo").toString();
                JS_date = jsobject.optString("date").toString();
                JS_id = jsobject.optString("id").toString();
                JS_imgUri=jsobject.optString("img").toString();
                JS_donedate=jsobject.optString("donedate").toString();
                Item tmp = new Item(JS_name, JS_cost, JS_memo, JS_date, JS_id,JS_imgUri,JS_donedate);
                HashMap<String, Item> data = new HashMap<String, Item>();
                data.put(a, tmp);
                Item go=data.get(a);


                //  if(go!=null){
                mitemlist.add(go);

//키자마자 정렬
                Comparator<Item> noDesc = new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        //역순
                        return item2.getId().compareTo(item1.getId());
                    }
                };
                Collections.sort(mitemlist, noDesc);
                wisadapt.notifyDataSetChanged();
                //   }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(mitemlist!=null){
            btn_countwish.setText("완료 위시 "+mitemlist.size()+"개");
        }else{ btn_countwish.setText("완료 위시 0개");}
        wisadapt.notifyDataSetChanged();

        donadapt = new doneAdapter(getApplicationContext(), mitemlist);
        lvitem.setAdapter(donadapt);
        lvitem.setAnimation(slide);

    }

    private void go(){
        Intent intent=new Intent(this, Cat.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }
    private void updateThread() {
        i++;
        //  myi.setText(String.valueOf(i)+" 별");
    }

}