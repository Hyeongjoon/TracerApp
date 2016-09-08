package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;


import com.example.admin.tracer.Adapter.Main_sub1_list_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;


import org.json.JSONArray;

import org.json.JSONObject;

/**
 * Created by admin on 2016-07-28.
 */
public class SocketListener_main_sub1 {

    Main_sub1_list_adapter adapter;
    Activity a;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private Handler mHandler = new Handler();

    public void setActivity(Activity a){
        this.a = a;
        mLayoutManager = new LinearLayoutManager(a);
    }

    private Emitter.Listener mainSub1Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("msg" , "여기까진오냐");
            final JSONArray group = (JSONArray) args[0];
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView = (RecyclerView) a.findViewById(R.id.main_sub1_group_list);
                    mAdapter = new Main_sub1_list_adapter(group);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setOnDragListener(new View.OnDragListener() {
                        @Override
                        public boolean onDrag(View v, DragEvent event) {
                            switch (event.getAction()) {
                                case DragEvent.ACTION_DRAG_STARTED :
                                    ListView listView = (ListView)a.findViewById(R.id.main_sub1_group_list);
                                    //etc etc. do some stuff with the drag event
                                    break;
                                case DragEvent.ACTION_DRAG_LOCATION :
                                    //do something with the position (a scroll i.e);
                                    break;
                                case DragEvent.ACTION_DROP :{
                                    Point touchPosition = getTouchPositionFromDragEvent(v, event);
                                    Log.d("msg" , touchPosition.x+ " , " + touchPosition.y);
                                    View view = (View) event.getLocalState();
                                    view.setVisibility(View.VISIBLE);
                                    break;
                                } case DragEvent.ACTION_DRAG_EXITED:{
                                    break;
                                } case DragEvent.ACTION_DRAG_ENDED:{
                                    View view = (View) event.getLocalState();
                                    view.setVisibility(View.VISIBLE);
                                    break;
                                }
                                default:
                            }
                            return true;
                        }
                    });

                }
            });
        }
    };
    public static Point getTouchPositionFromDragEvent(View item, DragEvent event) {
        Rect rItem = new Rect();
        item.getGlobalVisibleRect(rItem);
        return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
    }

    public Emitter.Listener getListener(){
        return mainSub1Listener;
    }
}
