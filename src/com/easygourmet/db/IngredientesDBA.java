package com.easygourmet.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.easygourmet.beans.Ingrediente;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class IngredientesDBA {
	
	/**
	 * Trae todos (getAll) los ingredientes de la base de datos.
	 * 
	 * @param helper El helper para manejar la base de datos.
	 * @param orderByColumnName El nombre de la columna por las que se ordenarán los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de ingredientes. La lista puede ser vacía en caso de que no haya registros.
	 */
	public static List<Ingrediente> getAllIntredientes(Context context, String orderByColumnName, boolean orderByAsc){
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		try {
			Dao<Ingrediente, Integer> ingredienteDao = DBHelper.getHelper(context).getDao(Ingrediente.class);
			QueryBuilder<Ingrediente, Integer> ingredientesQB = ingredienteDao.queryBuilder().orderBy(orderByColumnName, orderByAsc); 
			ingredientes = ingredientesQB.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingredientes;
	}
	
	/**
	 * Trae todos (getAll) los ingredientes de la base de datos.
	 * 
	 * @param helper El helper para manejar la base de datos.
	 * @param orderByColumnName El nombre de la columna por las que se ordenarán los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de ingredientes. La lista puede ser vacía en caso de que no haya registros.
	*/
	//TODO: Hacer con SQL puro
	public static List<Ingrediente> getAllIntredientesWhere(Context context, String orderByColumnName, boolean orderByAsc, boolean isVegetariano, boolean isVegano, boolean isCeliaco){
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		try {
			Dao<Ingrediente, Integer> ingredienteDao = DBHelper.getHelper(context).getDao(Ingrediente.class);
			QueryBuilder<Ingrediente, Integer> qb = ingredienteDao.queryBuilder();
			
			System.out.println("VEGETARIANO: " + isVegetariano + " - VEGANO: " + isVegano + " - CELIACO: " + isCeliaco);
			
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
			
			qb.orderBy(orderByColumnName, orderByAsc); 
			ingredientes = qb.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingredientes;
	}
}
