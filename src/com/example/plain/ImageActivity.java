package com.example.plain;

import java.io.File;

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
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageActivity extends Activity {

	ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
	TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9;
	Bitmap bitmap = null;
	Button retake;
	Bundle bundle;
	Typeface typeface;
	Typeface typeface2;
	IImageFilter filter1 = new SepiaFilter();
	IImageFilter filter2 = new BlackWhiteFilter();
	IImageFilter filter3 = new FilmFilter(80f);
	IImageFilter filter4 = new GammaFilter(40);
	IImageFilter filter5 = new SaturationModifyFilter();
	IImageFilter filter6 = new ZoomBlurFilter(30);
	IImageFilter filter7 = new EdgeFilter();
	IImageFilter filter8 = new ColorToneFilter(0x00FFFF, 109);
	LoadingDailog loadingDailog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		imageView2 = (ImageView)findViewById(R.id.imageView2);
		imageView3 = (ImageView)findViewById(R.id.imageView3);
		imageView4 = (ImageView)findViewById(R.id.imageView4);
		imageView5 = (ImageView)findViewById(R.id.imageView5);
		imageView6 = (ImageView)findViewById(R.id.imageView6);
		imageView7 = (ImageView)findViewById(R.id.imageView7);
		imageView8 = (ImageView)findViewById(R.id.imageView8);
		imageView9 = (ImageView)findViewById(R.id.imageView9);
		textView1  = (TextView)findViewById(R.id.textView1);
		textView2  = (TextView)findViewById(R.id.textView2);
		textView3  = (TextView)findViewById(R.id.textView3);
		textView4  = (TextView)findViewById(R.id.textView4);
		textView5  = (TextView)findViewById(R.id.textView5);
		textView6  = (TextView)findViewById(R.id.textView6);
		textView7  = (TextView)findViewById(R.id.textView7);
		textView8  = (TextView)findViewById(R.id.textView8);
		textView9  = (TextView)findViewById(R.id.textView9);
		typeface   = Typeface.createFromAsset(getAssets(), "font/Roboto-Light.ttf");
		typeface2  = Typeface.createFromAsset(getAssets(), "font/fangzhengthin.ttf");
		textView1.setTypeface(typeface);
		textView2.setTypeface(typeface);
		textView3.setTypeface(typeface);
		textView4.setTypeface(typeface);
		textView5.setTypeface(typeface);
		textView6.setTypeface(typeface);
		textView7.setTypeface(typeface);
		textView8.setTypeface(typeface);
		textView9.setTypeface(typeface);
		retake = (Button)findViewById(R.id.Retake);
		retake.setTypeface(typeface2);
		Intent intent = getIntent();
		bundle = new Bundle();
		if(intent != null){
			if(intent.getParcelableExtra("bitmap0") != null){
				bundle.putInt("is", 0);  //camera;
			    bitmap = Scale(ImageCrop((Bitmap) intent.getParcelableExtra("bitmap0")), display);
			    LoadImageFilter();
			    imageView1.setImageBitmap(bitmap);
			    imageView2.setImageBitmap(bitmap);
			    imageView3.setImageBitmap(bitmap);
			    imageView4.setImageBitmap(bitmap);
			    imageView5.setImageBitmap(bitmap);
			    imageView6.setImageBitmap(bitmap);
			    imageView7.setImageBitmap(bitmap);
			    imageView8.setImageBitmap(bitmap);
			    imageView9.setImageBitmap(bitmap);
			    //bitmap.recycle();
			}
			if(intent.getParcelableExtra("bitmap1")  != null){
				bundle.putInt("is", 1);   //gallery;
				bitmap = Scale(ImageCrop((Bitmap) intent.getParcelableExtra("bitmap1")), display);
				LoadImageFilter();
				imageView1.setImageBitmap(bitmap);
				imageView2.setImageBitmap(bitmap);
				imageView3.setImageBitmap(bitmap);
				imageView4.setImageBitmap(bitmap);
				imageView5.setImageBitmap(bitmap);
				imageView6.setImageBitmap(bitmap);
				imageView7.setImageBitmap(bitmap);
				imageView8.setImageBitmap(bitmap);
				imageView9.setImageBitmap(bitmap);
				//bitmap.recycle();
			}
		} else {
			Log.i("hehe","hehe");
		}
		
		imageView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent(ImageActivity.this, EditActivity.class);
			   bundle.putString("num", "SepiaFilter");
			   intent.putExtras(bundle);
			   startActivity(intent);
			   overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
		
		imageView2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "BlackWhite");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
		
        imageView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "Film");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "Gamma");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "SaturationModify");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "ZoomBlur");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "Edge");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "ColorTone");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        imageView9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, EditActivity.class);
				bundle.putString("num", "Original");
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
			}
		});
        
        retake.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageActivity.this, MainActivity.class);
				startActivity(intent);
			    onDestroy();
			}
		});

	}
	
	@Override
	protected void onDestroy() {
		bitmap = null;
		System.gc();
		overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
		super.onDestroy();
		
	};
	
	private void LoadImageFilter() {
		// TODO Auto-generated method stub
		new processImageTask(ImageActivity.this, filter1).execute();
		new processImageTask(ImageActivity.this, filter2).execute();
		new processImageTask(ImageActivity.this, filter3).execute();
		new processImageTask(ImageActivity.this, filter4).execute();
		new processImageTask(ImageActivity.this, filter5).execute();
		new processImageTask(ImageActivity.this, filter6).execute();
		new processImageTask(ImageActivity.this, filter7).execute();
		new processImageTask(ImageActivity.this, filter8).execute();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		ImageActivity.this.finish();
		System.gc();
		Intent intent = new Intent(ImageActivity.this, MainActivity.class);
		startActivity(intent);
		onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}
	
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // �õ�ͼƬ�Ŀ?��
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// ���к���ȡ����������߳�

        int retX = w > h ? (w - h) / 2 : 0;//����ԭͼ��ȡ�������Ͻ�x���
        int retY = w > h ? 0 : (h - w) / 2;

        //��������ǹؼ�
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }

    private static Bitmap Scale(Bitmap bitmap, Display display) {
          float screenWidth = display.getWidth();
          
          float bitmapWidth = bitmap.getWidth();
          
          float scaleWidth = screenWidth / 3.15f / bitmapWidth;
          
    	  Matrix matrix = new Matrix();
    	  matrix.postScale(scaleWidth, scaleWidth); //���Ϳ�Ŵ���С�ı���
    	  Log.i("scale","scale");
    	  return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    	 }
    
    public class processImageTask extends AsyncTask<Bitmap, Void, Bitmap>{

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
//			if(loadingDailog == null){
//			    loadingDailog = new LoadingDailog(ImageActivity.this);
//			    loadingDailog.show();
//			}
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
		protected void onPostExecute(Bitmap result) {
			if(result != null){
				super.onPostExecute(result);
				if(this.filter == filter1)
				    imageView1.setImageBitmap(result);
				if(this.filter == filter2)
					imageView2.setImageBitmap(result);
				if(this.filter == filter3)
					imageView3.setImageBitmap(result);
				if(this.filter == filter4)
					imageView4.setImageBitmap(result);
				if(this.filter == filter5)
					imageView5.setImageBitmap(result);
				if(this.filter == filter6)
					imageView6.setImageBitmap(result);
				if(this.filter == filter7)
					imageView7.setImageBitmap(result);
				if(this.filter == filter8){
					imageView8.setImageBitmap(result);
//					loadingDailog.dismiss();
				}
			    imageView9.setImageBitmap(bitmap);
			}

		}
    }

}
