package net.uyghurdev.avaroid.picturebookreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

public class DownloadFile {
	String url_con;
	String saved_name;
	int filesize;
	public int downloaddsize;
	Context m_cxt;
	URL m_uri;
	private String SDPATH;
	private String fileName;
	public ProgressDialog progressBar;

	DownloadFile(Context cxt, String url_c, String fileName,
			ProgressDialog _progressBar) {
		this.m_cxt = cxt;
		this.url_con = url_c;
		this.progressBar = _progressBar;
		this.SDPATH = Environment.getExternalStorageDirectory()
				+ Configs.SDPath;
		this.m_cxt = cxt;
		this.fileName = fileName;
	}

	public void downloadfile() throws Throwable {
		URL url;
		File file = new File(SDPATH, fileName);
		url = new URL(url_con);
		long startTime = System.currentTimeMillis();
		HttpURLConnection ucon;
		ucon = (HttpURLConnection) url.openConnection();
		ucon.setRequestMethod("GET");
		ucon.setDoOutput(true);
		ucon.connect();
		filesize = ucon.getContentLength();
		InputStream is = ucon.getInputStream();
		BufferedInputStream inStream = new BufferedInputStream(is, 5 * 1024);
		FileOutputStream outStream = new FileOutputStream(file);
		byte[] buff = new byte[1024];
		int len;
		while ((len = inStream.read(buff)) != -1) {
			downloaddsize += len;
			getDownloadedSize(downloaddsize);
			int i = (int) ((float) downloaddsize * 100 / (float) filesize);
			progressBar.setProgress(i);
			outStream.write(buff, 0, len);

		}
		outStream.flush();
		outStream.close();
		inStream.close();
		System.out.println("download completed in "
				+ ((System.currentTimeMillis() - startTime) / 1000) + " sec");

	}

	public void getDownloadedSize(int i) {
		downloaddsize = i;

	}

	public int setDownloadedSize() {
		return downloaddsize;
	}

}

//
// public class DownloadFile {
//
// private static final int BUFFER_SIZE = 1024;
// private URL url;
// private byte[] buffer;
// private String urlStr;
// public int downloadedSize;
// public int fileSize;
// public int getFile;
// private HttpURLConnection con = null;
// private Boolean finished = false;
// private String SDPATH;
// private String fileName;
// Context cxt;
//
// public DownloadFile(Context _cxt, String urlStr, String fileName) {
// this.urlStr = urlStr;
// this.SDPATH = Environment.getExternalStorageDirectory()
// + Configs.SDPath;
// this.cxt=_cxt;
// this.fileName = fileName;
// File file = new File(SDPATH, fileName);
// try {
// url = new URL(urlStr);
// con = (HttpURLConnection) url.openConnection();
// con.setRequestMethod("GET");
// con.setDoOutput(true);
// con.connect();
// fileSize=con.getContentLength();
//
// } catch (MalformedURLException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// catch (IOException e) {
// e.printStackTrace();
// }
//
//
// }
//
// public int start() {
//
// try {
//
// File file = new File(SDPATH, fileName);
// url = new URL(urlStr);
// con = (HttpURLConnection) url.openConnection();
// con.setRequestMethod("GET");
// con.setDoOutput(true);
// con.connect();
// FileOutputStream fileOutput = new FileOutputStream(file);
// InputStream inputStream = con.getInputStream();
//
// downloadedSize = 0;
// buffer = new byte[BUFFER_SIZE];
// int bufferLength = 0;
// do {
// bufferLength = inputStream.read(buffer);
// if (bufferLength == -1) {
// break;
// }
// fileOutput.write(buffer, 0, bufferLength);
// downloadedSize += bufferLength;
// finished = false;
// } while (true); // close the output stream when done
//
// fileOutput.close();
// inputStream.close();
// finished = true;
// } catch (MalformedURLException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// }
// return downloadedSize;
//
//
//
// }
//
// public Boolean isFinish() {
// return finished;
// }
//
// public int getCur() {
// // TODO Auto-generated method stub
// return downloadedSize;
// }
//
// public int getFileLength() {
// return fileSize;
// }
//
// public String getFileName(String Url) {
// int fileNameStart = Url.lastIndexOf("/");
// return Url.substring(fileNameStart);
//
// }
//
// public boolean isFileExist(String fileName) {
// File file = new File(SDPATH + fileName);
// return file.exists();
// }
//
// }
