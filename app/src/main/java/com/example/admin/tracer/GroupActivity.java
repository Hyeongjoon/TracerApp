package com.example.admin.tracer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.admin.tracer.Helper.S3SecretKey;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_group;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by admin on 2016-10-06.
 */

public class GroupActivity extends AppCompatActivity {

    SocketListener_group socketListener = new SocketListener_group();
    final int TAKE_CAMERA = 0;
    final int PICK_FROM_ALBUM = 1;
    final int CROP_FROM_IMAGE = 2;
    private int gid;
    private String fileName;
    AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials(S3SecretKey.getAccesskey() , S3SecretKey.getSecretkey()));
    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        s3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.AP_NORTHEAST_2));
        Intent intent =getIntent();
        gid = intent.getIntExtra("gid" , 0);
        SocketIO.getSocket().emit("getGrInfo" , gid);
    }

    @Override
    protected void onStart(){
        super.onStart();
        socketListener.setActivity(this);
        SocketIO.getSocket().on("getGroupFile" , socketListener.getGroupFileListener());
        SocketIO.getSocket().on("addFileResult" , socketListener.getAddFileResultListener());
    }

    @Override
    protected void onStop() {
        super.onStop();
        SocketIO.getSocket().off("getGroupFile" , socketListener.getGroupFileListener());
        SocketIO.getSocket().off("addFileResult" , socketListener.getAddFileResultListener());
    }

    public void groupFirstOnclick(View v){
        switch (v.getId()) {
                case R.id.group_file_upload : {

                    return;
                } case R.id.group_camera : {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.group_first_camera);
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileName = input.getText().toString().trim();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String url = String.valueOf(System.currentTimeMillis())+".jpg";
                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() , url));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT , mImageCaptureUri);
                        startActivityForResult(intent , TAKE_CAMERA);
                    }});
                builder.show();
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode , resultCode , data);
            if(resultCode == RESULT_OK){
               if(requestCode == TAKE_CAMERA){
                   Intent intent = new Intent("com.android.camera.action.CROP");
                   intent.setDataAndType(mImageCaptureUri , "image/*");

                   //CROP IMAGE 설정
                   intent.putExtra("outputX" , 200);
                   intent.putExtra("outputY" , 200);
                   intent.putExtra("aspectX" , 1);
                   intent.putExtra("aspectY" , 1);
                   intent.putExtra("scale" , true);
                   intent.putExtra("return-data" , true);
                   startActivityForResult(intent, CROP_FROM_IMAGE);

               } else if(requestCode== PICK_FROM_ALBUM ){

                    //갤러리에서 불러오는거 다시 크롭 인텐드로 보낼것
                } else if(requestCode == CROP_FROM_IMAGE){
                    final Bundle extras = data.getExtras();
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tracer/" + System.currentTimeMillis()+".jpg";

                   if(extras != null){
                       Bitmap photo = extras.getParcelable("data");
                       storeCropImage(photo , filePath);
                   }
                    File f = new File(mImageCaptureUri.getPath());
                   if(f.exists()){
                       f.delete();
                   }
               }
            }
    }

    private void storeCropImage(Bitmap bitmap , String filePath){

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tracer";
        File dir_tracer = new File(dirPath);

        if(!dir_tracer.exists()){
            dir_tracer.mkdir();
        }

        File tempFile = new File(filePath);
        BufferedOutputStream bos;

        try{
            tempFile.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(tempFile)));
            final String tempPath = filePath;

            bos.flush();
            bos.close();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Date d = new Date(System.currentTimeMillis());
                    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    df.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String fileKey = "file/" + gid +"/"+df.format(d) +fileName+".jpg";
                    PutObjectRequest por = new PutObjectRequest( getString(R.string.bucket), fileKey , new File(tempPath));
                    s3Client.putObject(por);
                    fileName = null;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("location", fileKey);
                        jsonObject.put("gid" , gid);
                        jsonObject.put("image" , true);
                        SocketIO.getSocket().emit("addFile", jsonObject);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
