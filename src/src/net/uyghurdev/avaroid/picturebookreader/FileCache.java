package net.uyghurdev.avaroid.picturebookreader;

import java.io.File;

import android.content.Context;

public class FileCache {
	private File cacheDir;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					Configs.SDPathIm);
		else
			cacheDir = context.getCacheDir();
		// cacheDir = new File(Configs.ThumbFolder);
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename;
		// if(url.contains(".")){
		// filename=String.valueOf(url.hashCode()) +
		// url.substring(url.lastIndexOf('.'));
		// }else{
		filename = String.valueOf(url.hashCode());
		// }
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		// File[] files=cacheDir.listFiles();
		// for(File f:files)
		// f.delete();
	}
}
