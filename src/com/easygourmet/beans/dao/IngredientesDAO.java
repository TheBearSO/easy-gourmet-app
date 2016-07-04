package com.easygourmet.beans.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.db.DBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class IngredientesDAO {
	
	/**
	 * Trae todos (getAll) los ingredientes de la base de datos.
	 * 
	 * @param helper El helper para manejar la base de datos.
	 * @param orderByColumnName El nombre de la columna por las que se ordenar�n los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de ingredientes. La lista puede ser vac�a en caso de que no haya registros.
	 */
	public static List<Ingrediente> getAllIntredientes(DBHelper helper, String orderByColumnName, boolean orderByAsc){
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		try {
			Dao<Ingrediente, Integer> ingredienteDao = helper.getDao(Ingrediente.class);
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
	 * @param orderByColumnName El nombre de la columna por las que se ordenar�n los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de ingredientes. La lista puede ser vac�a en caso de que no haya registros.
	*/
	public static List<Ingrediente> getAllIntredientesWhere(DBHelper helper, String orderByColumnName, boolean orderByAsc, boolean isVegetariano, boolean isVegano, boolean isCeliaco){
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		try {
			Dao<Ingrediente, Integer> ingredienteDao = helper.getDao(Ingrediente.class);
			QueryBuilder<Ingrediente, Integer> ingredientesQB = ingredienteDao.queryBuilder();
			
			System.out.println("VEGETARIANO: " + isVegetariano + " - VEGANO: " + isVegano + " - CELIACO: " + isCeliaco);
			
			if(isCeliaco && isVegetariano) ingredientesQB.where().eq(Ingrediente.FIELD_NAME_celiaco, true).and().eq(Ingrediente.FIELD_NAME_vegetariano, true);
			else if(isCeliaco && isVegano) ingredientesQB.where().eq(Ingrediente.FIELD_NAME_celiaco, true).and().eq(Ingrediente.FIELD_NAME_vegano, true);
			else if(isVegetariano) ingredientesQB.where().eq(Ingrediente.FIELD_NAME_vegetariano, true);
			else if(isVegano) ingredientesQB.where().eq(Ingrediente.FIELD_NAME_vegano, true);
			else if(isCeliaco) ingredientesQB.where().eq(Ingrediente.FIELD_NAME_celiaco, true);
			
			
			ingredientesQB.orderBy(orderByColumnName, orderByAsc); 
			ingredientes = ingredientesQB.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingredientes;
	}
}
