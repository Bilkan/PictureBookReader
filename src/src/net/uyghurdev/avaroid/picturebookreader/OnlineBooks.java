package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class OnlineBooks extends Activity implements
		android.view.View.OnClickListener {
	// ProgressThread progThread;
	GridView onlineGridView;
	ProgressBar pb ;
	int po;
	int wt, ht;
	int filesize;
	int downloadedsize;
	ListView bookList;
	ArrayList<Book> books;
	JSONManager jMan = new JSONManager(this);
	boolean listshow;
	int typeBar;
	BackgroundTask bgTask;
	ProgressDialog progressBar;
	BookmarkData bookmark;
	Cursor checkBook, checkshowState;
	LocalBook localbook;
	ArrayList<GetOnlineBookInfo> cats = new ArrayList<GetOnlineBookInfo>();
	Button netWork, about, exit, refresh;
	ImageButton showbook;
	boolean currentState;
	HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	int showState;
	int checkstate;
	int boooo;
	private int progressBarStatus;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		wt = displaymetrics.widthPixels / 3;
		ht = (displaymetrics.heightPixels - 150) / 3;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.onlinebooks);
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// check net work, if connected checkNetwork is 1, in not 0;
		Button about = (Button) findViewById(R.id.about);
		Button exit = (Button) findViewById(R.id.exit);
		Button refresh = (Button) findViewById(R.id.nrefresh);
		final ImageButton showbook = (ImageButton) findViewById(R.id.booklist);

		showbook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				currentState = !currentState;
				if (currentState) {
					bookList.setVisibility(View.VISIBLE);
					onlineGridView.setVisibility(View.INVISIBLE);
					showbook.setBackgroundResource(R.drawable.btnlist);
					showState = 1;
				}

				else if (!currentState) {
					bookList.setVisibility(View.INVISIBLE);
					onlineGridView.setVisibility(View.VISIBLE);
					showbook.setBackgroundResource(R.drawable.btshelf);
					showState = 0;
				}
			}

		});
		onlineGridView = (GridView) findViewById(R.id.gridViewshow);

		final Button netWork = (Button) findViewById(R.id.network);
		netWork.setOnClickListener(this);
		init();
		//initialShow(bookList, onlineGridView, showbook);

		about.setOnClickListener(this);
		exit.setOnClickListener(this);
		refresh.setOnClickListener(this);

		bookList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> view, View v, int position,
					long id) {
				// TODO Auto-generated method stub

				Configs.BookId = books.get(position).getID();
				showBookInfo(position);

			}

		});
		onlineGridView
				.setOnItemClickListener(new GridView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> view, View v,
							int position, long id) {
						// TODO Auto-generated method stub
						Configs.BookId = books.get(position).getID();
						showBookInfo(position);
					}

				});
	}

	@Override
	public void onClick(View v) {
		currentState = !currentState;
		if (v.getId() == R.id.network) {
			Intent intent = new Intent();
			intent.setClass(OnlineBooks.this, LocalBook.class);
			startActivity(intent);
			this.finish();
		} else if (v.getId() == R.id.exit) {
			android.os.Process.killProcess(android.os.Process.myPid());
			this.finish();

		} else if (v.getId() == R.id.nrefresh) {
			//init();
			bgTask=new BackgroundTask();
			bgTask.execute();
		} else if (v.getId() == R.id.about) {
			Intent intent = new Intent();
			intent.setClass(OnlineBooks.this, About.class);
			startActivity(intent);

		}

	}

	private void init() {
		 pb = (ProgressBar) findViewById(R.id.dpb);
		// TODO Auto-generated method stub
		pb.setVisibility(View.INVISIBLE);
		//final JSONManager jMan = new JSONManager(this);
		bookList = (ListView) findViewById(R.id.online_booklist);
		bookList.setCacheColorHint(0);

		//books = jMan.getOnlineBooks();
		bgTask=new BackgroundTask();
		bgTask.execute();
//		OnlineGBookAdapter gadapter = new OnlineGBookAdapter(this, books, wt,
//				ht);
//		onlineGridView.setAdapter(gadapter);
//		OnlineBookAdapter adapter = new OnlineBookAdapter(this, books);
//		bookList.setAdapter(adapter);

	}

	private void showBookInfo( final int pos) {
		// TODO Auto-generated method stub
		//

		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.bookinfo, null);
		final TextView txtlink = (TextView) textEntryView
				.findViewById(R.id.txtlink);
		final ImageView img = (ImageView) textEntryView
				.findViewById(R.id.bookImg);
		ImageLoader imageLoader = new ImageLoader(this);
		imageLoader.DisplayImage(
				Configs.BookImg + books.get(pos).getCoverImg(), this, img);
		txtlink.setText(getString(R.string.author)
				+ " "
				+ books.get(pos).getAuthor()
				+ "\n"
				+ getString(R.string.description)
				+ " "
				+ books.get(pos).getDescription()
				+ "\n"
				+ getString(R.string.language)
				+ " "
				+ books.get(pos).getLanguage()
				+ "\n"
				+ getString(R.string.size)
				+ " "
				+ books.get(pos).getSize()
				+ "\n\n\n"
				+ getString(R.string.confirm_download).replace("%bt",
						Configs.BookType));
		final AlertDialog.Builder builder = new AlertDialog.Builder(OnlineBooks.this);
		builder.setCancelable(false);
		// builder.setIcon(R.drawable.icon);
		builder.setTitle(books.get(pos).getTitle());
		builder.setView(textEntryView);
		if (new File("/sdcard" + Configs.SDPath + "/"
				+ books.get(pos).getTitle() + "." + Configs.BookType).exists()) {
			builder.setPositiveButton(getString(R.string.read),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {

							Intent intent = new Intent(OnlineBooks.this,
									LocalBook.class);

							startActivity(intent);
							finish();
							Toast.makeText(getApplicationContext(),
									"بۇ ھۆججەت ئۈسكۈنىدە بار!",
									Toast.LENGTH_LONG).show();

						}

					});
		} else {
			builder.setPositiveButton(getString(R.string.download),
					new DialogInterface.OnClickListener() {
						// BookmarkData bookmark = new BookmarkData(
						// OnlineBooks.this);

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							
							downloadFile(pos);

						}

					});
		}
		builder.setNegativeButton(getString(R.string.close),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});
		builder.show();

	}

	@SuppressWarnings("static-access")
	void downloadFile(final int p) {
		// TODO Auto-generated method stub
		progressBar = new ProgressDialog(OnlineBooks.this);
		progressBar.setCancelable(false);
		progressBar.setMessage("سەل ساقلاڭ ھۆججەتنى چۈشۈرىۋاتىدۇ!");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();
		if (isSdPresent()) {

			if (new File("/sdcard" + Configs.SDPath + "/"
					+ books.get(p).getTitle() + "." + Configs.BookType)
					.exists()) {
				
			} else {

				
				new Thread(new Runnable() {

					public void run() {
						try {
							DownloadFile downloadBook = new DownloadFile(
									OnlineBooks.this, Configs.DownloadServer
											+ Configs.BookId, books.get(p)
											.getTitle()
											+ "."
											+ Configs.BookType, progressBar);
							downloadBook.downloadfile();
							Thread.sleep(1000);
							// progressBarStatus+=df.setDownloadedSize();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progressBar.dismiss();
					}

				}).start();

				BookmarkData data = new BookmarkData(this);
				data.downloadBook(books.get(p));

			}

		} else {

		}

	}

	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			BookmarkData bookmark = new BookmarkData(this);

			checkshowState = bookmark.checkShowState();
			int i = checkshowState.getCount();

			if (i == 0) {
				bookmark.insertValue(1);

			}

			Log.d("CDA", "onKeyDown Called");
			bookmark.showCurrentState(showState);

		}

		return super.onKeyDown(keyCode, event);
	}

	public void initialShow(ListView l, GridView g, ImageButton mbtn) {
		BookmarkData bookmark = new BookmarkData(this);

		checkshowState = bookmark.checkShowState();
		int i = checkshowState.getCount();

		if (i == 0) {
			bookmark.insertValue(1);
		}
		checkstate = bookmark.checkShowState().getInt(0);

		if (checkstate == 0) {
			l.setVisibility(View.INVISIBLE);
			mbtn.setBackgroundResource(R.drawable.btshelf);
			
			currentState = false;
		} else {
			g.setVisibility(View.INVISIBLE);
			mbtn.setBackgroundResource(R.drawable.btnlist);
			currentState = true;
		}
	}
	private class BackgroundTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			startRefresh();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			
			books = jMan.getOnlineBooks();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			stopRefresh();
			super.onPostExecute(result);
		}
	}

	protected void startRefresh() {
		pb.setVisibility(View.VISIBLE);
	}
	private void stopRefresh() {
		ImageButton showbook = (ImageButton) findViewById(R.id.booklist);
		OnlineGBookAdapter gadapter = new OnlineGBookAdapter(this, books, wt,
				ht);
		onlineGridView.setAdapter(gadapter);
		OnlineBookAdapter adapter = new OnlineBookAdapter(this, books);
		bookList.setAdapter(adapter);
		initialShow(bookList, onlineGridView, showbook);
		
		pb.setVisibility(View.INVISIBLE);
		
	}

}
