package com.example.plain;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.twitter.Twitter;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.weibo.sdk.android.sso.SsoHandler;

public class MainActivity extends Activity implements PlatformActionListener {

	String imgPath, timeStamp;
	Uri fileUri;
	private Button takePhotoButton;
	private Button chooseGallerybButton;
	private static final int CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE = 200;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private Platform weibo;
	private Platform twitter;
	private SsoHandler mSsoHandler;
	private SlidingMenu sm;
	private ImageButton toggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File destDir = new File("/mnt/sdcard/YlseTemp");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }
		ShareSDK.initSDK(MainActivity.this);
		initSlidingMenu();
		weibo                 = ShareSDK.getPlatform(MainActivity.this, SinaWeibo.NAME);
		twitter               = ShareSDK.getPlatform(MainActivity.this, Twitter.NAME);
		takePhotoButton       = (Button)findViewById(R.id.btn_take_photo);
		chooseGallerybButton  = (Button)findViewById(R.id.btn_choose_photo);
		toggle                = (ImageButton)findViewById(R.id.toggle);
		preferences           = getSharedPreferences("yalp", MODE_PRIVATE);
		editor                = preferences.edit();
		//timeStamp             = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		imgPath               = "/YlseImgtakeTemp" + ".jpg";
		Typeface typeface     = Typeface.createFromAsset(getAssets(), "font/fangzhengthin.ttf");
		takePhotoButton.setTypeface(typeface);
		chooseGallerybButton.setTypeface(typeface);
		MenuFragment menuFragment =               new MenuFragment();
		FragmentManager fragmentManager =         getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.menu, menuFragment).commit();
		takePhotoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFile()));
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom); 
				
			}
		});
		
		chooseGallerybButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom); 
			}
		});
		
		toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sm.toggle();
			}
		});
		
	}

	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
		sm = new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setFadeDegree(0.35f);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        sm.setShadowWidth(15);
        //sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffset(display.getWidth()*2/3);
        sm.setMenu(R.layout.menu_frame);
	}

	@Override
	protected void onDestroy(){
		ShareSDK.stopSDK(MainActivity.this);
		super.onDestroy();
	}
	public File getFile() {
	    File imageFile;
	    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
	    if (!file.exists()) {
	        file.mkdir();
	    }

	    imageFile = new File(file, imgPath);
	    Log.d("TEST", " onActivityResult ----> imageFile : " + imageFile.getAbsolutePath());
	    return imageFile;

	}

		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE || requestCode == 300) {
		    if (resultCode == RESULT_OK) {
		        // Image captured and saved to fileUri specified in the Intent
		        BitmapFactory.Options opts = new BitmapFactory.Options();
		        opts.inJustDecodeBounds = true;
		        BitmapFactory.decodeFile(getFile().getAbsolutePath(), opts);
		        opts.inSampleSize = calculateInSampleSize(opts, 250, 250);
		        opts.inJustDecodeBounds = false;
		        Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                Bitmap bitmap = BitmapFactory.decodeFile(getFile().getAbsolutePath(), opts);
				intent.putExtra("bitmap0", bitmap);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
                this.finish();
		    } else if (resultCode == RESULT_CANCELED) {
		        // User cancelled the image capture
		    } else {
		        // Image capture failed, advise user
		        }
		}
		    
	   if (requestCode == CAPTURE_GALLERY_ACTIVITY_REQUEST_CODE || requestCode == 400){
		  if(resultCode == RESULT_OK) {
		    	try {
					Uri originalUri = data.getData();
					byte[] mContext = readStream(resolver.openInputStream(Uri.parse(originalUri.toString())));
					BitmapFactory.Options opts = new BitmapFactory.Options();
					BitmapFactory.Options opts2 = new BitmapFactory.Options();
					opts.inJustDecodeBounds = true;
					opts2.inJustDecodeBounds = true;
					BitmapFactory.decodeByteArray(mContext, 0, mContext.length, opts);
					BitmapFactory.decodeByteArray(mContext, 0, mContext.length, opts2);
					opts.inSampleSize = calculateInSampleSize(opts, 250, 250);
					opts2.inSampleSize = calculateInSampleSize(opts2, 1500, 1500);
					opts2.inJustDecodeBounds = false;
					opts.inJustDecodeBounds = false;
					Bitmap mbitmap = getPicFromBytes(mContext, opts2);
					File file = new File(Environment.getExternalStorageDirectory(),"/YlseImgchooseTemp" + ".jpg");
					FileOutputStream outputStream = null;
					try{
						outputStream = new FileOutputStream(file);
						mbitmap.compress(CompressFormat.JPEG, 100, outputStream);
						outputStream.close();
						mbitmap.recycle();
					} catch (IOException e){
						e.printStackTrace();
					}
					Intent intent = new Intent(MainActivity.this, ImageActivity.class);
					mbitmap = BitmapFactory.decodeFile("/sdcard/YlseImgchooseTemp" + ".jpg", opts);
					intent.putExtra("bitmap1" ,mbitmap);
					startActivity(intent);
					overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out); 
					this.finish();
				} catch (Exception e) {
					// TODO: handle exception
	                    
				}
		    		
		    } else if (resultCode == RESULT_CANCELED) {
		    } else {
		    }
		}
		if (mSsoHandler != null) {
	        mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }


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
		
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
		
    public static Bitmap getPicFromBytes(byte[] bytes,
                BitmapFactory.Options opts) {
    if (bytes != null)
         if (opts != null)
             return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
         else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    return null;
    }
		@Override
		public void onCancel(Platform arg0, int arg1) {
			// TODO Auto-generated method stub
			//Toast.makeText(MainActivity.this, "cancel", 1000).show();
		}
		@Override
		public void onComplete(Platform arg0, int arg1,
				HashMap<String, Object> arg2) {
			// TODO Auto-generated method stub
			Log.i("auth", "success");
		}
		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			// TODO Auto-generated method stub
			//Toast.makeText(MainActivity.this, "error", 1000).show();
		}
}
