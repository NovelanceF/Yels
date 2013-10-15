package com.example.plain;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class GuideActivity extends Activity implements OnPageChangeListener{

	private TextView pageNum;
	private ViewPager vp;
	private List<View> views;
	private ViewPagerAdapter vpAdapter;
	private int currentIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initViews();
		initPageNum();
	}
	
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    	super.onBackPressed();
    }

	private void initPageNum() {
		// TODO Auto-generated method stub
		pageNum = (TextView)findViewById(R.id.pagenum);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "font/Roboto-Thin.ttf");
		pageNum.setTypeface(typeface);
		pageNum.setText("");
	}

	private void initViews() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		
		views.add(inflater.inflate(R.layout.one, null));
		views.add(inflater.inflate(R.layout.two, null));
		views.add(inflater.inflate(R.layout.three, null));
		
		vpAdapter = new ViewPagerAdapter(views, this);
		
		vp = (ViewPager)findViewById(R.id.viewpager);
		vp.setPageTransformer(true, new DepthPageTransformer());
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if(arg0 == 0){
			pageNum.setText("");
		} else {
			pageNum.setTextColor(0xFF767676);
			pageNum.setText(arg0+1+" - 3");
		}

	}
	
    public class ViewPagerAdapter extends PagerAdapter{

    	private List<View> views;
    	private Activity activity;
    	
    	public ViewPagerAdapter(List<View> views, Activity activity){
    		this.views = views;
    		this.activity = activity;
    	}
    	@Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(views != null){
				return views.size();
			}
			return 0;
		}
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1),0);
			if(arg1 == views.size() - 1) {
				ImageView mStart = (ImageView)arg0.findViewById(R.id.start);
				mStart.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						setGuided();
						goHome();
					}
				});
			}
			return views.get(arg1);
		}

		public void goHome() {
			Intent intent = new Intent(activity, MainActivity.class);
	        activity.startActivity(intent);
	        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	        activity.finish();
		}
		
		public void setGuided() {
			SharedPreferences preferences = activity.getSharedPreferences("first_pref", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0 == arg1);
		}
    }
}
