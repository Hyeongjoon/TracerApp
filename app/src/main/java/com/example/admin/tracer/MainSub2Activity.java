package com.example.admin.tracer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainSub2Activity extends Fragment {


	@Override
	public void onAttach(Context c){
		super.onAttach(c);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.activity_main_sub2, container, false);
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart(){

		super.onStart();
	}

	@Override
	public void onResume(){

		super.onResume();
	}

	public void onDestroyView(){
		super.onDestroyView();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

}
