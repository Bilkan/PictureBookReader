package net.uyghurdev.avaroid.picturebookreader;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class JSONManager {
	public int onlinePBar;
	ProgressDialog progressBar;
	Context cxt;
	JSONManager(Context _cxt){
		this.cxt=_cxt;
		
	}
	public ArrayList<GetOnlineBookInfo> getOnlineCats() {
		// TODO Auto-generated method stub
		ArrayList<GetOnlineBookInfo> cats = new ArrayList<GetOnlineBookInfo>();
		NetWorkOperator net = new NetWorkOperator();
		String jString = "";
		jString = net.urlToString(Configs.CategoriesServer);
		try {
			// this will break the JSON messages into an array
			JSONArray aryJSONStrings = new JSONArray(jString);
			// loop through the array
			for (int i = 0; i < aryJSONStrings.length(); i++) {
				GetOnlineBookInfo cat = new GetOnlineBookInfo();
				cat.setID(aryJSONStrings.getJSONObject(i).getInt("id"));

			}

		} catch (JSONException e) {
			Log.d("json", e.toString());
		}

		return cats;
	}

	public ArrayList<Book> getOnlineBooks() {
	
		// TODO Auto-generated method stub
		ArrayList<Book> books = new ArrayList<Book>();
		NetWorkOperator net = new NetWorkOperator();
		String jString1 = "";
		GetOnlineBookInfo cat = new GetOnlineBookInfo();
		jString1 = net.urlToString(Configs.CategoriesServer);
		JSONArray aryJSONStrings1;
		try {
			aryJSONStrings1 = new JSONArray(jString1);
			for (int f = 0; f < aryJSONStrings1.length(); f++) {
				cat.setID(aryJSONStrings1.getJSONObject(f).getInt("id"));
				Configs.CatId = cat.getID();

				String jString = "";
				jString = net.urlToString(Configs.BooksServer + Configs.CatId);

				try {
					// this will break the JSON messages into an array
					JSONArray aryJSONStrings = new JSONArray(jString);
					// loop through the array
					// onlinePBar=aryJSONStrings.length();
					for (int i = 0; i < aryJSONStrings.length(); i++) {
						Book book = new Book();

						book.setID(aryJSONStrings.getJSONObject(i).getInt("id"));
						book.setTitle(aryJSONStrings.getJSONObject(i)
								.getString("title"));
						book.setDescription(aryJSONStrings.getJSONObject(i)
								.getString("description"));
						book.setPubDate(Long.parseLong(aryJSONStrings
								.getJSONObject(i).getString("date")
								.replace("/", "").replace("Date(", "")
								.replace(")", "")));
						book.setAuthor(aryJSONStrings.getJSONObject(i)
								.getString("author"));
						book.setCoverImg(aryJSONStrings.getJSONObject(i)
								.getString("coverimage"));
						book.setLanguage(aryJSONStrings.getJSONObject(i)
								.getString("language"));
						book.setSize(aryJSONStrings.getJSONObject(i).getString(
								"size"));
					
						books.add(book);

					}

				} catch (JSONException e) {
					Log.d("json", e.toString());
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return books;

	}
}
