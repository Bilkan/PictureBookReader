package net.uyghurdev.avaroid.picturebookreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookmarkData extends SQLiteOpenHelper {

	private final static String DB_PATH = "/data/data/net.uyghurdev.app.avaroid.epubreader/databases/";
	private static final String DATABASE_NAME = "avarreader";
	private final static int DATABASE_VERSION = 3;
	

	SQLiteDatabase db;
	Cursor cursor;

	public BookmarkData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//db.execSQL("CREATE TABLE bookmark(_id INTEGER PRIMARY KEY AUTOINCREMENT,filePath TEXT, MD5Code TEXT, chapter INTEGER, pageNum INTEGER, totalPage INTEGER);");
		db.execSQL("CREATE TABLE recentbooks(_id INTEGER PRIMARY KEY AUTOINCREMENT,filePath TEXT, fileName TEXT, time DATETIME);");
		db.execSQL("CREATE TABLE downloadbooks(_id INTEGER PRIMARY KEY AUTOINCREMENT,filePath TEXT, title TEXT, author TEXT, description TEXT, pubDate DATETIME, language TEXT, size TEXT);");
	    db.execSQL("CREATE TABLE showState(_id INTEGER PRIMARY KEY AUTOINCREMENT, showState INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

//	public void addBookmark(String fileName, String md5Code, int chapterNum,
//			int pageNum, int totalPage) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues cv = new ContentValues();
//		cv.put("filePath", fileName);
//		cv.put("MD5Code", md5Code);
//		cv.put("chapter", chapterNum);
//		cv.put("pageNum", pageNum);
//		cv.put("totalPage", totalPage);
//		db.insert("bookmark", "filePath", cv);
//	}

//	public void deleteBookmark(String filePath) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		String[] args = { filePath };
//		db.delete("bookmark", "filePath=?", args);
//	}
	public void insertValue(int showState) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("showState", showState);
		db.insert("showState", null, cv);
	}
	public void showCurrentState(int showState) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		cv.put("showState", showState);
		db.update("showState", cv, null, null);
	}
	public Cursor getshowState(){
		SQLiteDatabase db = this.getReadableDatabase();
		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		return cursor;
		
		
		
	}
	public Cursor checkShowState() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("showState", new String[] { "showState" },
				null, null, null, null, null);
		cursor.moveToFirst();
		
		return cursor;
	}

//	public Cursor getBookmark(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//		String[] selection = { "" + id };
//		String[] columns = { "chapter", "pageNum", "totalPage" };
//		Cursor cursor = db.query("bookmark", null, "_id=" + id, null, null,
//				null, null);
//		return cursor;
//	}

//	public Cursor getAllBookmarks() {
//		// TODO Auto-generated method stub
//		SQLiteDatabase db = this.getReadableDatabase();
//		String[] columns = { "_id", "MD5Code" };
//		Cursor cursor = db.query("bookmark", columns, null, null, null, null,
//				null);
//		return cursor;
//	}

	public Cursor getRecentBooks() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("recentbooks", new String[] { "filePath" },
				null, null, null, null, "time DESC");
		return cursor;
	}

	public void changeTime(String path, long time) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String[] args = { path };
		ContentValues cv = new ContentValues();
		cv.put("time", time);
		db.update("recentbooks", cv, "filePath=?", args);
	}

	public void deleteOpened(String _id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String[] args = {_id};
		db.delete("downloadbooks", "_id=?", args);
	}

	public Cursor getRecent(String filePath) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		String[] args = { filePath };
		Cursor cursor = db.query("recentbooks", null, "filePath=?", args, null,
				null, null);
		
		return cursor;
	}

	public void addRecent( String title, String filePath) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("filePath", filePath);
		cv.put("title", title);
		//cv.put("time", time);
		db.insert("downloadbooks", "title", cv);
	}

	public int getRecentFile(String currentFile) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		String[] args = { currentFile };
		Cursor cursor = db.query("recentbooks", null, "filePath=?", args, null,
				null, null);
		return cursor.getCount();
	}

	public Cursor getDownloadBooks() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query("downloadbooks", new String[] { "filePath" },
				null, null, null, null, null);
		
		return cursor;
	}

	public void downloadBook(Book book) {
		
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();

		cv.put("title", book.getTitle());
		cv.put("author", book.getAuthor());
		cv.put("description", book.getDescription());
		cv.put("filePath", "/sdcard" + Configs.SDPath + "/" + book.getTitle()
				+ "." + Configs.BookType);
		cv.put("pubDate", book.getPubDate());
		cv.put("language", book.getLanguage());
		cv.put("size", book.getSize());
		cv.put("language", book.getLanguage());
		cv.put("size", book.getSize());

		db.insert("downloadbooks", "filePath", cv);
	}

	public Cursor getFile() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query("downloadbooks", new String[] {"title","_id"} , null, null, null,
				null, null);
		return cursor;
	}

//	public void downloadBook(Book book, int i) {
//		// TODO Auto-generated method stub
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues cv = new ContentValues();
//		cv.put("title", book.getTitle() + "(" + i + ")");
//		cv.put("author", book.getAuthor());
//		cv.put("description", book.getDescription());
//		cv.put("filePath", "/sdcard" + Configs.SDPath + "/" + book.getTitle()
//				+ "(" + i + ")." + Configs.BookType);
//		cv.put("pubDate", book.getPubDate());
//
//		db.insert("downloadbooks", "filePath", cv);
//	}
}
