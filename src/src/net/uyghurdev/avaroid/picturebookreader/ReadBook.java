package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;
import java.io.IOException;

import net.uyghurdev.avaroid.picturebookreader.R.color;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class ReadBook extends Activity {
	MediaPlayer mp;
	String[] ii;
	Boolean sound = true;
	private CurlView mCurlView;
	Decompresser decompresser;
	private XMLContent content;
	String fileName;
	String textview;
	RelativeLayout rlayout;
	boolean showtext = true;
	boolean playsound = true;
	TextView tv;
	DecompressReader dre;
	public int pics;
	CurlView curlview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rlayout = (RelativeLayout) findViewById(R.id.rlcurl);
		fileName = getIntent().getStringExtra("file");
		setContentView(R.layout.test_curl);
		// DecompressReader dre = null;

		try {

			dre = new DecompressReader(fileName);

			// System.out.println("My Items Lengthc: " + dre.getPicsnumber());;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pics = dre.getPicsnumber();

			dre.getContent();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		// TextView textview=(TextView)findViewById(R.id.textView1);
		// getOrderText(0);
		rlayout = (RelativeLayout) findViewById(R.id.rlcurl);
		fileName = getIntent().getStringExtra("file");

		try {
			decompresser = new Decompresser(fileName);
			content = decompresser.getORDER();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curl);
		mCurlView.setPageProvider(new PageProvider());
		// mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(0xFF202830);

	}

	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

	/**
	 * Bitmap provider.
	 */
	private class PageProvider implements CurlView.PageProvider {
		int test = 0;

		// Bitmap resources.

		@Override
		public int getPageCount() {
			return pics;
		}

		@SuppressLint("NewApi")
		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {

			Bitmap front1 = decompresser.getBitmap(content.getOrderPic(index));
			Bitmap front = front1.copy(Bitmap.Config.ARGB_8888, true);
			page.setTexture(front, CurlPage.SIDE_FRONT);
			page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
			Canvas c = new Canvas(front);

			if (showtext) {
				front1 = decompresser.getBitmap(content.getOrderPic(index));
				front = front1.copy(Bitmap.Config.ARGB_8888, true);
				page.setTexture(front, CurlPage.SIDE_FRONT);
				page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
				c = new Canvas(front);

				showText(index, c);

			} else {
				front1 = decompresser.getBitmap(content.getOrderPic(index));
				front = front1.copy(Bitmap.Config.ARGB_8888, true);
				page.setTexture(front, CurlPage.SIDE_FRONT);
				page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
				c = new Canvas(front);

			}

			File temp = new File("/sdcard/PictureBook/pbk/temp.mp3");

			if (temp != null) {
				temp.delete();
			}

			if (test - index > 0) {
				if (content.getOrderSound(index + 1) != null
						&& content.getOrderSound(index + 1).endsWith(".mp3")) {

					decompresser.createTempFile(content
							.getOrderSound(index + 1));

					try {
						if (mp.isPlaying())
							mp.stop();

						playSound();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				if (content.getOrderSound(index) != null
						&& content.getOrderSound(index).endsWith(".mp3")) {

					decompresser.createTempFile(content.getOrderSound(index));

					try {
						if (mp != null && mp.isPlaying())
							mp.stop();
						playSound();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			test = index;
		}

	}

	private void playSound() throws IllegalArgumentException,
			IllegalStateException, IOException {
		// TODO Auto-generated method stub

		if (playsound) {

			mp = new MediaPlayer();
			if (mp.isPlaying()) {
				mp.stop();
				mp.release();
			}
			mp.setDataSource("/sdcard/PictureBook/pbk/temp.mp3");
			mp.prepare();
			mp.start();
		}
	}

	public void showText(int index, Canvas c) {

		tv = new TextView(getApplicationContext());
		String text1 = content.getOrderText(index);
		tv.setText(text1);
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(12);
		
		tv.setPadding(0, 0, 30, 0);
		tv.setShadowLayer(2, 1, 1, Color.BLACK);
		tv.layout(0, 0, c.getWidth(), 4 * tv.getLineHeight());
		RectF rf = new RectF(0, c.getHeight() - 4 * tv.getLineHeight(), c.getWidth(), c.getHeight());
		Paint paint = new Paint();
		paint.setColor(color.backgroundcolor);
		c.drawRect(rf, paint);
		c.translate(0, c.getHeight() - 4 * tv.getLineHeight());
		tv.draw(c);
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// 条用基类的方法，以便调出系统菜单（如果有的话�?

		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "تېكىست كونترولى");

		menu.add(0, 2, 0, "ئاۋاز كونترولى");// .setIcon(R.drawable.about).setTitle(R.id.voice_control);

		// 返回值为“true�?表示菜单可见，即显示菜单

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case 1:
			showtext = !showtext;
			

			break;

		case 2:
			playsound = !playsound;

			if (mp != null && mp.isPlaying()) {
				mp.stop();
			} else {
				try {
					playSound();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;

		}

		return true;

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (mp != null && mp.isPlaying()) {
				mp.stop();

				// localbook.onBackPressed();
			}
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
