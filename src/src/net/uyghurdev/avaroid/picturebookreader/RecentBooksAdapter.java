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
import android.widget.TextView;

public class RecentBooksAdapter extends BaseAdapter {


	private Activity activity;
	private ArrayList<File> map;
	private static LayoutInflater inflater = null;
	
	public RecentBooksAdapter(Activity a, ArrayList<File> books){
		
		activity = a;
		map = books;
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.localbook, null);
		}
		TextView text=(TextView)vi.findViewById(R.id.title);
		ImageView img = (ImageView)vi.findViewById(R.id.bookImage);
		text.setTextSize(11);
		text.setText(map.get(position).getName().replace("." + Configs.BookType, ""));
		
		DecompressReader dr;
		Bitmap b = null;
		try {
			dr = new DecompressReader(map.get(position).getPath());
			b = dr.getCover();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		if(b != null){
			img.setImageBitmap(b);
		}else{
			img.setImageResource(R.drawable.cover);
		}
		
		return vi;
	}
}