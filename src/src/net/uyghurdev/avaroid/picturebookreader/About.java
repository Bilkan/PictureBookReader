package net.uyghurdev.avaroid.picturebookreader;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;

public class About extends Activity {
Button closeabout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		closeabout=(Button)findViewById(R.id.btnclose);
		
		closeabout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		}
		);
	}
	

	

}
