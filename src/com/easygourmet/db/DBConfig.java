package com.easygourmet.db;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.IngredienteTipo;
import com.easygourmet.beans.Receta;
import com.easygourmet.beans.RecetaCategoria;
import com.easygourmet.beans.RecetaDetalle;
import com.easygourmet.beans.UserSettings;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * La Clase DBConfig la excribe un archivo de configuracion de ORMLITE.
 */
public class DBConfig extends OrmLiteConfigUtil {
	
	/** La Constante que contiene todas las clases que persisten en la base de datos. */
	private static final Class<?>[] classes = new Class[] {
	    Ingrediente.class, 
	    IngredienteTipo.class, 
	    Receta.class, 
	    RecetaCategoria.class, 
	    RecetaDetalle.class, 
	    UserSettings.class
	};
	
	/**
	 * El metodo main.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
	    writeConfigFile("ormlite_config.txt", classes);
	}
}
