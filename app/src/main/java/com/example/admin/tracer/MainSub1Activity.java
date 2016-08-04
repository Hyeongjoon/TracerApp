package com.example.admin.tracer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.admin.tracer.Helper.SocketIO;
import com.example.admin.tracer.Helper.SocketListener_main_sub1;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


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
		socketListener.setActivity(getActivity());
		SocketIO.getSocket().on( "GroupImageResult", socketListener.getListener());
		SocketIO.getSocket().emit("getGroupImage" , true);
		final RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.activity_main_sub1, container, false);
		Button testBtn = (Button) layout.findViewById(R.id.testBtn);
		testBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "첫번째 페이지입니다.", Toast.LENGTH_SHORT).show();
			}
		});
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
		super.onDestroyView();
	}

	@Override
	public void onDestroy(){
		SocketIO.getSocket().off( "GroupImageResult", socketListener.getListener());
		super.onDestroy();
	}
}
