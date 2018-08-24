package com.example.magi.iwish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.example.magi.iwish.R.id.edit_cost;
import static com.example.magi.iwish.R.id.edit_memo;
import static com.example.magi.iwish.R.id.edit_name;
import static com.example.magi.iwish.R.id.image_viewinedit;

public class Editpage extends AppCompatActivity {
    private Button btn_save, btn_exit,btn_addimage;
    private EditText edt_name,edt_cost,edt_memo;
    private String JS_name, JS_cost,JS_date, JS_id, JS_memo,JS_image;
    private ImageView edt_img;
    private String targ;
    private int count;
    private SharedPreferences target;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpage);

        edt_name=(EditText)findViewById(edit_name);
        edt_cost=(EditText)findViewById(edit_cost);
        edt_memo=(EditText)findViewById(edit_memo);
        edt_img=(ImageView)findViewById(image_viewinedit);

        btn_save=(Button)findViewById(R.id.btn_save);
        btn_exit=(Button)findViewById(R.id.btn_exit);
        btn_addimage=(Button)findViewById(R.id.btn_addimage);

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }

        });
        btn_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                exit();
            }
        });
        btn_addimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addimage();
            }
        });
        String prof;
        prof=getIntent().getStringExtra("DataofD");

        Log.e("에디트페이지:받아옴",prof);

        if(prof!=null){
            try {

                JSONObject jsobject = new JSONObject(getIntent().getStringExtra("DataofD"));
                JS_name   = jsobject.optString("name").toString();
                JS_cost   = jsobject.optString("cost").toString();
                JS_memo   = jsobject.optString("memo").toString();
                JS_date   = jsobject.optString("date").toString();
                JS_id     = jsobject.optString("id").toString();
               String coun=jsobject.optString("count").toString(); Log.e("카운트스트링",coun);
                count= Integer.parseInt(coun);
                JS_image  = jsobject.optString("img").toString();
                String a=jsobject.getString("img").toString();
                String c=jsobject.getString("count").toString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            edt_name.setText(JS_name);
            edt_cost.setText(JS_cost);
            edt_memo.setText(JS_memo);
            //이미지삽입
            edt_img.setImageURI(Uri.parse(JS_image));

        }else{};
    }


    public void save() {
        if((count%2)==0){
            target=getSharedPreferences("DataFile", Context.MODE_PRIVATE);
            targ=target.getString(JS_id,"");

        }else{target = getSharedPreferences("completeDataFile", Context.MODE_PRIVATE);
            targ=target.getString(JS_id,"");}
      savedetail();


    }

    public void savedetail(){
        String name=edt_name.getText().toString();
        String cost=edt_cost.getText().toString();
        String memo=edt_memo.getText().toString();

        try {JSONObject Datapack=new JSONObject(targ);
            Datapack.put("id", JS_id);
            Datapack.put("name", name);
            Datapack.put("cost", cost);
            Datapack.put("date", JS_date);
            Datapack.put("memo", memo);
            if(Uri.parse(JS_image)!=null && mImageCaptureUri!=null){Datapack.put("img",mImageCaptureUri.toString());
            }else if(Uri.parse(JS_image)!=null){Datapack.put("img",JS_image.toString());
            }else if(mImageCaptureUri!=null){Datapack.put("img",mImageCaptureUri.toString());
            }else{}

            String Total=Datapack.toString();
            SharedPreferences.Editor editor=target.edit();
            editor.putString(JS_id,Total);
            editor.commit();
            Log.e("새로만든제이슨자료",Total);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        finish();
    }



    public void addimage(){

        String fileName = "YourImage.png";
        String completePath = Environment.getExternalStorageDirectory() + "/" + fileName;



        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        doTakePhotoAction();
                    }
                })
                .setNeutralButton("앨범선택",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        doTakeAlbumAction();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void doTakePhotoAction(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private static final int PICK_FROM_ALBUM = 1;

    private void doTakeAlbumAction()
    {        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, PICK_FROM_ALBUM);

    }
    private Image bitmap; private Uri mImageCaptureUri;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {   File file = new File("/sdcard/Images/test_image.jpg");

        //카메라에서 찍은거
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //썸네일
            Bundle extras = data.getExtras();
            //  bitmap = (Image) extras.get("data");
            mImageCaptureUri=data.getData();
            edt_img.setImageURI(mImageCaptureUri);Log.e("이미지uri",data.getDataString());

        }
        //앨범위해
        if(resultCode != RESULT_OK)
        {
            return;
        }

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                // bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);

//이미지 임시 유알아이를 인풋스트림에 저장
//                    InputStream is = getContentResolver().openInputStream(mImageCaptureUri);
//                    Bitmap bit = BitmapFactory.decodeStream(is);
//                    is.close();


//                RealUri=getRealPathFromUri(this, mImageCaptureUri);
//                Log.e("가공유알아이",RealUri);
                edt_img.setImageURI(mImageCaptureUri);
            }
        }
    }

    //이미지 받아오는 uri
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    //이미지유지 화면전환시
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textview
        if(savedInstanceState !=null)bitmap=savedInstanceState.getParcelable("bitmap");
        edt_img.setImageURI(mImageCaptureUri);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        super.onSaveInstanceState(outState);
        edt_img.setImageURI(mImageCaptureUri);

    }

    public void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("이 창을 벗어나면 입력한 정보가 사라집니다");
        builder.setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
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
