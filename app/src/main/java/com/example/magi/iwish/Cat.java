package com.example.magi.iwish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.magi.iwish.R.drawable.m1;

public class Cat extends AppCompatActivity {
    private TextView mystar, hiddenstar;
    private SharedPreferences star, cat, skill,count;
    int s;
    private volatile boolean activityStopped = false;
    private volatile boolean Stopped = false;
    private Button newitem, ad, buyitem, hide, show;
    private ImageView tabby, cheeze, tuxedo, color3, sham, box, raccoon, corgi, rocket, rocket2;
    private RelativeLayout rlayout ,hideable, showable;
    private List<Mascot> mlist;
    private List<Buyable> blist;
    private ListView lvitem;
    private MascotAdapter mascotAdapter;
    private BuyableAdapter buyableAdapter;
    private MediaPlayer mainbgm,newbgm,walkingbgm, rocketbgm,corgibark;
    private Thread walk;
    private GestureDetectorCompat gestureObject;
    private  Animation zoomin;
    private ImageButton speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        mainbgm= MediaPlayer.create(Cat.this,R.raw.fullbg);
        newbgm= MediaPlayer.create(Cat.this,R.raw.yeah);
        walkingbgm= MediaPlayer.create(Cat.this,R.raw.popp);
        rocketbgm= MediaPlayer.create(Cat.this,R.raw.rocket1);
        corgibark= MediaPlayer.create(Cat.this,R.raw.corgi);


        mystar = (TextView) findViewById(R.id.totalstar);

        newitem=(Button)findViewById(R.id.newitem);
        ad=(Button)findViewById(R.id.advertise);
        buyitem=(Button)findViewById(R.id.btn_buyitem);
        hide=(Button)findViewById(R.id.btn_hide);
        show=(Button)findViewById(R.id.btn_show);
        hiddenstar = (TextView) findViewById(R.id.txt_hiddenstar);

        star=getSharedPreferences("Dsr", MODE_PRIVATE);
        skill=getSharedPreferences("skill", MODE_PRIVATE);
        count = getSharedPreferences("CountFile", Context.MODE_PRIVATE);

        mlist = new ArrayList<>();
        blist=new ArrayList<>();
        lvitem=(ListView)findViewById(R.id.seelist);

        tabby=(ImageView)findViewById(R.id.img_tabby);
        cheeze=(ImageView)findViewById(R.id.img_cheeze);
        tuxedo=(ImageView)findViewById(R.id.img_tuxedo);
        color3=(ImageView)findViewById(R.id.img_3color);
        sham=(ImageView)findViewById(R.id.img_sham);
        box=(ImageView)findViewById(R.id.img_box);
        raccoon=(ImageView)findViewById(R.id.img_raccoon);
        corgi=(ImageView)findViewById(R.id.img_corgi);
        rocket=(ImageView)findViewById(R.id.img_rocket);
        //    rocket2=(ImageView)findViewById(R.id.img_rocket2);
        rlayout = (RelativeLayout) findViewById(R.id.clickable);
        hideable=(RelativeLayout)findViewById(R.id.moveable);
        speaker=(ImageButton)findViewById(R.id.img_speaker);
        //    speaker.setImageResource(R.drawable.on);
        mainbgm.start();
        speaker.isClickable();


        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakercount();
            }
        });
        gestureObject=new GestureDetectorCompat(this, new LearnGesture());

        //  mainbgm.setLooping(true);

        showable=(RelativeLayout)findViewById(R.id.showable);
        showable.setVisibility(View.INVISIBLE);
        newitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtem();
            }
        });
