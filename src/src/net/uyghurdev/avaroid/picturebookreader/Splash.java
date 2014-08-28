package net.uyghurdev.avaroid.picturebookreader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity{

Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.splash);
		handler=new Handler();
		
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent();
				intent.setClass(Splash.this, LocalBook.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}
		
		}, 500);
			
		
		
		
	}
	
	
}
