package com.example.plain;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class WelcomeActivity extends Activity {
	private static final int GO_HOME = 100;
	private static final int GO_GUIDE = 200;
	boolean isFirst = false;
    private Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
    	}
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wecome);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
		isFirst = preferences.getBoolean("isFirst", true);
		if(!isFirst){
			mHandler.sendEmptyMessageDelayed(GO_HOME, 500);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, 500);
		}
	}

	public void goHome(){
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		this.finish();
	}

	public void goGuide(){
		Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		this.finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wecome, menu);
		return true;
	}

}
