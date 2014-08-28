package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LocalBook extends Activity implements OnClickListener {
	BookmarkData bookmark;
	ProgressDialog progressBar;
	Cursor checkBook, checkshowState;
	Cursor books;
	Cursor checkbooklist;
	int checkstate;
	ArrayList<String> saFiles;
	int currentbook = 0;
	ArrayList<File> allBooks;
	ListView localListview;
	Button lnetWork, lexit, labout, lrefresh;
	ImageButton listshow;
	JSONManager jMan;
	String root = "/sdcard/PictureBook/pbk/";
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	int b;
	GridView localGridView;
	boolean bookfileinsd;
	List<String> allbook;
	String test;
	public String[] dbStringArray;
	int[] dbIntArray;
	public int fileLength;
	int state;
	int wt, ht;
	OnlineBooks onlinebooks;
	public int networkstate;
	boolean currentState = true;
	RelativeLayout gLocalRelativeLayout;
	String sdcard_state = Environment.getExternalStorageState();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.localbooks);
		
		allBooks = new ArrayList<File>();
		bookmark = new BookmarkData(this);
		
		localListview = (ListView) findViewById(R.id.lbooklist);
		localListview.setCacheColorHint(0);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		wt = displaymetrics.widthPixels / 3;
		ht = (displaymetrics.heightPixels - 150) / 3;
		localGridView = (GridView) findViewById(R.id.localGridView);
		Button lnetWork = (Button) findViewById(R.id.lnetwork);
		lnetWork.setText(R.string.discon);
		Button lexit = (Button) findViewById(R.id.lexit);
		Button labout = (Button) findViewById(R.id.labout);
		Button lrefresh = (Button) findViewById(R.id.lrefresh);
		final ImageButton listshow = (ImageButton) findViewById(R.id.list);

		lnetWork.setOnClickListener(this);
		lexit.setOnClickListener(this);
		labout.setOnClickListener(this);
		lrefresh.setOnClickListener(this);
		initialShow(localListview, localGridView, listshow);
		listshow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentState = !currentState;
				if (currentState) {
					localListview.setVisibility(View.VISIBLE);
					localGridView.setVisibility(View.INVISIBLE);
					listshow.setBackgroundResource(R.drawable.btnlist);

				}

				else if (!currentState) {
					localListview.setVisibility(View.INVISIBLE);
					localGridView.setVisibility(View.VISIBLE);
					listshow.setBackgroundResource(R.drawable.btshelf);

				}

			}
		});
		checkBookList();
		deleteSameFile();
		SDcardTodb();
		setList();

		localListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				currentbook = position;
				extra();
				Intent intent = new Intent(LocalBook.this, ReadBook.class);
				intent.putExtra("file", root + saFiles.get(currentbook));
				startActivity(intent);
			}

		});
		localGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				currentbook = arg2;
				extra();
				Intent intent = new Intent(LocalBook.this, ReadBook.class);
				intent.putExtra("file", root + saFiles.get(currentbook));
				startActivity(intent);
			}

		});
		localListview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final File file = new File(localListview
						.getItemAtPosition(arg2).toString());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LocalBook.this);
				alertDialogBuilder.setTitle(file.getName().replace(".pbk", ""));
				alertDialogBuilder
						.setMessage(R.string.yesdelete)
						.setCancelable(false)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// boolean deleted =
										file.delete();
										setList();
									}
								})
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										return;
									}
								});
				alertDialogBuilder.show();

				return true;
			}

		});
		localGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final File file = new File(localListview
						.getItemAtPosition(arg2).toString());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LocalBook.this);
				alertDialogBuilder.setTitle(file.getName().replace(".pbk", ""));
				alertDialogBuilder
						.setMessage(R.string.yesdelete)
						.setCancelable(false)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										file.delete();
										setList();
									}
								})
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										return;
									}
								});
				alertDialogBuilder.show();

				return true;
			}

		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Log.d("CDA", "onKeyDown Called");
			onBackPressed();
		}

		return super.onKeyDown(keyCode, event);
	}

	private void setList() {
		// TODO Auto-generated method stub

		getBooks();

		RecentBooksAdapter adapter = new RecentBooksAdapter(this, allBooks);
		RecentGBooksAdapter gadapter = new RecentGBooksAdapter(this, allBooks,
				wt, ht);
		localListview.setAdapter(adapter);
		localGridView.setAdapter(gadapter);
		
	}

	private void getBooks() {
		// TODO Auto-generated method stub
		allBooks = new ArrayList<File>();
//		books = bookmark.getRecentBooks();
//		books.moveToFirst();
//		for (int b = 0; b < books.getCount(); b++) {
//			File f = new File(books.getString(0));
//			if (f.exists()) {
//				allBooks.add(f);
//			}
//			books.moveToNext();
//
//		}

//		books = bookmark.getDownloadBooks();
//		books.moveToFirst();
//		for (int b = 0; b < books.getCount(); b++) {
//			File f = new File(books.getString(0));
//			if (f.exists() && bookmark.getRecentFile(f.getPath()) <= 0) {
//
//				allBooks.add(f);
//
//			}
//			books.moveToNext();
//
//		}

		File[] files = null;

		File fl = new File(root);

		files = fl.listFiles();

		saFiles = new ArrayList<String>();
		for (int nIndex = 0; nIndex < files.length; nIndex++) {
			if (files[nIndex].getName().endsWith(".pbk")) {
				allBooks.add(files[nIndex]);
			}
		}
		
	}

	public void checkBookList() {
		checkbooklist = bookmark.getFile();
		checkbooklist.moveToFirst();

		String dbStr;
		int dbInt;
		dbStringArray = new String[checkbooklist.getCount()];
		dbIntArray = new int[checkbooklist.getCount()];

		for (int cb = 0; cb < checkbooklist.getCount(); cb++) {
			dbStr = checkbooklist.getString(0);
			dbInt = checkbooklist.getInt(1);
			dbStringArray[cb] = dbStr;
			dbIntArray[cb] = dbInt;

			checkbooklist.moveToNext();

		}

	}

	public void deleteSameFile() {
		for (int k = 0; k < checkbooklist.getCount(); k++) {
			for (int g = k + 1; g < checkbooklist.getCount(); g++) {
				if (dbStringArray[k].equals(dbStringArray[g])) {
					bookmark.deleteOpened(Integer.toString(dbIntArray[g]));

				}
			}

		}
	}

	public void SDcardTodb() {
		if (Environment.MEDIA_MOUNTED.equals(sdcard_state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;

			File fileDirectory = new File(
					Environment.getExternalStorageDirectory()
							+ "/PictureBook/pbk/");
			if (fileDirectory.exists()) {
				File[] dirFiles = fileDirectory.listFiles();
				if (dirFiles.length != 0) {

					for (int i = 0; i < dirFiles.length; i++) {
						if (dirFiles[i].getName().endsWith(
								"." + Configs.BookType)) {
							String fileOutput = dirFiles[i].getName().replace(
									".pbk", "");

							if (dbStringArray.length == 0) {
								bookmark.addRecent(fileOutput,
										dirFiles[i].getAbsolutePath());
							}
						}
					}
				}
			} else {
				fileDirectory.mkdirs();
			}
			
		} else {
			Toast.makeText(getApplicationContext(),
					"ئۈسكۈنىڭىزدە ساقلىغۇچ يوق، كارتا سىلىپ قايتا سىناڭ!",
					Toast.LENGTH_LONG).show();
			this.finish();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		currentState = !currentState;
		if (v.getId() == R.id.lnetwork) {
			state = checkNetwork(this);
			if (state == 0) {
				Toast.makeText(getApplicationContext(),
						"ئۈسكۈنە تورغا ئۇلانمىغان، تورغا ئۇلاپ قايتا سىناڭ!",
						Toast.LENGTH_SHORT).show();
				return;
			} 
//			else if(state==3){
//				Toast.makeText(getApplicationContext(),
//						"ئۈسكۈنىنى 3G ياكى Wifi تورىغا ئۇلاپ قايتا سىناڭ!",
//						Toast.LENGTH_SHORT).show();
//				return;
//			}
				else {
					
					
					
			}
				

				Intent intent = new Intent();
				intent.setClass(LocalBook.this, OnlineBooks.class);
				startActivity(intent);
				
				this.finish();
			
		} else if (v.getId() == R.id.lexit) {
			android.os.Process.killProcess(android.os.Process.myPid());
			this.finish();
		} else if (v.getId() == R.id.labout) {
			Intent intent = new Intent();
			intent.setClass(LocalBook.this, About.class);
			startActivity(intent);

		}

	}

	public void extra() {
		File[] files = null;

		File fl = new File(root);

		files = fl.listFiles();

		saFiles = new ArrayList<String>();
		for (int nIndex = 0; nIndex < files.length; nIndex++) {
			if (files[nIndex].getName().endsWith(".pbk")) {
				saFiles.add(files[nIndex].getName());
			}
		}
		
	}

	public int checkNetwork(Context ctx) {
		
		//int networkstate=0;
			ConnectivityManager cm = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			//int state = 0;
			if (info != null && info.isConnected()) {
				if (info.getType() == ConnectivityManager.TYPE_WIFI) {
					// WiFi Network
					networkstate = 1;
				} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
					// Mobile Network
					TelephonyManager tm = (TelephonyManager) ctx
							.getSystemService(Context.TELEPHONY_SERVICE);
					if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_1xRTT) {
						// Network type is 2G
						//Current network is 1xRTT
						//Constant Value: 7 (0x00000007)
						Log.d("Mobile Network", "1xRTT");
						networkstate = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_CDMA) {
						// Network type is 2G
						//Current network is CDMA: Either IS95A or IS95B
						//Constant Value: 4 (0x00000004)
						Log.d("Mobile Network", "CDMA");
						networkstate = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE) {
						// Network type is 2G
						//Current network is EDGE
						//Constant Value: 2 (0x00000002)
						Log.d("Mobile Network", "EDGE");
						networkstate = 3;
//					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EHRPD) {
//						// Network type is 2G
//						//Current network is eHRPD
//						//Constant Value: 14 (0x0000000e)
//						Log.d("Mobile Network", "eHRPD");
//						state = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_0) {
						// Network type is 3G
						//Current network is EVDO revision 0
						//Constant Value: 5 (0x00000005)
						//Log.d("Mobile Network", "EVDO_0");
						networkstate = 2;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_A) {
						// Network type is 3G
						//Current network is EVDO revision A
						//Constant Value: 6 (0x00000006)
						//Log.d("Mobile Network", "EVDO_A");
						networkstate = 2;
//					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EVDO_B) {
//						// Network type is 2G
//						//Current network is EVDO revision B
//						//Constant Value: 2 (0x00000002)
//						Log.d("Mobile Network", "EVDO_B");
//						state = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS) {
						// Network type is 2G
						//Current network is GPRS
						//Constant Value: 1 (0x00000001)
						//Log.d("Mobile Network", "GPRS");
						networkstate = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA) {
						// Network type is 3G
						//Current network is HSDPA
						//Constant Value: 8 (0x00000008)
						//Log.d("Mobile Network", "HSDPA");
						networkstate = 2;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA) {
						// Network type is 3G
						//Current network is HSPA
						//Constant Value: 10 (0x0000000a)
						//Log.d("Mobile Network", "HSPA");
						networkstate = 2;
//					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPAP) {
//						// Network type is 2G
//						//Current network is HSPAP
//						//Constant Value: 15 (0x0000000f)
//						Log.d("Mobile Network", "HSPAP");
//						state = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA) {
						// Network type is 3G
						//Current network is HSUPA
						//Constant Value: 9 (0x00000009)
						//Log.d("Mobile Network", "HSUPA");
						networkstate = 2;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_IDEN) {
						// Network type is 2G
						//Current network is IDEN
						//Constant Value: 11 (0x0000000b)
						//Log.d("Mobile Network", "iDEN");
						networkstate = 3;
//					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
//						// Network type is 4G
//						//Current network is LTE
//						//Constant Value: 13 (0x0000000d)
//						Log.d("Mobile Network", "LTE");
//						state = 2;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
						// Network type is 2G
						//Current network is UMTS
						//Constant Value: UMTS
						//Log.d("Mobile Network", "UMTS");
						networkstate = 3;
					} else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
						// Network type is 2G
						//Current network is UNKNOWN
						//Constant Value: 0 (0x00000000)
						//Log.d("Mobile Network", "UNKNOWN");
						networkstate = 3;
					}else{
						networkstate = 3;
					}

				}
			} else {
				// Network is off
				networkstate = 0;
			}
			return networkstate;
		}
		
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void delete(Object object) {
		File deleteFile = new File(root + object);
		deleteFile.delete();
	}

	public void initialShow(ListView l, GridView g, ImageButton im) {
		BookmarkData bookmark = new BookmarkData(this);

		checkshowState = bookmark.checkShowState();
		int i = checkshowState.getCount();

		if (i == 0) {
			bookmark.insertValue(1);
		}
		checkstate = bookmark.checkShowState().getInt(0);

		if (checkstate == 0) {
			l.setVisibility(View.INVISIBLE);
			im.setBackgroundResource(R.drawable.btshelf);
			currentState = false;
		} else if (checkstate != 0) {
			g.setVisibility(View.INVISIBLE);
			im.setBackgroundResource(R.drawable.btnlist);
			currentState = true;
		}
	}
	
}
