package com.easygourmet.beans.dao;

import java.sql.SQLException;

import android.app.Activity;
import android.widget.CheckBox;

import com.easygourmet.beans.UserSettings;
import com.easygourmet.db.DBHelper;
import com.easygourmet.main.R;
import com.easygourmet.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

public class UserSettingsDAO {
	/**
	 * Setea los valores de los checkbox ubicados en de la 'activity_menu_principal.xml' en la base de datos.
	 * 
	 * @param activity La activity en donde se ejecuta
	 * @param helper El helper de la base de datos
	 */
	public static UserSettings setUserSettings(Activity activity, DBHelper helper){
		
		UserSettings us = new UserSettings();
		
		try {
			
			boolean idVegetariano = ((CheckBox) activity.findViewById(R.id.checkBoxVegetariano)).isChecked();
			boolean idVegano = ((CheckBox) activity.findViewById(R.id.checkBoxVegano)).isChecked();
			boolean idCeliaco = ((CheckBox) activity.findViewById(R.id.checkBoxCeliaco)).isChecked();
			
			us.setCheckBoxVegetariano(idVegetariano);
			us.setCheckBoxVegano(idVegano);
			us.setCheckBoxCeliaco(idCeliaco);
			
			Dao<UserSettings, String> dao = helper.getDao(UserSettings.class);
			
			UpdateBuilder<UserSettings, String> ub = dao.updateBuilder();
			
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxVegetariano, Utils.booleanToInt(idVegetariano));
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxVegano, Utils.booleanToInt(idVegano));
			ub.updateColumnValue(UserSettings.FIELD_NAME_checkBoxCeliaco, Utils.booleanToInt(idCeliaco));
			
			ub.where().eq(UserSettings.FIELD_NAME_id, UserSettings.FIELD_VALUE_id);
	
			ub.update();
			
//			final LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.menu_principal_layout_checkboxes);
			
//			Utils.disableEnableControls(false, linearLayout);
			
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					Utils.disableEnableControls(true, linearLayout);
//				}
//			}, 25);
			
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
	public static UserSettings getUserSettings(DBHelper helper){
		UserSettings us = new UserSettings();
		try {
			
			Dao<UserSettings, String> dao = helper.getDao(UserSettings.class);
			us = dao.queryForId(String.valueOf(UserSettings.FIELD_VALUE_id));
			
		} catch (SQLException e) {
			return us;
		}
		
		return us;
	}
}
