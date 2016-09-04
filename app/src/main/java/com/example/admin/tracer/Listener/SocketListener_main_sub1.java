package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
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

    private Handler mHandler = new Handler();

    public void setActivity(Activity a){
        this.a = a;
        adapter = new Main_sub1_list_adapter(a.getApplicationContext() , R.layout.main_sub1_item);
    }

    private Emitter.Listener mainSub1Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONArray group = (JSONArray) args[0];
            final int groupNum = (int)args[1];
            Log.d("msg" , groupNum+"");
            try{
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post( new Runnable() {
                            @Override
                            public void run() {
                                ListView listView_group = (ListView)a.findViewById(R.id.main_sub1_group_list);
                                adapter.setGroup(group , groupNum );
                                listView_group.setAdapter(adapter);
                                listView_group.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        Main_sub1_list_adapter.ViewHolder vh = (Main_sub1_list_adapter.ViewHolder) view.getTag();
                                        final int touchedX = (int) (vh.lastTouchedX + 0.5f);
                                        final int touchedY = (int) (vh.lastTouchedY + 0.5f);

                                        view.startDrag(null, new View.DragShadowBuilder(view) {

                                            @Override
                                            public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                                                super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                                                shadowTouchPoint.x = touchedX;
                                                shadowTouchPoint.y = touchedY;
                                            }

                                            @Override
                                            public void onDrawShadow(Canvas canvas) {
                                                super.onDrawShadow(canvas);
                                            }
                                        }, view, 0);

                                        view.setVisibility(View.INVISIBLE);
                                        return true;
                                    }
                                });

                                listView_group.setOnDragListener(new View.OnDragListener() {
                                    @Override
                                    public boolean onDrag(View v, DragEvent event) {
                                        switch (event.getAction()) {
                                            case DragEvent.ACTION_DRAG_STARTED :
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
                });
                t.start();
            } catch (Exception e) {
                System.out.println(e);
            }
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
