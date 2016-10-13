package com.example.admin.tracer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.admin.tracer.Helper.GroupNameHelper;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_main_sub2;


public class MainSub2Activity extends Fragment {
	SocketListener_main_sub2 socketListener;
	private ProgressDialog pDialog;
	private final int TIME_OUT = 1001; //pDialog시간 설정

	@Override
	public void onAttach(Context c){
		socketListener = new SocketListener_main_sub2();
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
		socketListener.setActivity(getActivity());
	}

	@Override
	public void onStart(){
		super.onStart();
		SocketIO.getSocket().on("addCodeResult" , socketListener.getCodeResultListener());
	}

	@Override
	public void onStop(){
		super.onStop();
		SocketIO.getSocket().off("addCodeResult" , socketListener.getCodeResultListener());
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
	public void onDestroy(){
		super.onDestroy();
	}

	public void click() {
		EditText editText = (EditText)getActivity().findViewById(R.id.main_sub2_code_value);
		String code = editText.getText().toString().trim();
		if(code.length()!=5){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.sub2_wrong_code);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.cancel();
				}
			});
			builder.show();
		} else{
			editText.setText("");//이거 나중에 확인
			pDialog = ProgressDialog.show(getActivity() , "temp" ,"temp", true , false);
			socketListener.setpDialog(pDialog);
			mHandler.sendEmptyMessageDelayed(TIME_OUT, 2000);
			SocketIO.getSocket().emit("addCode" , code , GroupNameHelper.makeGroupName());
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == TIME_OUT) { // 타임아웃이 발생하면
				pDialog.dismiss(); // ProgressDialog를 종료
				pDialog = null;
			}
		}
	};
}
