package com.easygourmet.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
	
	public static DBHelper helper;
	
    private static String DATABASE_NAME = "easygourmet.db";
    private static final int DATABASE_VERSION = 3;
    
    protected AndroidConnectionSource mConnectionSource = new AndroidConnectionSource(this);

    public static synchronized DBHelper getHelper(Context context){
    	if(helper == null){
    		helper = new DBHelper(context);
    	}
    	return helper;
    }
    
    public static synchronized SQLiteDatabase getReadDB(Context context){
    	return getHelper(context).getReadableDatabase();
    }
    
    public static synchronized SQLiteDatabase getWriteDB(Context context){
    	return getHelper(context).getWritableDatabase();
    }
    
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void close() {
        super.close();
    }

    /**
     *	Obtiene el DAO a partir de una clase pasada como argumento.
     *
     * @param clazz La clase de la cual se quiere obtener el dao.
     * @param <D>
     * @param <T>
     * @return Un objeto de tipo DAO de ORMLITE
     * @throws java.sql.SQLException
    */
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        // lookup the dao, possibly invoking the cached database config
        Dao<T, ?> dao = DaoManager.lookupDao(mConnectionSource, clazz);
        if (dao == null) {
            // try to use our new reflection magic
            DatabaseTableConfig<T> tableConfig = DatabaseTableConfigUtil.fromClass(mConnectionSource, clazz);
            if (tableConfig == null) {
                dao = (Dao<T, ?>) DaoManager.createDao(mConnectionSource, clazz);
            } else {
                dao = (Dao<T, ?>) DaoManager.createDao(mConnectionSource, tableConfig);
            }
        }

        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }
 

}
