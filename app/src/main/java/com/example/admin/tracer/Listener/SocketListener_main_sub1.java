package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.example.admin.tracer.Adapter.Main_sub1_list_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;


import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016-07-28.
 */
public class SocketListener_main_sub1 {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Activity a;

    public void setActivity(Activity a){
        this.a = a;
    }

    private Emitter.Listener mainSub1Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONArray group = (JSONArray) args[0];

           a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView = (RecyclerView) a.findViewById(R.id.main_sub1_group_list);
                    mRecyclerView.setHasFixedSize(false);
                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(a);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    // specify an adapter (see also next example)
                    mAdapter = new Main_sub1_list_adapter(group);
                    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
                        int state;
                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                            // get the viewHolder's and target's positions in your adapter data, swap them
                            Collections.swap( ((Main_sub1_list_adapter)recyclerView.getAdapter()).getDataList() , viewHolder.getAdapterPosition(), target.getAdapterPosition());
                            // and notify the adapter that its dataset has changed
                            state = ItemTouchHelper.ACTION_STATE_DRAG;
                            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                            return true;
                        }

                        @Override
                        public void clearView(RecyclerView recyclerView , RecyclerView.ViewHolder viewHolder){
                            super.clearView(recyclerView , viewHolder);
                            if(state==ItemTouchHelper.ACTION_STATE_DRAG){
                                state = -1;
                                JSONArray data = new JSONArray();
                                List list = ((Main_sub1_list_adapter)recyclerView.getAdapter()).getDataOrder();
                                for(int i = 0 ; i < list.size() ; i++){
                                    data.put(list.get(i));
                                }
                                SocketIO.getSocket().emit("changedList" , data);
                            }
                        }

                        @Override
                        public boolean isItemViewSwipeEnabled() {
                            return true;
                        }

                        @Override
                        public boolean isLongPressDragEnabled() {
                            return true;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                            //mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        }

                        //defines the enabled move directions in each state (idle, swiping, dragging).
                        @Override
                        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);

                        }
                    };
                    ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
                    ith.attachToRecyclerView(mRecyclerView);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
        }
    };
    private Emitter.Listener mainSub1AddGroupListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    public Emitter.Listener getListener(){
        return mainSub1Listener;
    }
    public Emitter.Listener getAddListener(){ return mainSub1AddGroupListener; }
}
