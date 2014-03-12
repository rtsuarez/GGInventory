package com.example.gginventory;

import java.io.File;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class PictureHelper {
	
	static final int PICTURE_REQUEST_CODE = 1;

	public static Uri getOutputMediaFileUri(int type, int value, String plantName){
		return Uri.fromFile(getOutputMediaFile(type, value, plantName));
	}
	
	@SuppressLint("NewApi") 
	private static File getOutputMediaFile(int type, int value, String plantName){
    	//file for pics/growingGrounds
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "GrowingGrounds" + File.separator + plantName);

		if(!mediaStorageDir.exists()){
			if(!(mediaStorageDir.mkdir() || mediaStorageDir.isDirectory())){
				Log.d("NewRecordScreen", "directory not created correctly");
				return null;
			}
		}
		
		String filePath = mediaStorageDir.getPath() + File.separator + Integer.toString(value) + ".jpg";
		filePath.replaceAll("\\s+", "");
		if(type == PICTURE_REQUEST_CODE){
			return new File(filePath);
		}
		return null;
	}
}
