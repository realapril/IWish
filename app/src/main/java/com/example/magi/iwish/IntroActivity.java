package com.example.magi.iwish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class IntroActivity extends AppCompatActivity {


    private ImageView catview1,catview2, catview3, catview4, catview5, catview6, catview7, catview8, catview9 ,starview;
    private SharedPreferences cat;
    private volatile boolean Stopped = false;
    private TransitionDrawable transitiondrawable;

    ColorDrawable[] BackGroundColor = {
            new ColorDrawable(Color.parseColor("#36384c")),
            new ColorDrawable(Color.parseColor("#686492")),
    };
    ColorDrawable[]BackGroundColor2 = {
            new ColorDrawable(Color.parseColor("#364b4c")),
            new ColorDrawable(Color.parseColor("#63828b")),
    };
    ColorDrawable[]BackGroundColor3 = {
            new ColorDrawable(Color.parseColor("#905555")),
            new ColorDrawable(Color.parseColor("#e79a7f")),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this,
                        MainActivity.class);
                startActivity(intent);
//                Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
//                fade_out.start();
                finish();
              //  overridePendingTransition(R.anim.fadeout,R.anim.notmove);

            }
        }, 3000);

        catview1=(ImageView)findViewById(R.id.imgv_cat1);
        catview2=(ImageView)findViewById(R.id.imgv_cat2);
        catview3=(ImageView)findViewById(R.id.imgv_cat3);
        catview4=(ImageView)findViewById(R.id.imgv_cat4);
        catview5=(ImageView)findViewById(R.id.imgv_cat5);
        catview6=(ImageView)findViewById(R.id.imgv_cat6);
        catview7=(ImageView)findViewById(R.id.imgv_cat7);
        catview8=(ImageView)findViewById(R.id.imgv_cat8);
        catview9=(ImageView)findViewById(R.id.imgv_cat9);
        starview=(ImageView)findViewById(R.id.star);

        RelativeLayout layout=(RelativeLayout)findViewById(R.id.full_layout);
        Random r = new Random();
        int i1 = r.nextInt(4- 1) + 1;
        Log.e("랜덤",i1+"");
        if(i1==1){transitiondrawable = new TransitionDrawable(BackGroundColor);
        }else if(i1==2){transitiondrawable = new TransitionDrawable(BackGroundColor2);
        }else if(i1==3){transitiondrawable = new TransitionDrawable(BackGroundColor3);}

        layout.setBackground(transitiondrawable);
        transitiondrawable.startTransition(4000);


        Animation falldown = AnimationUtils.loadAnimation(IntroActivity.this, R.anim.fall_down);
        starview.startAnimation(falldown);

        cat=getSharedPreferences("cat",MODE_PRIVATE);

        if(cat.getString("cat1","").equals("yes")){
            walk_tabby();
        }
        if(cat.getString("cat2","").equals("yes")){
            walk_cheeze();
        }
        if(cat.getString("cat3","").equals("yes")){
            walk_3color();
        }
        if(cat.getString("cat4","").equals("yes")){
            walk_sham();
        }
        if(cat.getString("cat5","").equals("yes")){
            walk_box();
        }
        if(cat.getString("cat6","").equals("yes")){
            walk_tuxedo();
        }
        if(cat.getString("cat7","").equals("yes")){
            walk_rocket();
        }
        if(cat.getString("cat8","").equals("yes")){
            walk_corgi();
        }
        if(cat.getString("cat9","").equals("yes")){
            walk_raccoon();
        }

    }
    private void walk_3color() {
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
                            if(a ==0){catview3.setImageResource(R.drawable.m3_2);}
                            else if(a ==1){catview3.setImageResource(R.drawable.m3);}
                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
    private void walk_box() {
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
                            if(a ==0){catview5.setImageResource(R.drawable.m5_2);}
                            else if(a ==1){catview5.setImageResource(R.drawable.m5);}
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
                            if(a ==0){catview7.setImageResource(R.drawable.m7_2);}
                            else if(a ==1){catview7.setImageResource(R.drawable.m7);}
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
                            if(a ==0){catview4.setImageResource(R.drawable.m4);}
                            else if(a ==1){catview4.setImageResource(R.drawable.m4_2);}
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
                            if(a ==0){catview9.setImageResource(R.drawable.m9_2);}
                            else if(a ==1){catview9.setImageResource(R.drawable.m9);}

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

                            if(a ==0){catview2.setImageResource(R.drawable.m2);}
                            else if(a ==1){catview2.setImageResource(R.drawable.m2_2);}
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
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
                            if(a ==0){catview6.setImageResource(R.drawable.m6);}
                            else if(a ==1){catview6.setImageResource(R.drawable.m6_2);}

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

                            if(a ==0){catview1.setImageResource(R.drawable.m1_2);}
                            else if(a ==1){catview1.setImageResource(R.drawable.m1);}
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
                            if(a ==0){catview8.setImageResource(R.drawable.m8_2);}
                            else if(a ==1){catview8.setImageResource(R.drawable.m8);}

                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }

    @Override
    protected void onPause() {
        Stopped = true;
        super.onPause();
    }
}
