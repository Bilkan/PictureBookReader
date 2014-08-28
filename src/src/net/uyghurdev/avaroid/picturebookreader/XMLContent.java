package net.uyghurdev.avaroid.picturebookreader;

import java.util.List;
import java.util.Vector;



public class XMLContent {
	private  List<PlayOrder1> order;
	private String title=null;
	private String category=null;
	private String description=null;
	private String author=null;
	
	public XMLContent(){
		order=new Vector<PlayOrder1>();
		
	}
	
	public List<PlayOrder1> getAllOrder(){
		return order;
	}
	
	
	public void addPlayOrder1(PlayOrder1 pOrder){
		order.add(pOrder);
	}
	
	public String getOrderId(int location){
		return order.get(location).getId();
	}
	
	public String getOrderPic(int location){
		String str=order.get(location).getPicPath();
		return order.get(location).getPicPath();
	}
	
	public String getOrderSound(int location){
		return order.get(location).getSoundPath();
	}
	
	public String getOrderText(int location){
		return order.get(location).getText();
	}

	public void addTitle(String theString) {
		// TODO Auto-generated method stub
		title=theString;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void addAuthor(String theString) {
		// TODO Auto-generated method stub
		author=theString;
	}
	
	public String getAuthor(){
		return author;
	}

	public void addCategory(String theString) {
		// TODO Auto-generated method stub
		category=theString;
	}
	
	public String getCategory(){
		return category;
	}

	public void addDescription(String theString) {
		// TODO Auto-generated method stub
		description=theString;
	}
	
	public String getDescription(){
		return description;
	}
	
}