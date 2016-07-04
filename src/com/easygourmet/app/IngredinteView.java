package com.easygourmet.app;

import java.sql.SQLException;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.db.DBHelper;
import com.easygourmet.main.R;
import com.j256.ormlite.dao.Dao;

public class IngredinteView extends ActionBarActivity {

	private DBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredientes_vista);
		
		this.helper = new DBHelper(IngredinteView.this);
		
		//Obtengo parametro pasado como argumento a la activity
		Bundle b = getIntent().getExtras();
		Integer idIngrediente = b.getInt("idIngrediente");
		
		//INIT ACTIVITY
		initIngredienteVista(idIngrediente);
	}
	
	private Ingrediente initIngredienteVista(int idIngrediente){
		
		Ingrediente ingrediente = null;
		
		try {
			Dao<Ingrediente, Integer> ingredintesDao = helper.getDao(Ingrediente.class);
			ingrediente = ingredintesDao.queryForId(idIngrediente);
			
			TextView titulo = (TextView) findViewById(R.id.ingredinte_titulo);
			TextView ingredinte_tipo = (TextView) findViewById(R.id.ingediente_tipo);
			TextView kcal = (TextView) findViewById(R.id.kcal);
            
            titulo.setText(ingrediente.getNombre());
            ingredinte_tipo.setText(ingrediente.getTipo().getDescripcion());
            kcal.setText(Double.toString(ingrediente.getKcal()));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingrediente;
	}
}
