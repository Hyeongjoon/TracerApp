package com.example.admin.tracer;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.tracer.Helper.GroupNameHelper;


/* 수정된 Main Activity */
public class MainActivity extends FragmentActivity  {

	private int NUM_PAGES = 3;		// 최대 페이지의 수
	private boolean enabled;
	/* Fragment numbering */
	public final static int FRAGMENT_PAGE1 = 0;
	public final static int FRAGMENT_PAGE2 = 1;
	public final static int FRAGMENT_PAGE3 = 2;

	ViewPager mViewPager;			// View pager를 지칭할 변수
	pagerAdapter pagerAdapter;


	public MainActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ViewPager를 검색하고 Adapter를 달아주고, 첫 페이지를 선정해준다.
		mViewPager = (ViewPager)findViewById(R.id.pager);
		pagerAdapter = new pagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setCurrentItem(FRAGMENT_PAGE1);


		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if(position==FRAGMENT_PAGE1) {

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public void onStart(){
		super.onStart();
	}

	// FragmentPageAdater : Fragment로써 각각의 페이지를 어떻게 보여줄지 정의한다.
	private class pagerAdapter extends FragmentPagerAdapter{
		MainSub1Activity mainSub1Activity;
		MainSub2Activity mainSub2Activity;
		MainSub3Activity mainSub3Activity;
		public pagerAdapter(FragmentManager fm) {
			super(fm);
		}
		// 특정 위치에 있는 Fragment를 반환해준다.
		@Override
		public Fragment getItem(int position) {
					switch(position){
						case 0:{
							if(mainSub1Activity==null){
								mainSub1Activity = new MainSub1Activity();
							}
							return mainSub1Activity;
						}
						case 1: {
							if(mainSub2Activity==null){
								mainSub2Activity = new MainSub2Activity();
							}
							return mainSub2Activity;
						}
						case 2:{
							if(mainSub3Activity==null){
								mainSub3Activity = new MainSub3Activity();
							}
							return mainSub3Activity;
						}
				default:
					return null;
			}
		}

		// 생성 가능한 페이지 개수를 반환해준다.
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_PAGES;
		}
	}

	@Override
	public void onDestroy(){
		Log.d("msg" , mViewPager.getCurrentItem()+"");
		if(mViewPager.getCurrentItem()==0){
			this.findViewById(R.id.main_sub1_mid).setBackground(null);
		}
		super.onDestroy();
	}

	public void main_onClick(View v){
		switch (v.getId()){
			case R.id.main_sub1_add_group :{
				final AlertDialog.Builder wrongGroupName = new AlertDialog.Builder(this);
				wrongGroupName.setTitle(R.string.sub1_group_wrong_create);
				wrongGroupName.setPositiveButton(R.string.ok , new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.sub1_group_create_title);
				// Set up the input
				final EditText input = new EditText(this);
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
				input.setText(GroupNameHelper.makeGroupName());
				input.setSelectAllOnFocus(true);
				builder.setView(input);
				// Set up the buttons
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.setPositiveButton(R.string.sub1_group_create, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String groupName = input.getText().toString().trim();
						if (groupName.length() == 0) {
							wrongGroupName.show();
						} else {
							((MainSub1Activity)pagerAdapter.getItem(0)).makeGroup(groupName);
						}
					}
				});

				AlertDialog mDialog = builder.create();
				mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				mDialog.show();
				return;
			}
		}

	}
}
