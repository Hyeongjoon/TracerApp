package com.example.admin.tracer;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.tracer.Adapter.Main_sub1_list_adapter;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_main_sub1;


public class MainSub1Activity extends Fragment {
	SocketListener_main_sub1 socketListener;

	@Override
	public void onAttach(Context c){
		socketListener = new SocketListener_main_sub1();
		Log.d("msg" , "onAttach");
		super.onAttach(c);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("msg" , "onCreate");
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main_sub1, container, false);
		SocketIO.getSocket().on( "GroupImageResult", socketListener.getListener());
		SocketIO.getSocket().emit("getGroupImage" , true);
		//LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
//		mRecyclerView.setHasFixedSize(true);
		//mRecyclerView.setLayoutManager(layoutManager);



		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		Log.d("msg" , "onActivityView");
		super.onActivityCreated(savedInstanceState);
		socketListener.setActivity(getActivity());
	}

	@Override
	public void onStart(){
		Log.d("msg" , "onStart");
		super.onStart();

	}

	@Override
	public void onResume(){
		Log.d("msg" , "onResume");
		super.onResume();
	}

	@Override
	public void onDestroyView(){
		Log.d("msg" , "onDestroy");
		SocketIO.getSocket().off( "GroupImageResult", socketListener.getListener());
		super.onDestroyView();
	}

	@Override
	public void onStop(){
		super.onStop();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();

	}
}
