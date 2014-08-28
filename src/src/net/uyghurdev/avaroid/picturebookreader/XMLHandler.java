package net.uyghurdev.avaroid.picturebookreader;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XMLHandler extends DefaultHandler {

	// DecompressReader der;
	DecompressReader der;
	BookText booktext;
	XMLContent ncx = new XMLContent();
	ReadBook rBook = new ReadBook();
	public PlayOrder1 order;

	final int TITLE = 8;
	final int AUTHOR = 9;
	final int CATEGORY = 7;
	final int DESCRIPTION = 1;
	final int PAGE = 2;
	final int ID = 3;
	final int IMAGE = 4;
	final int SOUND = 5;
	final int TEXT = 6;
	StringBuilder builder;
	String fullPath = "";

	int currentstate = 0;

	/*
	 * Constructor
	 */

	public XMLHandler() {

	}

	public String getFullPath() {
		// TODO Auto-generated method stub
		return fullPath;
	}

	public XMLContent getNCX() {
		return ncx;
	}

	public void setFullPath(String path) {
		fullPath = path;
	}

	public void startDocument() throws SAXException {

		order = new PlayOrder1();
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		// read after &
		builder = new StringBuilder();
		if (localName.equals("page")) {
			order = new PlayOrder1();
			return;
		}

		if (localName.equals("title")) {
			currentstate = TITLE;
			return;
		}

		if (localName.equals("category")) {
			currentstate = CATEGORY;
			return;
		}

		if (localName.equals("description")) {
			currentstate = DESCRIPTION;
			return;
		}

		if (localName.equals("id")) {
			currentstate = ID;
			return;
		}

		if (localName.equals("picture")) {
			currentstate = IMAGE;
			return;
		}
		if (localName.equals("sound")) {
			currentstate = SOUND;
			return;
		}
		if (localName.equals("text")) {
			currentstate = TEXT;

			return;
		}
		if (localName.equals("author")) {
			currentstate = AUTHOR;
			return;
		}

		currentstate = 0;
	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		// come from characters method.
		if (currentstate == TEXT)
			order.setText(builder.toString());
		if (localName.equals("page")) {
			ncx.addPlayOrder1(order);
			return;
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		super.characters(ch, start, length);
		builder.append(ch, start, length);
		String theString = new String(ch, start, length);

		switch (currentstate) {

		case TITLE:
			ncx.addTitle(theString);
			currentstate = 0;
			break;
		case CATEGORY:
			ncx.addCategory(theString);
			currentstate = 0;
			break;
		case DESCRIPTION:
			ncx.addDescription(theString);
			currentstate = 0;
			break;
		case ID:
			order.setId(theString);
			currentstate = 0;
			break;
		case IMAGE:
			order.setPicPath(theString);
			currentstate = 0;
			break;
		case SOUND:
			order.setSoundPath(theString);
			currentstate = 0;
			break;
		// Can not read symbol "&" have been removed. please check the
		// endElement method.

		// case TEXT:
		// Log.e("The full text: ", builder.toString());
		// order.setText(builder.toString());
		// currentstate = 0;
		// break;
		case AUTHOR:
			ncx.addAuthor(theString);
			currentstate = 0;
			break;

		default:
			return;
		}

	}
}
