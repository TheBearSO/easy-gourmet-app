package com.easygourmet.beans;

import com.easygourmet.utils.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase UserSettings.
 */
@DatabaseTable(tableName = UserSettings.TABLE_NAME)
public class UserSettings {
	
	/** The Constant TABLE_NAME. */
	public static final String TABLE_NAME = "userSettings";
	
	/** The Constant FIELD_NAME_id. */
	public static final String FIELD_NAME_id = "id";
	
	/** The Constant FIELD_VALUE_id. */
	public static final int FIELD_VALUE_id = 1;
	
	/** The Constant FIELD_NAME_checkBoxVegetariano. */
	public static final String FIELD_NAME_checkBoxVegetariano = "checkBoxVegetariano";
	
	/** The Constant FIELD_NAME_checkBoxVegano. */
	public static final String FIELD_NAME_checkBoxVegano = "checkBoxVegano";
	
	/** The Constant FIELD_NAME_checkBoxCeliaco. */
	public static final String FIELD_NAME_checkBoxCeliaco = "checkBoxCeliaco";
	
	/** El id auto incremental que indetifica de manera única a cada setting de usuario. */
	@DatabaseField(generatedId = true)
	private int id;
	
	/** check box vegetariano. */
	@DatabaseField(canBeNull = false)
	private int checkBoxVegetariano;
	
	/** check box vegano. */
	@DatabaseField(canBeNull = false)
	private int checkBoxVegano;
	
	/** check box celiaco. */
	@DatabaseField(canBeNull = false)
	private int checkBoxCeliaco;
	
	/**
	 * Contructor vacío.
	 */
	public UserSettings(){}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCheckBoxVegetariano() {
		return Utils.intToBoolean(checkBoxVegetariano);
	}

	public void setCheckBoxVegetariano(boolean checkBoxVegetariano) {
		this.checkBoxVegetariano = Utils.booleanToInt(checkBoxVegetariano);
	}

	public boolean isCheckBoxVegano() {
		return Utils.intToBoolean(checkBoxVegano);
	}

	public void setCheckBoxVegano(boolean checkBoxVegano) {
		this.checkBoxVegano = Utils.booleanToInt(checkBoxVegano);
	}

	public boolean isCheckBoxCeliaco() {
		return Utils.intToBoolean(checkBoxCeliaco);
	}

	public void setCheckBoxCeliaco(boolean checkBoxCeliaco) {
		this.checkBoxCeliaco = Utils.booleanToInt(checkBoxCeliaco);
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserSettings [checkBoxVegetariano=" + checkBoxVegetariano
				+ ", checkBoxVegano=" + checkBoxVegano + ", checkBoxCeliaco="
				+ checkBoxCeliaco + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + checkBoxCeliaco;
		result = prime * result + checkBoxVegano;
		result = prime * result + checkBoxVegetariano;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSettings other = (UserSettings) obj;
		if (checkBoxCeliaco != other.checkBoxCeliaco)
			return false;
		if (checkBoxVegano != other.checkBoxVegano)
			return false;
		if (checkBoxVegetariano != other.checkBoxVegetariano)
			return false;
		return true;
	}
}
