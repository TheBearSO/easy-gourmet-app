package com.easygourmet.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.easygourmet.beans.Usuario;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class UsuariosDBA {
	
	public static List<Usuario> getAllUsuarios(Context context, String orderByColumnName, boolean orderByAsc) {

		List<Usuario> usuarios = new ArrayList<>();
		try {
			
			Dao<Usuario, Integer> usuarioDao = DBHelper.getHelper(context).getDao(Usuario.class);
			QueryBuilder<Usuario, Integer> usuarioQB = usuarioDao.queryBuilder().orderBy(orderByColumnName, orderByAsc);
			usuarios = usuarioQB.query();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usuarios;
	}
}
