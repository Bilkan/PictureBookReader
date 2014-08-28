package net.uyghurdev.avaroid.picturebookreader;

import java.util.ArrayList;
import java.util.HashMap;

public class Configs {
	protected static final String SDPath = "/PictureBook/pbk";
	protected static final String SDPathIm = "/PictureBook/Im";
	public static boolean FontChanged = false;
	public static ArrayList<HashMap<String, String>> Fonts = null;
	public static String FontName="ALKATIP Tor";
	public static String FontColor="BLACK";
	public static String BackGroundColor="WHITE";
	public static String CurrentFile="";
	public static int CurrentPage=0;
	public static String FilePath="";
	public static int FontSize=20;
	public static int Margin=20;
	public static int PartSeperator=10;
	public static int FontPosition=0;
	public static boolean FromHome = false;
	public static String BookType = "pbk";
	public static int CatId;
	public static int i;
	protected static int BookId;
	public static String CategoriesServer = "" + BookType+ "&catid="; // add your server address here
	public static String BooksServer = "" + BookType + "&catid="; // add your server address here
	public static String DownloadServer = ""; // add your server address here
	public static String BookImg = ""; // add your server address here
	public static int CurrentOrder=0;

	public static int tryParse(String string) {
		// TODO Auto-generated method stub
		try {
		    return new Integer(string);
		  } catch (NumberFormatException e) {
		    return 10;
		  }
	}
	

}
