package com.example.admin.tracer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
		SocketIO.getSocket().on( "GroupImageResult", socketListener.getListener());
		SocketIO.getSocket().on("addGroupResult" , socketListener.getAddListener());
		SocketIO.getSocket().emit("getGroupImage" , true);
	}

	@Override
	public void onResume(){

		super.onResume();
	}

	@Override
	public void onDestroyView(){
		super.onDestroyView();

	}

	@Override
	public void onStop(){
		super.onStop();
		SocketIO.getSocket().off( "GroupImageResult", socketListener.getListener());
		SocketIO.getSocket().off("addGroupResult" , socketListener.getAddListener());
		SocketIO.getSocket().off("getGroupImage");
		SocketIO.getSocket().off("addGroup");
		SocketIO.getSocket().off("changedList");
		SocketIO.getSocket().off("deleteGroup");
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public void onDetach(){
		super.onDetach();
	}

	public void makeGroup(String groupName){
		SocketIO.getSocket().emit("addGroup" , groupName);
	}

}