//        ad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adver();
//            }
//        });
        mascotAdapter = new MascotAdapter(getApplicationContext(), mlist);
        lvitem.setAdapter(mascotAdapter);

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                cat=getSharedPreferences("cat",MODE_PRIVATE);
                if(position==0){
                    posi1();
                }else if(position==1){
                    posi2();
                }else if(position==2){
                    posi3();
                }else if(position==3){
                    posi4();
                }else if(position==4){
                    posi5();
                }else if(position==5){
                    posi6();
                }else if(position==6){
                    posi7();
                }else if(position==7){
                    posi8();
                }else if(position==8){
                    posi9();
                }
            }
        });


        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickcount();
            }
        });

        buyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buytem();
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hid();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rocketbgm.start();
                Animation rocketjump = AnimationUtils.loadAnimation(Cat.this, R.anim.rocket);
                rocket.startAnimation(rocketjump);
            }
        });

        raccoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
            }
        });
        tabby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { walkingbgm.start();
            }
        });
        sham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
            }
        });
        tuxedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
            }
        });
        corgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                corgibark.start();
                Animation tilt = AnimationUtils.loadAnimation(Cat.this, R.anim.tilt);
                corgi.startAnimation(tilt);
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
            }
        });
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
                Animation hide=AnimationUtils.loadAnimation(Cat.this, R.anim.fade_out);
                box.startAnimation(hide);
            }
        });
        cheeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkingbgm.start();
            }
        });



    }
    private int counter=0; private double cal;
    private void speakercount(){
        counter++;
        Log.e("카운터",Integer.toString(counter));
        checkcal();
    }

    private void checkcal(){
        cal=(counter)%2;
        if(cal==0){
            Log.e("카운터버튼 짝",cal+"");
            speaker.setImageResource(R.drawable.on);
            mainbgm.start();
            SharedPreferences.Editor edit = count.edit();
            edit.putString("count0", "yes");
            edit.commit();
        }
        else{
            Log.e("카운터버튼홀", cal+"");
            speaker.setImageResource(R.drawable.off);
            mainbgm.pause();
            SharedPreferences.Editor edit = count.edit();
            edit.putString("count0", "no");
            edit.commit();
        }
        Log.e("버튼저장스트링",count.getString("count0",""));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        super.onBackPressed();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            if(event2.getX()<event1.getX()){
                //오->왼 스와이핑

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
            }
            return true;
        }
    }

    private void hid(){
        hideable.setVisibility(View.INVISIBLE);
        lvitem.setVisibility(View.INVISIBLE);
        showable.setVisibility(View.VISIBLE);
        showable.getBackground().setAlpha(204);
        show.getBackground().setAlpha(204);
        hiddenstar.setText(String.valueOf(s)+" 별");
    }
    private void show(){
        hideable.setVisibility(View.VISIBLE);
        hideable.getBackground().setAlpha(204);
        lvitem.setVisibility(View.VISIBLE);
        showable.setVisibility(View.INVISIBLE);
    }
    private void clickcount(){
        skill=getSharedPreferences("click",MODE_PRIVATE);
        if(skill.getString("skill1","").equals("yes")&&skill.getString("skill2","").equals("yes")){
            s=s+6;}else if(skill.getString("skill1","").equals("yes")){
            s=s+2;
        }else if(skill.getString("skill2","").equals("yes")){
            s=s+3;
        }else{s++;}
        mystar.setText(String.valueOf(s)+" 별");
        hiddenstar.setText(String.valueOf(s)+" 별");
    }

    private void buytem(){
        mlist.clear(); blist.clear();
        buyableAdapter=new BuyableAdapter(getApplicationContext(), blist);
        lvitem.setAdapter(buyableAdapter);

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                skill=getSharedPreferences("click",MODE_PRIVATE);
                if(position==0){
                    pos1();
                }else if(position==1){
                    pos2();
                }
            }

        });
    }

    private void newtem(){
        mlist.clear(); blist.clear();
        mascotAdapter = new MascotAdapter(getApplicationContext(), mlist);
        lvitem.setAdapter(mascotAdapter);

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                cat=getSharedPreferences("cat",MODE_PRIVATE);
                if(position==0){
                    posi1();
                }else if(position==1){
                    posi2();
                }else if(position==2){
                    posi3();
                }else if(position==3){
                    posi4();
                }else if(position==4){
                    posi5();
                }else if(position==5){
                    posi6();
                }else if(position==6){
                    posi7();
                }else if(position==7){
                    posi8();
                }else if(position==8){
                    posi9();
                }
            }
        });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateThread();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        activityStopped = false;
        //  i=5;값지정 여기

        if(count.getString("count0","").equals("no")){
            speaker.setImageResource(R.drawable.off);
            if(mainbgm.isPlaying()){ mainbgm.pause();Log.e("저장스트링",count.getString("count0",""));}

        }else{ speaker.setImageResource(R.drawable.on);
            mainbgm.start();
        }
        s=star.getInt("star",s);
        Log.e("받아온아이",s+"");

        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (!activityStopped) {
                    try {
                        handler.sendMessage(handler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Throwable t) {
                    }
                }
            }
        });


        myThread.start();
        Stopped=false;
        cat=getSharedPreferences("cat",MODE_PRIVATE);
        if(cat.getString("cat1","").equals("yes")){
            tabby.setImageResource(m1);
            walk_tabby();
        }
        if(cat.getString("cat2","").equals("yes")){
            cheeze.setImageResource(R.drawable.m2);
            walk_cheeze();
        }
        if(cat.getString("cat3","").equals("yes")){
            color3.setImageResource(R.drawable.m3);
        }
        if(cat.getString("cat4","").equals("yes")){
            sham.setImageResource(R.drawable.m4);
            walk_sham();
        }
        if(cat.getString("cat5","").equals("yes")){
            box.setImageResource(R.drawable.m5);
            walk_box();
        }
        if(cat.getString("cat6","").equals("yes")){
            tuxedo.setImageResource(R.drawable.m6);
            walk_tuxedo();
        }
        if(cat.getString("cat7","").equals("yes")){
            rocket.setImageResource(R.drawable.m7);
            walk_rocket();
        }
        if(cat.getString("cat8","").equals("yes")){
            corgi.setImageResource(R.drawable.m8);
            walk_corgi();
        }
        if(cat.getString("cat9","").equals("yes")){
            raccoon.setImageResource(R.drawable.m9);
            walk_raccoon();
        }
    }


    private void updateThread() {
        s++;
        mystar.setText(String.valueOf(s)+" 별");
        hiddenstar.setText(String.valueOf(s)+" 별");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityStopped = true;
        Stopped=true;
        //star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor=star.edit();
        editor.putInt("star",s);
        editor.commit();
        mainbgm.pause();
    }

    private void pos1(){
        if (skill.getString("skill1", "").equals("yes")) {
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();

        }else{
            if (s >= 5000) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                SharedPreferences.Editor edit = skill.edit();
                                edit.putString("skill1", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - 5000);
                                editor.commit();
                                s = s - 5000;
                                mystar.setText(s + " 별");


                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void pos2(){

        if (skill.getString("skill2", "").equals("yes")) {
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            if (s >= 8000) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                SharedPreferences.Editor edit = skill.edit();
                                edit.putString("skill2", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - 8000);
                                editor.commit();
                                s = s - 8000;
                                mystar.setText(s + " 별");


                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

    private void posi1(){

        if(cat.getString("cat1","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=3000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat1", "yes");
                                edit.commit();
                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s -cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");

                                tabby.setImageResource(m1);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                tabby.startAnimation(zoomin);
                                walk_tabby();


                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

    private void posi2(){
        final int cost=3000;
        if(cat.getString("cat2","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat2", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");
                                cheeze.setImageResource(R.drawable.m2);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                cheeze.startAnimation(zoomin);
                                walk_cheeze();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi3(){

        if(cat.getString("cat3","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=3000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat3", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");
                                color3.setImageResource(R.drawable.m3);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                color3.startAnimation(zoomin);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi4(){

        if(cat.getString("cat4","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=3000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat4", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");

                                sham.setImageResource(R.drawable.m4);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                sham.startAnimation(zoomin);
                                walk_sham();
                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi5(){

        if(cat.getString("cat5","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=3000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat5", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");
                                box.setImageResource(R.drawable.m5);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                box.startAnimation(zoomin);
                                walk_box();
                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi6(){

        if(cat.getString("cat6","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=3000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat6", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");

                                tuxedo.setImageResource(R.drawable.m6);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                tuxedo.startAnimation(zoomin);
                                walk_tuxedo();
                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi7(){

        if(cat.getString("cat7","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=4000;
            if (s >= cost) { //이전 3000 여기부터 4000
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat7", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");
                                rocket.setImageResource(R.drawable.m7);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                rocket.startAnimation(zoomin);
                                walk_rocket();
                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    private void posi8(){

        if(cat.getString("cat8","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=4000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat8", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");

                                corgi.setImageResource(R.drawable.m8);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                corgi.startAnimation(zoomin);
                                walk_corgi();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void posi9(){

        if(cat.getString("cat9","").equals("yes")){
            AlertDialog.Builder build = new AlertDialog.Builder(Cat.this);
            build.setMessage("이미 구입했습니다");
            AlertDialog al = build.create();
            al.show();
        }else{
            final int cost=4000;
            if (s >= cost) {
                Log.e("i가 가격보다 큼 통과", s + "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("정말 구매하시겠습니까");
                builder.setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                newbgm.start();
                                SharedPreferences.Editor edit = cat.edit();
                                edit.putString("cat9", "yes");
                                edit.commit();

                                SharedPreferences.Editor editor = star.edit();

                                editor.putInt("star", s - cost);
                                editor.commit();
                                s = s - cost;
                                mystar.setText(s + " 별");
                                raccoon.setImageResource(R.drawable.m9);
                                zoomin = AnimationUtils.loadAnimation(Cat.this, R.anim.zoom_in);
                                raccoon.startAnimation(zoomin);

                                walk_raccoon();
                                Log.e("스트링저장여부", cat.getString("cat1", ""));
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alter=builder.create();
                alter.show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Cat.this);
                builder.setMessage("별이 부족합니다!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }
    private void walk_tuxedo() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(800);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){tuxedo.setImageResource(R.drawable.m6);}
                            else if(a ==1){tuxedo.setImageResource(R.drawable.m6_2);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
    private void walk_corgi() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(300);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){corgi.setImageResource(R.drawable.m8_2);}
                            else if(a ==1){corgi.setImageResource(R.drawable.m8);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
    private void walk_tabby() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped) {
                    try {
                        Thread.sleep(500);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {

                            if(a ==0){tabby.setImageResource(R.drawable.m1_2);}
                            else if(a ==1){tabby.setImageResource(m1);}
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
    private void walk_cheeze() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped) {
                    try {
                        Thread.sleep(500);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {

                            if(a ==0){cheeze.setImageResource(R.drawable.m2);}
                            else if(a ==1){cheeze.setImageResource(R.drawable.m2_2);}
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }
    private void walk_raccoon() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(800);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){raccoon.setImageResource(R.drawable.m9_2);}
                            else if(a ==1){raccoon.setImageResource(R.drawable.m9);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
    private void walk_sham() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(500);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){sham.setImageResource(R.drawable.m4);}
                            else if(a ==1){sham.setImageResource(R.drawable.m4_2);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
    private void walk_rocket() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(1000);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){rocket.setImageResource(R.drawable.m7_2);}
                            else if(a ==1){rocket.setImageResource(R.drawable.m7);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }private void walk_box() {
        final Handler handle = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                while(!Stopped)  {
                    try {
                        Thread.sleep(1100);
                        i++;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int a=i%2;
                    handle.post(new Runnable(){
                        public void run() {
                            if(a ==0){box.setImageResource(R.drawable.m5_2);}
                            else if(a ==1){box.setImageResource(R.drawable.m5);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
}
