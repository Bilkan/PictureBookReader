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
import android.widget.TextView;

public class OnlineBookAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<Book> map;
	private static LayoutInflater inflater = null;
	
	public OnlineBookAdapter(Activity a, ArrayList<Book> books){
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return map.get(position);
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
			vi = inflater.inflate(R.layout.book, null);
		}
		TextView title=(TextView)vi.findViewById(R.id.title);
		TextView author=(TextView)vi.findViewById(R.id.author);
		TextView description=(TextView)vi.findViewById(R.id.description);
		TextView size=(TextView)vi.findViewById(R.id.size);
		ImageView bookCover = (ImageView)vi.findViewById(R.id.bookImage);
		
		title.setText(map.get(position).getTitle());
		author.setText(activity.getString(R.string.author) + " " + map.get(position).getAuthor());
		description.setText(activity.getString(R.string.description) + " " + map.get(position).getDescription());
		size.setText(activity.getString(R.string.size) + " " + map.get(position).getSize());
		
		if (new File("/sdcard/").exists()
				&& new File("/sdcard/").isDirectory()) {
			ImageLoader imageLoader = new ImageLoader(activity);
			imageLoader.DisplayImage(Configs.BookImg + map.get(position).getCoverImg(),
					activity, bookCover);
		} else {
			bookCover.setImageBitmap(BitmapFactory.decodeFile(Configs.BookImg + map.get(position).getCoverImg()));
		}
		
		
		return vi;
	}
}
