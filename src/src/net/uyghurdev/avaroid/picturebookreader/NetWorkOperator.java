package net.uyghurdev.avaroid.picturebookreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class NetWorkOperator {

	public String urlToString(String url) {
		// TODO Auto-generated method stub
		String str = "";
		URL jurl;
		try {

			jurl = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) jurl
					.openConnection();
			
			InputStream inputStream = urlConn.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}

			str = total.toString();
			
		}

		catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			Log.d("Exep", e1.toString());
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}

}
