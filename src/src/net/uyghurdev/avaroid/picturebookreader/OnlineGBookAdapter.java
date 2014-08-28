package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class OnlineGBookAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Book> map;
	private static LayoutInflater inflater ;
	private int _wt, _ht;
	private RelativeLayout rLayout;
	public OnlineGBookAdapter(Activity a, ArrayList<Book> books,int wt,int ht){
		activity = a;
		map = books;
		_wt = wt;
		_ht=ht;
		inflater = (LayoutInflater) activity
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return map.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
		if (arg1 == null) {
			vi = inflater.inflate(R.layout.bookshelfonlinebook, null);
			
		}
		RelativeLayout imagerelative=new RelativeLayout(activity);
		ImageView img=new ImageView(activity);
		RelativeLayout rLayout = (RelativeLayout) vi
				.findViewById(R.id.llpic);
		RelativeLayout.LayoutParams lpl = new	RelativeLayout.LayoutParams(
				_wt, _ht);
		RelativeLayout.LayoutParams lpi = new	RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpi.setMargins(13, 15, 15, 10);
		lpl.setMargins(4, 2, 4, 0);
		imagerelative.setBackgroundResource(R.drawable.bookimage);
		imagerelative.addView(img, lpi);
		rLayout.removeView(img);
		rLayout.addView(imagerelative, lpl);
		
		if (new File("/sdcard/").exists()
				&& new File("/sdcard/").isDirectory()) {
			ImageLoader imageLoader = new ImageLoader(activity);
			imageLoader.DisplayImage(Configs.BookImg + map.get(arg0).getCoverImg(),
					activity, img);
		} else {
			img.setImageBitmap(BitmapFactory.decodeFile(Configs.BookImg + map.get(arg0).getCoverImg()));
		}
		
		
		return vi;
	}

}
