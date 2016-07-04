package com.easygourmet.beans.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easygourmet.beans.RecetaCategoria;
import com.easygourmet.db.DBHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class RecetaCategoriaDAO {
	
	/**
	 * Trae todos (getAll) las categorias de recetas de la base de datos.
	 * 
	 * @param helper El helper para manejar la base de datos.
	 * @param orderByColumnName El nombre de la columna por las que se ordenarán los ingredientes.
	 * @param orderByAsc Si es true, el orden es ascendente, si es false, el orden es descendente. 
	 * @return Una lista de categorias de recetas. La lista puede ser vacía en caso de que no haya registros.
	*/
	public static List<RecetaCategoria> getAllRecetasCategorias(DBHelper helper, String orderByColumnName, boolean orderByAsc){
		List<RecetaCategoria> recetaCategorias = new ArrayList<>();
		try {
			Dao<RecetaCategoria, Integer> dao = helper.getDao(RecetaCategoria.class);
			QueryBuilder<RecetaCategoria, Integer> qb = dao.queryBuilder().orderBy(orderByColumnName, orderByAsc); 
			recetaCategorias = qb.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recetaCategorias;
	}
}
