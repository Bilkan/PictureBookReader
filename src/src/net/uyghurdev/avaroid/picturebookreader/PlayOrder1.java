package net.uyghurdev.avaroid.picturebookreader;


public class PlayOrder1 {

	private String id=null;
	private String picPath=null;
	private String soundPath=null;
	private String text=null;
	PlayOrder1 (){}
	
	void setId(String str){
		id=str;
	}
	
	public String getId(){
		return id;
	}
	
	void setPicPath(String str){
		picPath="Picture/"+str;
	}
	
	public String getPicPath(){
		return picPath;
	}
	
	void setSoundPath(String str){
		soundPath="Sound/"+str;
	}
	
	public String getSoundPath(){
		return soundPath;
	}
	
	void setText(String str){
		text=str;
	}
	
	public String getText(){
		return text;
	}
	
	
}
