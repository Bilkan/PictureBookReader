package net.uyghurdev.avaroid.picturebookreader;

public class GetOnlineBookInfo {
	
		private String CoverImage;
		private int ID;
		private String BookName;
		private String Description;
		String Auther;
		
		public GetOnlineBookInfo(){}
		
		public void setBookName(String book_name){
			BookName = book_name;
		}
		public String getBookName(){
			return BookName;
		}
		public void setCoverImg(String coverimg){
			CoverImage = coverimg;
		}
		
		public String getCoverImg(){
			return CoverImage;
		}
		
		public void setAuther(String auther){
			Auther=auther;
		}
		
		public String getAuther(){
			return Auther;
		}
		
		
		public void setID(int id){
			ID = id;
		}
		
		public int getID(){
			return ID;
		}
		
		
		
		
		public void setDescription(String desc){
			Description = desc;
		}
		
		public String getDescription(){
			return Description;
		}

		
		
	

}
