package net.uyghurdev.avaroid.picturebookreader;

public class Book {

	private int ID;
	private String Title;
	private String Author;
	private String Description;
	private int DownCount;
	private String Language;
	private long PubDate;
	private String CoverImage;
	private String Size;
	
	public Book(){}

	
	public void setID(int id){
		ID = id;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setTitle(String title){
		Title = title;
	}
	
	public String getTitle(){
		return Title;
	}
	
	public void setAuthor(String author){
		Author = author;
	}
	
	public String getAuthor(){
		return Author;
	}
	
	public void setDescription(String desc){
		Description = desc;
	}
	
	public String getDescription(){
		return Description;
	}
	
	public void setDownCount(int down){
		DownCount = down;
	}
	
	public int getDownCount(){
		return DownCount;
	}
	
	public void setLanguage(String lang){
		Language = lang;
	}
	
	public String  getLanguage(){
		return Language;
	}
	
	public void setPubDate(long date){
		PubDate = date;
	}
	
	public long getPubDate(){
		return PubDate;
	}
	
	public void setCoverImg(String coverimg){
		CoverImage = coverimg;
	}
	
	public String getCoverImg(){
		return CoverImage;
	}
	
	public void setSize(String size){
		Size = size;
	}
	
	public String getSize(){
		return Size;
	}
	
}