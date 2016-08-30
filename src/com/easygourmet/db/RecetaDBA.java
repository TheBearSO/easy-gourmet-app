package com.easygourmet.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.Receta;
import com.easygourmet.beans.RecetaCategoria;
import com.easygourmet.beans.RecetaDetalle;
import com.easygourmet.beans.UserSettings;
import com.easygourmet.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class RecetaDBA {
	
	/**
	 * Trae una receta de la base de datos por id de receta (idReceta).
	 * 
	 * @param idReceta El id de la receta a buscar
	 * @param helper El helper de la base de datos
	 * @return Un Objeto {@link com.easygourmet.beans.Receta} si existe el id en la base de datos, 
	 * 		   o <code>null</code> en caso contrario.  
	 */
	public static Receta getRecetaById(int idReceta, Context context){
		
		Receta r = null;
		try {
			Dao<Receta, Integer> recetaDao = DBHelper.getHelper(context).getDao(Receta.class);
			r = recetaDao.queryForId(idReceta);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
	/**
	 * Retorna recetas basadas en los settings del usuario.
	 * 
	 * @param  helper El helper de la base de datos
	 * @return Un Objeto {@link com.easygourmet.beans.Receta}
	 */
	public static List<Receta> getAllWhere(Context context){
		List<Receta> recetas = new ArrayList<Receta>();
		
		try {
			UserSettings us = UserSettingsDBA.getUserSettings(context);
			boolean isVegetariano = us.isCheckBoxVegetariano();
			boolean isVegano = us.isCheckBoxVegano();
			boolean isCeliaco = us.isCheckBoxCeliaco();
			
			Dao<Receta, Integer> dao = DBHelper.getHelper(context).getDao(Receta.class);
			
			QueryBuilder<Receta, Integer> qb = dao.queryBuilder();
			
			if(isVegetariano){
				if(!isVegano && !isCeliaco){
					qb.where().eq(Ingrediente.FIELD_NAME_vegetariano, true);
				}else if(isVegano && !isCeliaco){
					qb.where()
					.eq(Ingrediente.FIELD_NAME_vegetariano, true).and()
					.eq(Ingrediente.FIELD_NAME_vegano, true);
				}else if(!isVegano && isCeliaco){
					qb.where()
					.eq(Ingrediente.FIELD_NAME_vegetariano, true).and()
					.eq(Ingrediente.FIELD_NAME_celiaco, true);
				}else{
					qb.where()
					.eq(Ingrediente.FIELD_NAME_vegetariano, true).and()
					.eq(Ingrediente.FIELD_NAME_vegano, true).and()
					.eq(Ingrediente.FIELD_NAME_celiaco, true);
				}
			}else if(isVegano){
				if(!isCeliaco){
					qb.where().eq(Ingrediente.FIELD_NAME_vegano, true);
				}else {
					qb.where()
					.eq(Ingrediente.FIELD_NAME_vegano, true).and()
					.eq(Ingrediente.FIELD_NAME_celiaco, true);
				}
			}else if(isCeliaco){
				qb.where().eq(Ingrediente.FIELD_NAME_celiaco, true);
			}
			
			
			qb.orderBy(Receta.FIELD_NAME_idReceta, false);
			
			recetas = qb.query();
			
		} catch (SQLException e) {
			return recetas;
		}
		
		return recetas;
	}
	
	public static List<Receta> getRecetasByIngrediente(Context context, Integer idIngrediente){
		
		final String sql = "SELECT * FROM ( " +
								"SELECT COUNT(d.idDetalle) AS det, *  from recetas r " +
								"LEFT JOIN recetasDetalles d ON r.idReceta = d.receta_idReceta " +
								"GROUP BY d.receta_idReceta " +
							 ") sub " +
							 "LEFT JOIN recetasDetalles d1 on sub.idReceta = d1.receta_idReceta " +
							 "WHERE d1.ingrediente_idIngrediente = ? " +
							 "ORDER BY sub.det";
		
		Cursor c = DBHelper.getReadDB(context).rawQuery(sql, new String[]{idIngrediente.toString()});
		List<Receta> recetas = new ArrayList<Receta>();
		
		try {
			Receta r;
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				r = new Receta();
				r.setIdReceta( c.getInt(c.getColumnIndex("idReceta")) );
				r.setNombre( c.getString(c.getColumnIndex("nombre")) );
				r.setDescripcion( c.getString(c.getColumnIndex("descripcion")) );
				r.setPreparacion( c.getString(c.getColumnIndex("preparacion")) );
				r.setTiempo( c.getString(c.getColumnIndex("tiempo")) );
				r.setDificultad( c.getString(c.getColumnIndex("dificultad")) );
				r.setVegetariano( Utils.intToBoolean(c.getInt(c.getColumnIndex("vegetariano"))) );
				r.setVegano( Utils.intToBoolean(c.getInt(c.getColumnIndex("vegano"))) );
				r.setCeliaco( Utils.intToBoolean(c.getInt(c.getColumnIndex("celiaco"))) );
			    
				recetas.add(r);
			}
		
		}finally{
			c.close();
		}
		
		recetas = filterUserSettings(context, recetas);
		
		return recetas;
	}
	
	/**
	 * Retorna una receta de la base de datos al azar.
	 * 
	 * @param  helper El helper de la base de datos
	 * @return Un Objeto {@link com.easygourmet.beans.Receta}
	 */
	public static Receta getRecetaDelDia(Context context){
		Receta recetaDelDia = new Receta();
		try {
			UserSettings us = UserSettingsDBA.getUserSettings(context);
			
			Dao<Receta, Integer> dao = DBHelper.getHelper(context).getDao(Receta.class);
			
			QueryBuilder<Receta, Integer> qb = dao.queryBuilder();
			
			if(us.isCheckBoxCeliaco() && us.isCheckBoxVegetariano()) qb.where().eq(Receta.FIELD_NAME_celiaco, true).and().eq(Receta.FIELD_NAME_vegetariano, true);
			else if(us.isCheckBoxCeliaco() && us.isCheckBoxVegano()) qb.where().eq(Receta.FIELD_NAME_celiaco, true).and().eq(Receta.FIELD_NAME_vegano, true);
			else if(us.isCheckBoxVegetariano()) qb.where().eq(Receta.FIELD_NAME_vegetariano, true);
			else if(us.isCheckBoxVegano()) qb.where().eq(Receta.FIELD_NAME_vegano, true);
			else if(us.isCheckBoxCeliaco()) qb.where().eq(Receta.FIELD_NAME_celiaco, true);
			
			qb.orderByRaw("RANDOM()");
			
			recetaDelDia = dao.queryForFirst(qb.prepare());
			
		} catch (SQLException e) {
			return null;
		}
		
		return recetaDelDia;
	}
	
	public static List<Receta> getRecetasByIngredientes(Context context, List<Integer> ingredintesElegidos){
		List<Receta> recetas = new ArrayList<>();
		
		try {
			
			Dao<Receta, Integer> recetaDao = DBHelper.getHelper(context).getDao(Receta.class);
			Dao<RecetaDetalle, Integer> recetaDetalleDao = DBHelper.getHelper(context).getDao(RecetaDetalle.class);
			
			QueryBuilder<Receta, Integer> recetaQB = recetaDao.queryBuilder();
			QueryBuilder<RecetaDetalle, Integer> recetaDetalleQB = recetaDetalleDao.queryBuilder();
			QueryBuilder<RecetaDetalle, Integer> recetaDetalleSubQB = recetaDetalleDao.queryBuilder();
			
			recetaQB.join(recetaDetalleQB);
			if(ingredintesElegidos.size() > 0){
				recetaDetalleSubQB.selectColumns("receta_idReceta").where().in("ingrediente_idIngrediente", ingredintesElegidos);
			}
			recetaDetalleSubQB.groupBy("receta_idReceta");
			recetaDetalleSubQB.having("COUNT(DISTINCT ingrediente_idIngrediente) >= " + ingredintesElegidos.size());
			
			List<Integer> idsReceta = new ArrayList<>();
			for(RecetaDetalle rd : recetaDetalleSubQB.query()) idsReceta.add(rd.getReceta().getIdReceta());
			
			recetaDetalleQB.where().in("receta_idReceta", idsReceta);
			recetaDetalleQB.groupBy("receta_idReceta");
			
			recetas = recetaDao.query(recetaQB.prepare());
			recetas = filterUserSettings(context, recetas);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recetas;
	}
	
	public static List<Receta> getRecetasByIngredientesAnyMatch(Context context, List<Integer> ingredintesElegidos){
		List<Receta> recetas = new ArrayList<>();
		
		try {
			
			Dao<Receta, Integer> recetaDao = DBHelper.getHelper(context).getDao(Receta.class);
			Dao<RecetaDetalle, Integer> recetaDetalleDao = DBHelper.getHelper(context).getDao(RecetaDetalle.class);
			
			QueryBuilder<Receta, Integer> recetaQB = recetaDao.queryBuilder();
			QueryBuilder<RecetaDetalle, Integer> recetaDetalleQB = recetaDetalleDao.queryBuilder();
			
			recetaQB.join(recetaDetalleQB);
			recetaDetalleQB.where().in("ingrediente_idIngrediente", ingredintesElegidos);
			recetaDetalleQB.groupBy("receta_idReceta");
			
			recetas = recetaDao.query(recetaQB.prepare());
			recetas = filterUserSettings(context, recetas);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recetas;
	}
	
	public static List<Receta> getRecetasByCategoria(Context context, int idCategoria){
		
		List<Receta> recetas = new ArrayList<>();
		
		try {
			Dao<Receta, Integer> recetaDao = DBHelper.getHelper(context).getDao(Receta.class);
			recetas = recetaDao.queryForEq("categoria_id", idCategoria);
			recetas = filterUserSettings(context, recetas);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recetas;
	}
	
	/**
	 * Trae todos (getAll) las categorias de recetas de la base de datos.
	 * 
	 * @param helper El helper para manejar la base de datos.
	 * @param orderByColumnName El nombre de la columna por las que se ordenarán los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de categorias de recetas. La lista puede ser vacía en caso de que no haya registros.
	*/
	public static List<RecetaCategoria> getAllRecetasCategorias(Context context, String orderByColumnName, boolean orderByAsc){
		List<RecetaCategoria> recetaCategorias = new ArrayList<>();
		try {
			Dao<RecetaCategoria, Integer> dao = DBHelper.getHelper(context).getDao(RecetaCategoria.class);
			QueryBuilder<RecetaCategoria, Integer> qb = dao.queryBuilder().orderBy(orderByColumnName, orderByAsc); 
			recetaCategorias = qb.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recetaCategorias;
	}
	
	private  static List<Receta> filterUserSettings(Context context, List<Receta> recetas){
		
		UserSettings us = UserSettingsDBA.getUserSettings(context);
		Iterator<Receta> it = recetas.iterator();
		Receta r;
		if(us.isCheckBoxCeliaco() && us.isCheckBoxVegetariano()){
			
			while (it.hasNext()) {
			    r = it.next();
			    if(!r.isCeliaco() || !r.isVegetariano()) it.remove();
			}
		}
		else if(us.isCheckBoxCeliaco() && us.isCheckBoxVegano()){
			while (it.hasNext()) {
			    r = it.next();
			    if(!r.isCeliaco() || !r.isVegano()) it.remove();
			}
		}
		else if(us.isCheckBoxVegetariano()){
			while (it.hasNext()) {
			    r = it.next();
			    if(!r.isVegetariano()) it.remove();
			}
		}
		else if(us.isCheckBoxVegano()){
			while (it.hasNext()) {
			    r = it.next();
			    if(!r.isVegano()) it.remove();
			}
		}
		else if(us.isCheckBoxCeliaco()){
			while (it.hasNext()) {
			    r = it.next();
			    if(!r.isCeliaco()) it.remove();
			}
		}
		
		return recetas;
		
	}

}
