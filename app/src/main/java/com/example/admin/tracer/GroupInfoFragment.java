package com.example.admin.tracer;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.tracer.Helper.S3SecretKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by admin on 2016-11-05.
 */

public class GroupInfoFragment extends Fragment {

    private int pageNum;
    private int uid;
    private int writer;
    private String url;
    private String fileName;
    private String key;
    private int image;

    AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials(S3SecretKey.getAccesskey() , S3SecretKey.getSecretkey()));

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pageNum = getArguments().getInt("pageNum");
        uid = getArguments().getInt("uid");
        writer = getArguments().getInt("writer");
        url = getArguments().getString("URL");
        fileName = getArguments().getString("fileName");
        key = getArguments().getString("key");
        image = getArguments().getInt("image");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.group_info_item, container, false);
                Button downBtn = (Button)rootView.findViewById(R.id.group_info_item_download);

                downBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String dirPath;
                        String filePath;
                        if(image==1){
                            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + getString(R.string.download_path);
                        } else{
                            dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + getString(R.string.download_path);
                        }
                        File dir_tracer = new File(dirPath);
                        if(!dir_tracer.exists()){
                            dir_tracer.mkdir();
                        }
                        String[] temp = key.split("/");
                        filePath = dirPath + "/"+temp[2];
                        File file = new File(filePath);
                        TransferUtility transferUtility = new TransferUtility(s3Client, getContext());
                        TransferObserver observer = transferUtility.download(getString(R.string.bucket) , key , file);
                        observer.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                Log.d("msg" , "여긴오냐");
                                Log.d("msg" , state+"");
                                if(TransferState.COMPLETED == state){
                                    Toast.makeText(getContext() ,getString(R.string.success_download) ,Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                Toast.makeText(getContext() ,getString(R.string.fail_download) ,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                if(writer!=uid) {
                    View button = rootView.findViewById(R.id.group_info_item_delete);
                    ((LinearLayout)(button.getParent())).removeView(button);
                    }
                ImageView imageView = (ImageView) rootView.findViewById(R.id.group_info_item_imageview);
                if(image==1) {
                    Glide.with(getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                } else{
                    Glide.with(getContext()).load(R.drawable.file).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                }
                 return rootView;
     }

    public static GroupInfoFragment create (int position , JSONObject jsonObject , int uid){
        GroupInfoFragment fragment = new GroupInfoFragment();
        Bundle args = new Bundle();
        try {
            args.putString("URL" , jsonObject.getString("url"));
            args.putString("fileName" , jsonObject.getString("file_name"));
            args.putString("key" , jsonObject.getString("location"));
            args.putInt("pageNum" , position);
            args.putInt("writer" , jsonObject.getInt("uid"));
            args.putInt("uid" , uid);
            args.putInt("image" , jsonObject.getInt("image"));
            fragment.setArguments(args);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return fragment;
    }

}
