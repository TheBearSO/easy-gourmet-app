package com.easygourmet.utils;

import android.annotation.SuppressLint;


public class ImageUtils {
	
	@SuppressLint("DefaultLocale")
	public static String generateFileName(String folderName, String fileName){
		if(fileName == null || fileName == null){
			return "";
		}
		
		return folderName + "/" + fileName.toLowerCase();
	}
}
