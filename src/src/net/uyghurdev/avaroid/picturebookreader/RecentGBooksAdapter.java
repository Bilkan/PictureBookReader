package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class RecentGBooksAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<File> map;
	private LayoutInflater inflater;
	

private int _wt, _ht;


	public RecentGBooksAdapter(Activity a, ArrayList<File> books,int wt, int ht) {

		activity = a;
		map = books;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		_wt = wt;
		_ht=ht;
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.local_book, null);
		}
		
		RelativeLayout imagerelative=new RelativeLayout(activity);
		ImageView img=new ImageView(activity);
		
		RelativeLayout rLayout = (RelativeLayout) vi
				.findViewById(R.id.relative);
		//int width=localbook.wt;
		RelativeLayout.LayoutParams lpl = new	RelativeLayout.LayoutParams(
				_wt, _ht);//((int) (_wt*(1.3))));
		RelativeLayout.LayoutParams lpi = new	RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		lpi.setMargins(13, 15, 15, 10);
		lpl.setMargins(4, 2, 4, 0);
		imagerelative.setBackgroundResource(R.drawable.bookimage);
		imagerelative.addView(img, lpi);
		rLayout.removeView(img);
		rLayout.addView(imagerelative, lpl);

		
		DecompressReader dr;
		Bitmap b = null;
		try {
			dr = new DecompressReader(map.get(position).getPath());
			b = dr.getCover();

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		if (b != null) {
			img.setImageBitmap(b);
		} else {
			img.setImageResource(R.drawable.cover);
		}

		return vi;
	}

}
