package com.example.plain;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.plain.R.id;

import HaoRan.ImageFilter.BlackWhiteFilter;
import HaoRan.ImageFilter.ColorToneFilter;
import HaoRan.ImageFilter.EdgeFilter;
import HaoRan.ImageFilter.FilmFilter;
import HaoRan.ImageFilter.GammaFilter;
import HaoRan.ImageFilter.IImageFilter;
import HaoRan.ImageFilter.Image;
import HaoRan.ImageFilter.SaturationModifyFilter;
import HaoRan.ImageFilter.SepiaFilter;
import HaoRan.ImageFilter.ZoomBlurFilter;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.twitter.Twitter;



public class EditActivity extends Activity {
 
	private TextView textView,notice;
	private ImageView imageView;
	private Bitmap bitmap,bitmap2;
	private Bundle bundle;
	private IImageFilter filter;
	private Button save;
	private Button share;
	private String timeStamp;
	private RadioButton rbLight;
	private RadioButton rbMedium;
	private RadioButton rbHeavy;
	private TextView radiotext1;
	private TextView radiotext2;
	private TextView radiotext3;
	private String socialPlatform = SinaWeibo.NAME;
	private LoadingDailog loadingDailog;
	private Typeface typeface;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		File destDir = new File("/mnt/sdcard/Photo/Plain");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		}
		ShareSDK.initSDK(this);
		WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
	    radiotext1    = (TextView)findViewById(R.id.radio0text);
	    save          = (Button)findViewById(R.id.save);
	    radiotext2    = (TextView)findViewById(R.id.radio1text);
	    radiotext3    = (TextView)findViewById(R.id.radio2text);
	    rbLight       = (RadioButton)findViewById(R.id.radio0);
	    rbMedium      = (RadioButton)findViewById(R.id.radio1);
	    rbHeavy       = (RadioButton)findViewById(R.id.radio2);
		textView      = (TextView)findViewById(R.id.filter_id);
		imageView     = (ImageView)findViewById(R.id.sample_pic);
		share         = (Button)findViewById(R.id.weiboshare);
		notice        = (TextView)findViewById(R.id.notice);
		Typeface t    = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
		typeface      = Typeface.createFromAsset(getAssets(), "font/fangzhengthin.ttf");
		save.setTypeface(typeface);
		notice.setTypeface(typeface);
		radiotext1.setTypeface(t);
		radiotext2.setTypeface(t);
		radiotext3.setTypeface(t);
		share.setTypeface(t);
		textView.setTypeface(t);
		Intent intent = getIntent();
		bundle = new Bundle();
		bundle = intent.getExtras();
		final String string = bundle.getString("num");
		if(string.equals("Film") || string.equals("Gamma") || string.equals("ZoomBlur") || string.equals("ColorTone")){
			notice.setVisibility(View.GONE);
			rbLight.setVisibility(View.VISIBLE);
			rbMedium.setVisibility(View.VISIBLE);
			rbHeavy.setVisibility(View.VISIBLE);
			radiotext1.setVisibility(View.VISIBLE);
			radiotext2.setVisibility(View.VISIBLE);
			radiotext3.setVisibility(View.VISIBLE);
			rbLight.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					IImageFilter mFilter = loadMyFilter(string, 1);
					new processImageTask(EditActivity.this, mFilter).execute();
					rbMedium.setChecked(false);
					rbHeavy.setChecked(false);
				}
			});
			rbMedium.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					IImageFilter mFilter = loadMyFilter(string, 2);
					new processImageTask(EditActivity.this, mFilter).execute();
					rbLight.setChecked(false);
					rbHeavy.setChecked(false);
				}
			});
			rbHeavy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					IImageFilter mFilter = loadMyFilter(string, 3);
					new processImageTask(EditActivity.this, mFilter).execute();
					rbLight.setChecked(false);
					rbMedium.setChecked(false);
				}
			});
		}
        if(intent != null){
		    textView.setText(bundle.getString("num", "Error"));
        }
        bitmap = getBitmap(1);
        new processImageTask(EditActivity.this, loadMyFilter(bundle.getString("num", "Error"),2)).execute();
        share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onSharing();
			}
		});
        
	}
	
	@Override
	public void onDestroy(){
		overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		onDestroy();
	}
	
    private static Bitmap Scale(Bitmap bitmap, Display display) {
        float screenWidth = display.getWidth();
        
        float bitmapWidth = bitmap.getWidth();
        float scaleWidth;
     if(bitmap.getWidth() > bitmap.getHeight())   
        scaleWidth = screenWidth / 2f / bitmapWidth;
     else {
		scaleWidth = screenWidth / 3f / bitmapWidth;
	}   
  	  Matrix matrix = new Matrix();
  	  matrix.postScale(scaleWidth, scaleWidth); //���Ϳ�Ŵ���С�ı���
  	  Log.i("scale","scale");
  	  return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  	 }
    
	public Bitmap getBitmap(int i){
		BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inJustDecodeBounds = true;
	    BitmapFactory.Options opts2 = new BitmapFactory.Options();
	    opts2.inJustDecodeBounds = true;
	    Bitmap bitmap = null;
		if(i == 1){
		    if(bundle.getInt("is") == 0) { // camera;
		    	BitmapFactory.decodeFile("/sdcard/YlseImgtakeTemp" + ".jpg", opts);
        	    opts.inSampleSize = calculateInSampleSize(opts, 600, 600);
        	    opts.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile("/sdcard/YlseImgtakeTemp" + ".jpg", opts);
            }
        
            if(bundle.getInt("is") == 1) { //gallery
            	BitmapFactory.decodeFile("/sdcard/YlseImgchooseTemp" + ".jpg", opts);
        	    opts.inSampleSize = calculateInSampleSize(opts, 900, 900);
        	    opts.inJustDecodeBounds = false;
        	    bitmap = BitmapFactory.decodeFile("/sdcard/YlseImgchooseTemp" + ".jpg", opts);
            }
        return bitmap;
		}
	return null;
	}

	private IImageFilter loadMyFilter(String string, int i) {
		// TODO Auto-generated method stub
		if(string.equals("SepiaFilter")){
			filter = new SepiaFilter();
			return filter;
		} else if (string.equals("BlackWhite")){
			filter = new BlackWhiteFilter();
			return filter;
		} else if (string.equals("Film")){
			if(i == 1){
				filter = new FilmFilter(40f);
				return  filter;
			} else if (i == 2){
				filter = new FilmFilter(75f);
				return filter;
			} else if (i == 3){
				filter = new FilmFilter(100f);
				return filter;
			}
		} else if (string.equals("Gamma")){
			if(i == 1){
				filter = new GammaFilter(50);
				return filter;
			} else if (i == 2){
				filter = new GammaFilter(40);
				return filter;
			} else if (i == 3){
				filter = new GammaFilter(32);
				return filter;
			}
		} else if (string.equals("SaturationModify")){
			filter = new SaturationModifyFilter();
			return filter;
		} else if (string.equals("ZoomBlur")){
			if (i == 1){
				filter = new ZoomBlurFilter(10);
				return filter;
			} else if (i == 2){
				filter = new ZoomBlurFilter(15);
				return filter;
			} else if (i == 3){
				filter = new ZoomBlurFilter(20);
				return filter;
			}
		} else if (string.equals("Edge")){
			filter = new EdgeFilter();
			return filter;
		} else if (string.equals("ColorTone")){
			if(i == 1){
				filter = new ColorToneFilter(0x00FFFF, 75);
				return filter;
			} else if (i == 2){
				filter = new ColorToneFilter(0x00FFFF, 100);
				return filter;
			} else if (i == 3){
				filter = new ColorToneFilter(0x00FFFF, 125);
				return filter;
			}
		} else if (string.equals("Original")){
			filter = null;
			return filter;
		}
		return null;
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,  
            int reqWidth, int reqHeight) {  
        // Raw height and width of image  
        final int height = options.outHeight;  
        final int width = options.outWidth;  
        int inSampleSize = 1;  
  
        if (height > reqHeight || width > reqWidth) {  
  
            // Calculate ratios of height and width to requested height and  
            // width  
            final int heightRatio = Math.round((float) height  
                    / (float) reqHeight);  
            final int widthRatio = Math.round((float) width / (float) reqWidth);  
  
            // Choose the smallest ratio as inSampleSize value, this will  
            // guarantee  
            // a final image with both dimensions larger than or equal to the  
            // requested height and width.  
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;  
        }  
  
        return inSampleSize;  
    }
	
    public class processImageTask extends AsyncTask<Bitmap, Bitmap, Bitmap>{

    	private IImageFilter filter;
    	private Activity activity = null;
    	public processImageTask(Activity activity, IImageFilter imageFilter) {
			this.filter = imageFilter;
			this.activity = activity;
		}
    	
    	@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loadingDailog = new LoadingDailog(EditActivity.this);
			loadingDailog.show();
		}
    	
		@Override
		protected Bitmap doInBackground(Bitmap... bitmap0) {
			// TODO Auto-generated method stub
			Image img = null;
			try
	    	{
				img = new Image(bitmap);
				if (filter != null) {
					img = filter.process(img);
					img.copyPixelsFromBuffer();
				}
				return img.getImage();
	    	}
			catch(Exception e){
				if (img != null && img.destImage.isRecycled()) {
					img.destImage.recycle();
					img.destImage = null;
					System.gc(); // ����ϵͳ��ʱ����
				}
			}
			finally{
				if (img != null && img.image.isRecycled()) {
					img.image.recycle();
					img.image = null;
					System.gc(); // ����ϵͳ��ʱ����
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(final Bitmap result) {
			if(result != null){
				super.onPostExecute(result);
				imageView.setImageBitmap(result);
				//image to post
				String imagpathtemp = "/YlseToShareTemp.jpg";
				bitmap2 = result;
				File file = new File(Environment.getExternalStorageDirectory(), imagpathtemp);
				FileOutputStream outputStream = null;
				try{
					outputStream = new FileOutputStream(file);
					result.compress(CompressFormat.JPEG, 60, outputStream);
					outputStream.close();
				} catch (Exception e) {
					
				}
				save = (Button)findViewById(R.id.save);
				save.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
						String imgPath = "/Photo/Plain/img" + timeStamp + ".jpg";
						File file = new File(Environment.getExternalStorageDirectory(), imgPath);
						FileOutputStream outputStream = null;
						try {
							outputStream = new FileOutputStream(file);
							result.compress(CompressFormat.JPEG, 100, outputStream);
							outputStream.close();
							Toast.makeText(EditActivity.this, "Image saved to" + imgPath, 4000).show();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
			}
			loadingDailog.dismiss();
			if(this.filter == null){
                   imageView.setImageBitmap(bitmap);
			}
		}
    }
	private void onSharing() {
		// TODO Auto-generated method stub
		View shareDialog           = getLayoutInflater().inflate(R.layout.share_dialog, null);
		final EditText weiboEditor = (EditText)shareDialog.findViewById(R.id.weiboedit);
		ImageView samplePreView    = (ImageView)shareDialog.findViewById(R.id.samplepreview);
		final TextView textnum           = (TextView)shareDialog.findViewById(R.id.textnum);
		WindowManager manage       = getWindowManager();
	    Display display            = manage.getDefaultDisplay();
	    RadioGroup group           = (RadioGroup)shareDialog.findViewById(R.id.socialchoose);
		samplePreView.setImageBitmap(Scale(bitmap2, display));
		textnum.setText("还可以输入：140个字");
		weiboEditor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int count = 140 - s.length();
				textnum.setText("还可以输入:"+count+"个字");
				
			}
		});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = group.getCheckedRadioButtonId();
				if(id == R.id.sharetoweibo){
					weiboEditor.setHint("请输入微博内容");
					socialPlatform = SinaWeibo.NAME;
					Log.i("social",SinaWeibo.NAME);
				}
				if (id == R.id.sharetotwitter){
					weiboEditor.setHint("请输入Twitter内容");
					socialPlatform = Twitter.NAME;
					Log.i("social",Twitter.NAME);
				}
			}
		});
		new AlertDialog.Builder(EditActivity.this).setView(shareDialog)
		.setPositiveButton("好的", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			if(socialPlatform == SinaWeibo.NAME){
                showShare(true, SinaWeibo.NAME, weiboEditor.getText().toString());
                Log.i("share",SinaWeibo.NAME);
				}
			if(socialPlatform == Twitter.NAME){
				showShare(true, Twitter.NAME, weiboEditor.getText().toString());
				Log.i("share",Twitter.NAME);
			    }
			}
           }).setNegativeButton("取消", null).show();
	}
	private void showShare(boolean silent, String platform,String s) {
		if(s.length() <= 140){
			final OnekeyShare oks = new OnekeyShare();
			oks.setNotification(R.drawable.ic_launcher, "Yels");
			oks.setAddress("12345678901");
			oks.setTitleUrl("http://sharesdk.cn");
			oks.setText(s);
			oks.setImagePath("/sdcard/YlseToShareTemp.jpg");
			oks.setUrl("http://www.sharesdk.cn");
			oks.setSiteUrl("http://sharesdk.cn");
			oks.setVenueName("Southeast in China");
			oks.setVenueDescription("This is a beautiful place!");
			//oks.setLatitude(23.122619f);
			//oks.setLongitude(113.372338f);
			oks.setSilent(silent);
			if (platform != null) {
				oks.setPlatform(platform);
			}
			oks.show(EditActivity.this);
		}
		else Toast.makeText(EditActivity.this, "140字以下哟", 2000).show();

	}

}
