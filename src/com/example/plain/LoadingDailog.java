package com.example.plain;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class LoadingDailog extends Dialog{
	Typeface typeface;
    Context mContext;
	public LoadingDailog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context; 
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.progressdialog);
        typeface = Typeface.createFromAsset(mContext.getAssets(), "font/fangzhengthin.ttf");
        TextView loadingText = (TextView)findViewById(R.id.loadingText);
        //loadingText.setTypeface(typeface);
    }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
