package com.example.admin.tracer.Adapter;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-05.
 */
public class Main_sub1_grid_adapter extends BaseAdapter{

    Context mContext;
    JSONArray jsonArrayGroup;
    JSONArray jsonArrayProfileURL;
    JSONObject groupNum;
    JSONArray groupInfo;
    int layout;
    LayoutInflater inf;

    public Main_sub1_grid_adapter(Context context , int layout){
        this.layout = layout;
        mContext = context;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(jsonArrayGroup==null){
            return 0;
        } else {
            try {
                return groupNum.getInt("groupNum");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public Object getItem(int position) {
        if(groupInfo==null){
            return null;
        } else {
            try {
                return groupInfo.getJSONObject(position);
            } catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        if(groupInfo == null){
            return 0;
        } else{
            JSONObject targetJSON = (JSONObject)getItem(position);
            try {
                return targetJSON.getInt("gid");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int[] tempUID = new int[4];
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        long temp = getItemId(position);
        try {
            int count = 0;
            for (int i = 0; i < jsonArrayGroup.length() ; i++) {
                if(temp == jsonArrayGroup.getJSONObject(i).getInt("gid")){
                    tempUID[count] = jsonArrayGroup.getJSONObject(i).getInt("uid");
                    ++count;
                }
            }
            GridLayout item_layout = (GridLayout)convertView.findViewById(R.id.main_sub1_item);
            TextView groupNum = (TextView)convertView.findViewById(R.id.main_sub1_group_num);
            TextView groupName = (TextView)convertView.findViewById(R.id.main_sub1_group_name);
            JSONObject target = (JSONObject)getItem(position);
            groupNum.setText(target.getString("number"));
            groupName.setText(target.getString("name"));      //이름설정부분

            ImageView[] IvArr = new ImageView[4];
            switch (count) {
                case 1 : {
                    int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180,  mContext.getResources().getDisplayMetrics());
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView4));
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView3));
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView2));

                    ImageView imageView1 = (ImageView)convertView.findViewById(R.id.main_sub1_imageView1);
                    ViewGroup.LayoutParams itemLayoutParams1 = imageView1.getLayoutParams();
                    itemLayoutParams1.height = value;
                    itemLayoutParams1.width = value;
                    imageView1.setLayoutParams(itemLayoutParams1);
                    IvArr[0] = imageView1;
                    break;
                } case 2 : {
                    int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180,  mContext.getResources().getDisplayMetrics());
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView4));
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView3));
                    ImageView imageView1 = (ImageView)convertView.findViewById(R.id.main_sub1_imageView1);
                    ImageView imageView2 = (ImageView)convertView.findViewById(R.id.main_sub1_imageView2);
                    ViewGroup.LayoutParams itemLayoutParams1 = imageView1.getLayoutParams();
                    ViewGroup.LayoutParams itemLayoutParams2 = imageView2.getLayoutParams();
                    itemLayoutParams1.height = value;
                    itemLayoutParams2.height = value;
                    imageView1.setLayoutParams(itemLayoutParams1);
                    imageView2.setLayoutParams(itemLayoutParams2);
                    IvArr[0] = imageView1;
                    IvArr[1] = imageView2;
                    break;
                } case 3 : {

                    ImageView imageView1 = (ImageView)convertView.findViewById(R.id.main_sub1_imageView1);
                    //ViewGroup.LayoutParams itemLayoutParams1 = imageView1.getLayoutParams();
                    GridLayout.LayoutParams itemLayoutParams = new GridLayout.LayoutParams(imageView1.getLayoutParams());
                    itemLayoutParams.rowSpec = GridLayout.spec(1,2);
                    itemLayoutParams.columnSpec = GridLayout.spec(0);
                    imageView1.setLayoutParams(itemLayoutParams);
                    item_layout.removeViewInLayout((ImageView)convertView.findViewById(R.id.main_sub1_imageView2));
                    IvArr[0] = imageView1;
                    IvArr[1] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView3);
                    IvArr[2] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView4);
                    break;
                } case 4 : {
                    IvArr[0] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView1);
                    IvArr[1] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView2);
                    IvArr[2] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView3);
                    IvArr[3] = (ImageView)convertView.findViewById(R.id.main_sub1_imageView4);
                    break;
                }
            };
            for (int i = 0 ; i < count ; i++ ) {
                for (int j = 0 ; j < jsonArrayProfileURL.length(); j++) {
                    if (tempUID[i] ==jsonArrayProfileURL.getJSONObject(j).getInt("uid")) {
                        String tempURL = jsonArrayProfileURL.getJSONObject(j).getString("profile");
                        Glide.with(mContext).load(tempURL).into(IvArr[i]);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return convertView;
    }

    public void setGroup(JSONArray jsonArrayGroup , JSONArray jsonArrayProfileURL , JSONObject groupNum, JSONArray groupInfo) {
        this.jsonArrayGroup = jsonArrayGroup;
        this.jsonArrayProfileURL = jsonArrayProfileURL;
        this.groupNum = groupNum;
        this.groupInfo = groupInfo;
    }

    public void addGroup(JSONObject jsonObject){
        this.jsonArrayGroup.put(jsonObject);
    }
}