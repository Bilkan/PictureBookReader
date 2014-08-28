package net.uyghurdev.avaroid.picturebookreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DecompressReader {
	private String _encode = "UTF-8";
	String[] bookPara = new String[100];
	private String _fileName;
	BookText booktext;
	// Chapter chapter;
	int myPics;
	private String _path;
	PlayOrder1 playorder;
	private ZipFile _zipFile;

	public DecompressReader(String fileName) throws IOException {
		_fileName = fileName;
		_zipFile = new ZipFile(_fileName);
	}

	
	public Bitmap getCover() {
		Bitmap b;

		InputStream in = zipEntryToStream(".png");

		b = BitmapFactory.decodeStream(in);
		return b;
	}

	public int getPicsnumber() throws SAXException, IOException {
		
		booktext=new BookText();
		
		int picNumber = 0;
		InputStream in = null ;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			in = zipEntryToStream(".xml");
			Document dom = builder.parse(in);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("id");
			picNumber=items.getLength();
			NodeList context=root.getElementsByTagName("page");
//			
//			for(int i=0;i<context.getLength();i++){
//				Node text=context.item(i);
//				Node id=context.item(i);
//				Node soundPath=context.item(i);
//				Node picPath=context.item(i);
//				Node textcontent=((Element) text).getElementsByTagName("text").item(0);
//				String res=((Node) textcontent).getFirstChild().getNodeValue();
//				
//				Node idset=((Element) id).getElementsByTagName("id").item(0);
//				String id_set=((Node) idset).getFirstChild().getNodeValue();
//				Node pic_path=((Element) soundPath).getElementsByTagName("picture").item(0);
//				String picpath=((Node) pic_path).getFirstChild().getNodeValue();
//				Node sound_path=((Element) picPath).getElementsByTagName("sound").item(0);
//				String sound=((Node) sound_path).getFirstChild().getNodeValue();
//				
//			
//				
//				bookPara[i]=res;
				
				//booktext.setText(bookPara);
				
				//System.out.println("My XML context: "+bookPara[i]);
				
			//}
			//booktext.setText(bookPara);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return picNumber;
	}
	
	public String getContent() throws SAXException, IOException {
		
		
		String content = null;
		InputStream in = null ;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			in = zipEntryToStream(".xml");
			Document dom = builder.parse(in);
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("text");
			content=items.toString();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return content;
	}

//	private int parseOpf(InputStream in) {
//		// TODO Auto-generated method stub
//		String entry = null;
//		int picnumber = 0;
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//
//		try {
//
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document dom = builder.parse(in);
//			Element root = dom.getDocumentElement();
//			NodeList items = root.getElementsByTagName("id");
//			System.out.println("My Items Length: " + items.getLength());
//			picnumber=items.getLength();
//			for (int i = 0; i < items.getLength(); i++) {
//
//				Node item = items.item(i);
//
//				Element element = (Element) items.item(i);
//				String id = element.getAttribute("id").toLowerCase();
//				String href = element.getAttribute("href").toLowerCase();
//				if (id.contains("cover")
//						&& (href.endsWith("jpg") || href.endsWith("jpeg") || href
//								.endsWith("png"))) {
//					entry = element.getAttribute("href");
//					break;
//				}
//
//			}
//
//		} catch (Exception e) {
//			e.toString();
//			Log.d("My log", "Start index: " + e.toString());
//		}
//		return picnumber;
//	}

	private InputStream zipEntryToStream(String entry) {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		@SuppressWarnings("rawtypes")
		Enumeration entries = _zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry currentEntry = (ZipEntry) entries.nextElement();
			if (currentEntry.getName().endsWith(entry)) {
				try {
					inputStream = this._zipFile.getInputStream(currentEntry);
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return inputStream;
	}

	public void initialWork() throws IOException, SAXException,
			ParserConfigurationException {
		InputStream is = this.getRootfilePath("META-INF/container.xml");

	}

	public InputStream getRootfilePath(String filePath) throws IOException,
			SAXException, ParserConfigurationException {
		ZipEntry entry = this._zipFile.getEntry(filePath);

		if (entry == null)
			return null;
		InputStream inputStream = this._zipFile.getInputStream(entry);
		return inputStream;
	}

	private void setRelativePath(String path) {
		// TODO Auto-generated method stub
		for (int i = 0; i < path.length(); i++) {
			String str = path.substring(0, path.length() - i);
			if (str.endsWith("/")) {
				_path = str;
				Log.d("My Log", _path);
				return;
			}
		}
	}

}
