package com.example.admin.tracer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_main_sub1;
import com.example.admin.tracer.Listener.SocketListener_main_sub2;


public class MainSub2Activity extends Fragment {

	SocketListener_main_sub2 socketListener;

	@Override
	public void onAttach(Context c){
		socketListener = new SocketListener_main_sub2();
		Log.d("msg" , "onAttach");
		super.onAttach(c);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		socketListener.setActivity(getActivity());
		SocketIO.getSocket().on( "alramResult", socketListener.getListener());
		SocketIO.getSocket().emit("getAlram" , true);

		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.activity_main_sub2, container, false);
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		Log.d("msg" , "onActivityView");
		super.onActivityCreated(savedInstanceState);
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

	public void onDestroyView(){
		Log.d("msg" , "onDestroy");
		SocketIO.getSocket().off( "alramResult", socketListener.getListener());
		super.onDestroyView();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
