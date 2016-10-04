package com.example.admin.tracer.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.tracer.GroupInfoActivity;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016-08-05.
 */
public class Main_sub1_list_adapter extends RecyclerView.Adapter <Main_sub1_list_adapter.ViewHolder>  {

    List<JSONObject> mList= new ArrayList<>();;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView groupImage;
        public TextView groupName;
        public TextView groupList;
        public TextView groupNum;
        public LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout)v.findViewById(R.id.main_sub1_item);
            groupImage = (ImageView)v.findViewById(R.id.main_sub1_imageView);
            groupName = (TextView)v.findViewById(R.id.main_sub1_group_name);
            groupList = (TextView)v.findViewById(R.id.main_sub1_member_list);
            groupNum = (TextView)v.findViewById(R.id.main_sub1_group_num);
        };

        public void setData(JSONObject group){
            try {
                groupNum.setText(group.getString("member_number"));
                groupName.setText(group.getString("name"));
                groupList.setText( group.getString("memberName"));
                if(!group.getString("file_location").equals(null)){
                    String imageURL = group.getString("file_location");
                    Glide.with(groupImage.getContext()).load(imageURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(groupImage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Main_sub1_list_adapter( JSONArray jsonArrayGroup ){
        for(int i =0 ; i<jsonArrayGroup.length() ; i++){
            try {
                mList.add(jsonArrayGroup.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sub1_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            final JSONObject group= mList.get(position);
            holder.setData(group);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(holder.linearLayout.getContext());
                ab.setTitle(holder.groupName.getText());
                ab.setItems(R.array.main_sub1_click, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0 : {

                                return;
                            }case 1 :{

                                return;
                            }case 2 : {
                                Context context = holder.linearLayout.getContext();
                                final AlertDialog.Builder wrongGroupName = new AlertDialog.Builder(context);
                                wrongGroupName.setTitle(R.string.sub1_group_wrong_create);
                                wrongGroupName.setPositiveButton(R.string.ok , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(R.string.sub1_group_create_title);
                                final EditText input = new EditText(context);
                                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                                input.setText(holder.groupName.getText());
                                input.setSelectAllOnFocus(true);
                                builder.setView(input);
                                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String groupName = input.getText().toString().trim();
                                        if (groupName.length() == 0) {
                                            wrongGroupName.show();
                                        } else {
                                            SocketIO.getSocket().emit("changeGroupName" , groupName , getItemId(position));
                                        }
                                    }
                                });
                                AlertDialog mDialog = builder.create();
                                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                                mDialog.show();
                                return;
                            }
                        }
                    }
                });
                ab.show();
            }
        });
    }



    @Override
    public long getItemId(int position) {
        if(mList.size()==0){
            return 0;
        } else{
            try {
                JSONObject targetJSON = mList.get(position);
                return targetJSON.getInt("gid");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mList.size()==0){
            return 0;
        } else {
            return  mList.size();
        }
    }

    public List getDataList(){
        return mList;
    }

    public List getDataOrder(){
        List temp = new ArrayList();
        for(int i =0 ; i<mList.size() ; i++){
            try {
                temp.add(mList.get(i).getString("gid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    public void addGroup(JSONObject jsonObject){
        mList.add(0 , jsonObject);
    }

    public JSONArray deleteGroup(int position){
        JSONArray temp = new JSONArray();
        for(int i = position ; i < mList.size() ; i++){
            try {
                temp.put(mList.get(i).getInt("gid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mList.remove(position);
        return temp;
    }
}
