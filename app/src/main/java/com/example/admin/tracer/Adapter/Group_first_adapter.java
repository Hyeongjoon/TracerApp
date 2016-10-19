package com.example.admin.tracer.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016-10-17.
 */
public class Group_first_adapter extends RecyclerView.Adapter<Group_first_adapter.ViewHolder> {

    private List<JSONArray> mList= new ArrayList<>();

    public Group_first_adapter(JSONArray jsonArray){
        for(int i =0 ; i<jsonArray.length() ; i++){
            try {
                mList.add(jsonArray.getJSONArray(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public ImageView[] imageViewArr = new ImageView[3];

        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.group_first_item);
            date = (TextView)itemView.findViewById(R.id.group_first_date);
            imageViewArr[0] = (ImageView)itemView.findViewById(R.id.group_first_image1);
            imageViewArr[1] = (ImageView)itemView.findViewById(R.id.group_first_image2);
            imageViewArr[2] = (ImageView)itemView.findViewById(R.id.group_first_image3);
        }

        public void setData(JSONArray jsonArray){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                date.setText(jsonObject.getString("d"));
                for(int i = 0 ; i<jsonArray.length() ; i++){
                    JSONObject tempObject = jsonArray.getJSONObject(i);
                    if(tempObject.getInt("image")==1) {
                        Glide.with(imageViewArr[i].getContext()).load(tempObject.getString("location")).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).into(imageViewArr[i]);
                    }
                }
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_first_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONArray target = mList.get(position);
        holder.setData(target);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
