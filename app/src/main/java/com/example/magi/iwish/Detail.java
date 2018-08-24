package com.example.magi.iwish;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Detail extends AppCompatActivity {
    private Button btn_cancel, btn_update, btn_share, btn_delete ;
    private TextView edit_name, edit_cost, edit_date, edit_memo,edit_completedate;
    private String JS_name, JS_cost,JS_date, JS_id, JS_memo,JS_imgUri,JS_completedate;
    private ImageView edit_img;
    private String count;
    private Animator mCurrentAnimator;   private int mShortAnimationDuration;
   private boolean pressed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_share=(Button)findViewById(R.id.btn_share);
        btn_delete=(Button)findViewById(R.id.btn_delete);

        edit_memo=(TextView) findViewById(R.id.edit_memo);
        edit_name=(TextView)findViewById(R.id.edit_name);
        edit_cost=(TextView)findViewById(R.id.edit_cost);
        edit_date=(TextView)findViewById(R.id.edit_date);
        edit_completedate=(TextView)findViewById(R.id.edit_completedate);
        edit_img=(ImageView)findViewById(R.id.Detailimage_view);
        edit_img.isClickable();

        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomforview);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomoutforview);
        btn_share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                share();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                update();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                delete();
            }
        });
        edit_img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(!pressed) {
                    v.startAnimation(zoomin);
                    pressed = !pressed;
                } else {
                    v.startAnimation(zoomout);
                    pressed = !pressed;
                }
            }
        });


        String prof;
        prof=getIntent().getStringExtra("DataofI");

        Log.e("디테일클래스:받아옴",prof);
        if(prof!=null){
            try {

                JSONObject jsobject = new JSONObject(getIntent().getStringExtra("DataofI"));
                JS_name   = jsobject.optString("name").toString();
                JS_cost   = jsobject.optString("cost").toString();
                JS_memo   = jsobject.optString("memo").toString();
                JS_date   = jsobject.optString("date").toString();
                JS_id     = jsobject.optString("id").toString();
                JS_imgUri =jsobject.optString("imageUri").toString();
                JS_completedate= jsobject.optString("donedate").toString();
                Log.e("날짜", JS_completedate);
                count=jsobject.optString("count").toString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            edit_name.setText(JS_name);
            edit_cost.setText(JS_cost);
            edit_date.setText(JS_date);
            edit_memo.setText(JS_memo);
            if(JS_completedate!=""){            edit_completedate.setText("구매일: "+JS_completedate);}

            Uri a=Uri.parse(JS_imgUri);

            edit_img.setImageURI(Uri.parse(JS_imgUri));

        }else{};

    }
    private void zoom(){
edit_img.setAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
    }

    private void delete(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("정말 삭제하시겠습니까");
        builder.setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if((Integer.parseInt(count)%2)==0){ SharedPreferences prefA=getSharedPreferences("DataFile", Context.MODE_PRIVATE);
                            prefA.edit().remove(JS_id).commit();
                        }else{SharedPreferences success = getSharedPreferences("completeDataFile", Context.MODE_PRIVATE);
                            success.edit().remove(JS_id).commit();}


                        finish();
                    }
                })
                .setNegativeButton("안돼요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("뒤로가기");
        alert.show();
    }

    private void share(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String fin=   "이름: "+JS_name +"\n"+"가격: "+JS_cost +"\n"+JS_memo;

        shareIntent.putExtra(Intent.EXTRA_TEXT, fin);shareIntent.setType("text/html");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(JS_imgUri));
        shareIntent.setType("image/jpeg");

        startActivity(Intent.createChooser(shareIntent, "공유할 앱을 고르세요"));
    }


    private void update(){
        Intent goEditP=new Intent(this, Editpage.class);
        JSONObject DatatoEditP=new JSONObject();
        try {
            DatatoEditP.put("id", JS_id);
            DatatoEditP.put("name", JS_name);
            DatatoEditP.put("cost", JS_cost);
            DatatoEditP.put("date", JS_date);
            DatatoEditP.put("memo", JS_memo);
            DatatoEditP.put("img",JS_imgUri);
            DatatoEditP.put("count",count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Total=DatatoEditP.toString();
        Log.e("수정페이지로보내는정보",Total);
        goEditP.putExtra("DataofD", Total);
        startActivity(goEditP);
        finish();
    }

    //여기부터 별세기
    private int s;
    private volatile boolean activityStopped = false;
    private SharedPreferences star;

    @Override
    protected void onResume() {
        super.onResume();
        star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);
        activityStopped = false;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                updateThread();
            }
        };

        if(s==1){
        }else{  s=star.getInt("star",s);}

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
    private void updateThread() {
        s++;
        //  myi.setText(String.valueOf(i)+" 별");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityStopped = true;
        star=getSharedPreferences("Dsr", Context.MODE_PRIVATE);;
        SharedPreferences.Editor edit=star.edit();
        edit.putInt("star",s);
        edit.commit();
        Log.e("저장별",s+"");
    }
}
