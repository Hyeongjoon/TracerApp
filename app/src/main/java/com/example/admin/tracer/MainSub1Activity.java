package com.example.admin.tracer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_main_sub1;


public class MainSub1Activity extends Fragment {
	SocketListener_main_sub1 socketListener;

	@Override
	public void onAttach(Context c){

		socketListener = new SocketListener_main_sub1();
		super.onAttach(c);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main_sub1, container, false);
		SocketIO.getSocket().on( "GroupImageResult", socketListener.getListener());
		SocketIO.getSocket().emit("getGroupImage" , true);
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		socketListener.setActivity(getActivity());
	}

	@Override
	public void onStart(){
		super.onStart();
	}

	@Override
	public void onResume(){

		super.onResume();
	}

	@Override
	public void onDestroyView(){
		super.onDestroyView();
		SocketIO.getSocket().off( "GroupImageResult", socketListener.getListener());
	}

	@Override
	public void onStop(){

		super.onStop();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public void onDetach(){
		super.onDetach();
		socketListener=null;
	}
}
