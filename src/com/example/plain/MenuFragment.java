package com.example.plain;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.twitter.Twitter;

public class MenuFragment extends Fragment{
	private MainActivity activity;
	private View contentView;
	private Button weiboAuthButton;
	private Button twitterAuthButton;
	private TextView weiboTitle;
	private TextView twitterTitle;
	private TextView authText;
	private Typeface typeface;
	private Typeface typeface2;
	private Platform weibo;
	private Platform twitter;
	private Button about;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = (MainActivity)getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
		ShareSDK.initSDK(activity);
		weibo             = ShareSDK.getPlatform(activity, SinaWeibo.NAME);
		twitter           = ShareSDK.getPlatform(activity, Twitter.NAME);
		contentView       = inflater.inflate(R.layout.menu_layout, null);
		weiboAuthButton   = (Button)contentView.findViewById(R.id.weibologo);
		twitterAuthButton = (Button)contentView.findViewById(R.id.twitterlogo);
		weiboTitle        = (TextView)contentView.findViewById(R.id.weibotitle);
		twitterTitle      = (TextView)contentView.findViewById(R.id.twittertitle);
		authText          = (TextView)contentView.findViewById(R.id.authtext);
		about             = (Button)contentView.findViewById(R.id.about);
		typeface          = Typeface.createFromAsset(activity.getAssets(), "font/Roboto-Thin.ttf");
		typeface2         = Typeface.createFromAsset(activity.getAssets(), "font/fangzhengthin.ttf");
		authText.setTypeface(typeface2);
		weiboTitle.setTypeface(typeface);
		twitterTitle.setTypeface(typeface);
		weiboTitle.setText("Sina Weibo");
		twitterTitle.setText("   Twitter");
		weiboAuthButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				weibo.setPlatformActionListener(activity);
				weibo.authorize();
			}
		});
		twitterAuthButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				twitter.setPlatformActionListener(activity);
				twitter.authorize();
			}
		});
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity,AboutActivity.class);
				startActivity(intent);
				activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		});
		return contentView;
	}
}
