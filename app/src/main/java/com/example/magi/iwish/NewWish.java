package com.example.magi.iwish;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewWish extends Activity {

    private EditText edt_name,edt_cost,edt_memo;
    private Button btn_save, btn_exit,btn_addimage;
    JSONObject jsonresponse;SharedPreferences prefs;
    private ImageView mPhotoImageView;
    private Uri mImageCaptureUri;
    private String RealUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish);

        edt_name=(EditText)findViewById(R.id.edit_name);
        edt_cost=(EditText)findViewById(R.id.edit_cost);
        edt_memo=(EditText)findViewById(R.id.edit_memo);

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
        mPhotoImageView = (ImageView) findViewById(R.id.image_view);
    }


    public void save() {
        Date date = Calendar.getInstance().getTime();
        Date currentTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);

        SimpleDateFormat count = new SimpleDateFormat("yyMMddHHmms");  //yyMMddHHmm
        SimpleDateFormat show=new SimpleDateFormat("MM월 dd일 yyyy");

        String counttime=count.format(date);
        String showtime=show.format(date);
        String name=edt_name.getText().toString();
        String cost=edt_cost.getText().toString();
        String memo=edt_memo.getText().toString();
        if(edt_name.getText().toString()!=""){
            if(mImageCaptureUri !=null){

                prefs = getSharedPreferences("DataFile", Context.MODE_PRIVATE);

                JSONObject Datapack=new JSONObject();
                try {
                    Datapack.put("id", counttime);
                    Datapack.put("name", name);
                    Datapack.put("cost", cost);
                    Datapack.put("date", showtime);
                    Datapack.put("memo", memo);
                    Datapack.put("img",mImageCaptureUri.toString());
                    Datapack.put("donedate","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String Total=Datapack.toString();
                Log.e("새로만든제이슨자료",Total);
                Log.e("제이슨자료의 쉐어프리퍼키값",counttime);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putString(counttime,Total);
                editor.commit();
            }else{String imgUri=null;
                prefs = getSharedPreferences("DataFile", Context.MODE_PRIVATE);

                JSONObject Datapack=new JSONObject();
                try {
                    Datapack.put("id", counttime);
                    Datapack.put("name", name);
                    Datapack.put("cost", cost);
                    Datapack.put("date", showtime);
                    Datapack.put("memo", memo);
                    Datapack.put("img",imgUri);
                    Datapack.put("donedate","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String Total=Datapack.toString();
                Log.e("새로만든제이슨자료",Total);
                Log.e("제이슨자료의 쉐어프리퍼키값",counttime);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putString(counttime,Total);
                editor.commit();}

            finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(NewWish.this);
            builder.setMessage("정보를 작성해주세요");
            builder.setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("뒤로가기");
            alert.show();
        }
    }
    @Override
    public void onBackPressed() {
        exit();
    }
    public void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewWish.this);
        builder.setMessage("이 창을 벗어나면 입력한 정보가 사라집니다");
        builder.setCancelable(false)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
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

    private static final int PICK_FROM_ALBUM = 1;

    public void addimage(){

        CharSequence colors[] = new CharSequence[] {"앨범선택", "사진촬영","검색", "취소" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정렬방식");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    doTakeAlbumAction();
                }
                else if(which==1){
                    doTakePhotoAction();
                }
                else if(which==2){
                    searchintent();
                }
                else if(which==3){
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }

    private void searchintent(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        String name=edt_name.getText().toString();
        Log.e("가져옴",name);
        if(name!=null){intent.putExtra(SearchManager.QUERY,name);}else{
            intent.putExtra(SearchManager.QUERY,"");
        }

        startActivity(intent);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void doTakePhotoAction(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void doTakeAlbumAction()
    {        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }
    //카메라로 찍은 이미지 저장;
//    String mCurrentPhotoPath;
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //File file = new File("/sdcard/Images/test_image.jpg");

        //카메라에서 찍은거
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //썸네일
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            // bitmap=  (Image) data.getExtras().get("data");
            mPhotoImageView.setImageBitmap(bitmap);
//            mImageCaptureUri=data.getData();
//            mPhotoImageView .setImageURI(mImageCaptureUri);Log.e("이미지uri",data.getDataString());

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
                mPhotoImageView.setImageURI(mImageCaptureUri);
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
        // if(savedInstanceState !=null)bitmap=savedInstanceState.getParcelable("image");
        mPhotoImageView.setImageURI(mImageCaptureUri);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the values you need from your textview into "outState"-object
        super.onSaveInstanceState(outState);
        mPhotoImageView.setImageURI(mImageCaptureUri);

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
        mPhotoImageView.setImageURI(mImageCaptureUri);
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
        mPhotoImageView.setImageURI(mImageCaptureUri);
    }
}
