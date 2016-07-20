package com.easygourmet.db;

import java.sql.SQLException;

import android.content.Context;

import com.easygourmet.beans.UserSettings;
import com.easygourmet.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

public class UserSettingsDBA {
	/**
	 * Setea los valores de los checkbox ubicados en de la 'activity_menu_principal.xml' en la base de datos.
	 * 
	 * @param activity La activity en donde se ejecuta
	 * @param helper El helper de la base de datos
	 */
	public static UserSettings setUserSettings(Context context, boolean isVegetariano, boolean isVegano, boolean isCeliaco){
		
		UserSettings us = new UserSettings();
		
		us.setCheckBoxVegetariano(isVegetariano);
		us.setCheckBoxVegano(isVegano);
		us.setCheckBoxCeliaco(isCeliaco);
		
		try {
			
			Dao<UserSettings, String> dao = DBHelper.getHelper(context).getDao(UserSettings.class);
			
			UpdateBuilder<UserSettings, String> ub = dao.updateBuilder();
			
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxVegetariano, Utils.booleanToInt(isVegetariano));
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxVegano, Utils.booleanToInt(isVegano));
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxCeliaco, Utils.booleanToInt(isCeliaco));
			
			ub.where().eq(UserSettings.FIELD_NAME_id, UserSettings.FIELD_VALUE_id);
	
			ub.update();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return us;
	}
	
	/**
	 * Obtiene los valores de la tabla 'userSettings' de la base de datos y los setea en un objeto 
	 * de tipo {@link com.easygourmet.beans.UserSettings}. 
	 * 
	 * @param helper El helper de la base de datos
	 * @return Un objeto de tipo {@link com.easygourmet.beans.UserSettings}.
	*/
	public static UserSettings getUserSettings(Context context){
		UserSettings us = new UserSettings();
		try {
			
			Dao<UserSettings, String> dao = DBHelper.getHelper(context).getDao(UserSettings.class);
			us = dao.queryForId(String.valueOf(UserSettings.FIELD_VALUE_id));
			
		} catch (SQLException e) {
			return us;
		}
		
		return us;
	}
}
