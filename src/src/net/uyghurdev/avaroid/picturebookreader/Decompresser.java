package net.uyghurdev.avaroid.picturebookreader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Decompresser {
	private String _encode = "UTF-8";
	
	private String _fileName;

	private String location = "/sdcard/PictureBook/pbk/temp.mp3";
	private XMLContent ncx;
	private PlayOrder1 order;
	private ZipFile _zipFile;

	public Decompresser(String fileName) throws IOException {
		this._fileName = fileName;
		this._zipFile = new ZipFile(this._fileName);	
	}
	
	public InputStream getRootfilePath(String filePath) throws IOException,
			SAXException, ParserConfigurationException {
		ZipEntry entry = this._zipFile.getEntry(filePath);
		
		if (entry == null)
			return null;
		InputStream inputStream = this._zipFile.getInputStream(entry);
		
		return inputStream;
	}

	
	
	
	
	public XMLContent getORDER() throws IOException {
		// TODO Auto-generated method stub
		
		InputStream in = null;
		@SuppressWarnings("rawtypes")
		Enumeration entries = _zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry currentEntry = (ZipEntry) entries.nextElement();
			if (currentEntry.getName().endsWith(".xml")) {
				in = _zipFile.getInputStream(currentEntry);
				
			} 
		}
		
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			XMLHandler theEpubHandler = new XMLHandler();
			xmlreader.setContentHandler(theEpubHandler);
			xmlreader.parse(new InputSource(in));
			return theEpubHandler.getNCX();
		} catch (Exception ee) {
			return null;
		}
	}

//	public XMLContent getBookInfo() throws IOException {
//		// TODO Auto-generated method stub
//		
//		InputStream in = null;
//		@SuppressWarnings("rawtypes")
//		Enumeration entries = _zipFile.entries();
//		while (entries.hasMoreElements()) {
//			ZipEntry currentEntry = (ZipEntry) entries.nextElement();
//			if (currentEntry.getName().endsWith(".xml")) {
//				in = _zipFile.getInputStream(currentEntry);
//				
//			} 
//		}
//		
//		try {
//
//			SAXParserFactory factory = SAXParserFactory.newInstance();
//			SAXParser parser = factory.newSAXParser();
//			XMLReader xmlreader = parser.getXMLReader();
//			XMLHandler theEpubHandler = new XMLHandler();
//			xmlreader.setContentHandler(theEpubHandler);
//			xmlreader.parse(new InputSource(in));
//			return theEpubHandler.getNCX();
//		} catch (Exception ee) {
//			return null;
//		}
//	}
	
//	public InputStream ncxFileStream() throws IOException {
//		InputStream inputStream = null;
//		@SuppressWarnings("rawtypes")
//		Enumeration entries = _zipFile.entries();
//		while (entries.hasMoreElements()) {
//			ZipEntry currentEntry = (ZipEntry) entries.nextElement();
//			if (currentEntry.getName().endsWith(".xml")) {
//				inputStream = this._zipFile.getInputStream(currentEntry);
//			}
//
//		}
//
//		return inputStream;
//	}

//	@SuppressWarnings("unused")
//	private void setRelativePath(String path) {
//		// TODO Auto-generated method stub
//		for (int i = 0; i < path.length(); i++) {
//			String str = path.substring(0, path.length() - i);
//			if (str.endsWith("/")) {
//				return;
//			}
//		}
//	}

//	public XMLContent setBookOrder() throws IOException, XmlPullParserException {
//		// TODO Auto-generated method stub
//
//		InputStream inputStream = null;
//		@SuppressWarnings("rawtypes")
//		Enumeration entries = _zipFile.entries();
//		while (entries.hasMoreElements()) {
//			ZipEntry currentEntry = (ZipEntry) entries.nextElement();
//			if (currentEntry.getName().endsWith(".xml")) {
//				inputStream = _zipFile.getInputStream(currentEntry);
//				
//			} 
//
//		}
//
//		byte[] b = new byte[10240];
//		inputStream.read(b);
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlPullParser xpp = factory.newPullParser();
////		xpp.setInput(new StringReader(str));
//		
//		xpp.setInput(inputStream, _encode);
//		int eventType = xpp.getEventType();
//		ncx = new XMLContent();
//		order = new PlayOrder1();
//		while (eventType != XmlPullParser.END_DOCUMENT) {
//			
//
////			if (eventType == XmlPullParser.START_DOCUMENT) {
////				
////			} 
//			
//			if (eventType == XmlPullParser.START_TAG) {
//
//				if (xpp.getName().equals("page")) {
//					order = new PlayOrder1();
//				}
//
//				else if (xpp.getName().equals("id")) {
//					String text = xpp.nextText();
//
//					order.setId(text);
//
//				} else if (xpp.getName().equals("picture")) {
//					String text = xpp.nextText();
//					order.setPicPath(text);
//				} else if (xpp.getName().equals("sound")) {
//					String text = xpp.nextText();
//					order.setSoundPath(text);
//				} else if (xpp.getName().equals("text")) {
//					String text = xpp.nextText();
//					order.setText(text);
//				}
//			} else if (eventType == XmlPullParser.END_TAG) {
//				if (xpp.getName().equals("page")) {
//					ncx.addPlayOrder1(order);
//				}
//
//			}
//			eventType = xpp.next();
//		}
//		return ncx;
//
//	}

	public Bitmap getBitmap(String orderPic) {
		// TODO Auto-generated method stub
		Bitmap bMap = null;
		InputStream in;
		BufferedInputStream buf;
		try {
			in = getRootfilePath(orderPic);
			buf = new BufferedInputStream(in);
			bMap = BitmapFactory.decodeStream(buf);
			in.close();
			buf.close();
		} catch (Exception e) {
		}

		return bMap;
	}
	
	public Bitmap getCoverBitmap(String orderPic) {
		// TODO Auto-generated method stub
		Bitmap bMap = null;
		InputStream in;
		BufferedInputStream buf;
		try {
			in = getRootfilePath(orderPic);
			buf = new BufferedInputStream(in);
			bMap = BitmapFactory.decodeStream(buf);
			in.close();
			buf.close();
		} catch (Exception e) {
		}

		return bMap;
	}

	public void createTempFile(String orderSound) {
		// TODO Auto-generated method stub
		try {

			InputStream in = getRootfilePath(orderSound);
			ZipInputStream zin = new ZipInputStream(in);

			// ZipEntry ze = _zipFile.getEntry(orderSound);
			//
			// Log.v("Decompress", "Unzipping " + ze.getName());

			FileOutputStream fout = new FileOutputStream(location);

			int read;
			byte[] data = new byte[1024];
			while ((read = in.read(data)) != -1)
				fout.write(data, 0, read);

			// for (int c = zin.read(); c != -1; c = zin.read()) {
			// fout.write(c);
			// Log.v("Decompress", "writing");
			// }

			zin.closeEntry();
			fout.close();

			zin.close();
		} catch (Exception e) {
			e.toString();
		}
	}

}
