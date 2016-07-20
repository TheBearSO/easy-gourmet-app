package com.easygourmet.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * La Clase Utils, la cual contiene funciones static útiles que se utilizan en muchas partes del sistema.
 */
public class Utils {
	
	public static final String URL_CLOUDINARY_DB_JSON = "http://res.cloudinary.com/dwt3ti4fn/raw/upload/v1448830509/db.json";
	
	/**
	 * Conviete un boolean a int.
	 *
	 * @param bool El boolean a convertir
	 * @return 1 si bool es TRUE, 0 en caso contrario.
	 */
	public static int booleanToInt(boolean bool){
		if(bool) return 1;
		else return 0;
	}
	
	/**
	 * Conviete un int a boolean. (FALSE = 0, TRUE = 1)
	 *
	 * @param i El int a convertir
	 * @return TRUE si i es 1, FALSE caso contrario 
	 */
	public static boolean intToBoolean(int i){
		if(i == 1) return true;
		else return false;
	}
	
	/**
	 * Obtiene la url de una imagen con extensión '.jpg' coludinary a partir de un nombre de archivo.
	 *
	 * @param fileName El nombre de la imagen en la nube
	 * @param width El ancho que queremos setear a la imagen.
	 * @param height El alto que queremos setear a la imagen.
	 * @return Un string con la url (coludinary url).
	 */
	private static String getColudinaryURL(String fileName, int width, int height){
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", "dwt3ti4fn");
		config.put("api_key", "862626225766584");
		config.put("api_secret", "OuCR8BIYKHVRI_Ydy2WmZHYYCAY");
		Cloudinary cloudinary = new Cloudinary(config);
		
		String url = cloudinary.url()
							   .format("jpg")
							   .transformation(
									   new Transformation().width(width).height(height).crop("fill").gravity("north_west")
							   ).generate(fileName);
		
		return url;
	}
	
	public static void loadImage(final Context context, String fileName, int width, int height, final ImageView img){
		
		final String url = getColudinaryURL(fileName, width, height);
		
		Picasso.with(context)
		.load(url)
		.networkPolicy(NetworkPolicy.OFFLINE)
		.into(img, new Callback() {
		    @Override
		    public void onSuccess() {

		    }

		    @Override
		    public void onError() {
		        //Try again online if cache failed
		        Picasso.with(context)
		                .load(url)
		                .into(img, new Callback() {
		            @Override
		            public void onSuccess() {

		            }

		            @Override
		            public void onError() {
		                
		            }
		        });
		    }
		});
	}
	
	/**
	 * Ajusta el tamaño de la listview según la cantidad de items que contenga.
	 *
	 * @param listView La listview que se desea ajustar.
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	        ListAdapter listAdapter = listView.getAdapter(); 
	        if (listAdapter == null) return;

	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(0, 0);
	            totalHeight += listItem.getMeasuredHeight();
	        }

	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	}
	
	
	/**
	 * Deshabilita o habilita un layout y todos sus hijos.
	 *
	 * @param enable Si es true, habilita, caso contrario deshabilita.
	 * @param vg ViewGroup que se desea habilitar o deshabilitar.
	 */
	public static void disableEnableControls(boolean enable, ViewGroup vg){
	    for (int i = 0; i < vg.getChildCount(); i++){
	       View child = vg.getChildAt(i);
	       child.setEnabled(enable);
	       if (child instanceof ViewGroup) disableEnableControls(enable, (ViewGroup)child);
	    }
	}
	
	/**
	 * Cambia el color de la tab activa.
	 *
	 * @param tabHost El objeto TabHost
	 * @param tabBackgroungColor El color que van a tomar las  tabs no activas.
	 * @param tabBackgroungColorActive El color que va a tomar la tab activa.
	 */
	public static void setActiveTab(TabHost tabHost, int tabBackgroungColor, int tabBackgroungColorActive){
		TabWidget tabWidget = tabHost.getTabWidget();
		
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).setBackgroundColor(tabBackgroungColor);
        }
		
		tabWidget.getChildAt(tabHost.getCurrentTab()).setBackgroundColor(tabBackgroungColorActive);
	}
	
}
