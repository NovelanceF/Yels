package com.example.plain;

import com.example.plain.R.id;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {

	private Button problemsButton;
	private Button guidepageButton;
	private TextView personite;
	private TextView author;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		problemsButton = (Button)findViewById(R.id.problems);
		guidepageButton= (Button)findViewById(R.id.guidepages);
		personite      = (TextView)findViewById(R.id.personsite);
		author         = (TextView)findViewById(R.id.author);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "font/fangzhengthin.ttf");
		Typeface typeface2 = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
		author.setTypeface(typeface2);
		problemsButton.setTypeface(typeface);
		guidepageButton.setTypeface(typeface);
		problemsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				String subj = "Rylyn－问题反馈";
				String bodyString = "请在此输入反馈内容";
				
				String[] recip = new String[]{"Novelancef@gmail.com"};
				emailIntent.putExtra(Intent.EXTRA_EMAIL, recip);
				emailIntent.putExtra(Intent.EXTRA_TEXT, bodyString);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, subj);
				emailIntent.setType("message/rfc822");
				startActivity(emailIntent);
			}
		});
		guidepageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutActivity.this, GuideActivity.class);
				startActivity(intent);
				AboutActivity.this.finish();
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

}
