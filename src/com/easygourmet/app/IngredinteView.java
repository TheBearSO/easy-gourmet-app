package com.easygourmet.app;

import java.sql.SQLException;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.Receta;
import com.easygourmet.db.DBHelper;
import com.easygourmet.db.RecetaDBA;
import com.easygourmet.main.R;
import com.easygourmet.ui.ResultadosAdapter;
import com.j256.ormlite.dao.Dao;

public class IngredinteView extends ActionBarActivity {
	
	private ResultadosAdapter adapterRecetas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredientes_vista);
		

		overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		
		//Obtengo parametro pasado como argumento a la activity
		Bundle b = getIntent().getExtras();
		Integer idIngrediente = b.getInt("idIngrediente");
		
		//INIT ACTIVITY
		initIngredienteVista(idIngrediente);
	}
	
	private Ingrediente initIngredienteVista(final int idIngrediente){
		
		Ingrediente ingrediente = null;
		
		try {
			Dao<Ingrediente, Integer> ingredintesDao = DBHelper.getHelper(IngredinteView.this).getDao(Ingrediente.class);
			ingrediente = ingredintesDao.queryForId(idIngrediente);
			
			TextView titulo = (TextView) findViewById(R.id.ingredinte_titulo);
			
			TextView ingredinte_tipo = (TextView) findViewById(R.id.ingediente_tipo);
			TextView kcal = (TextView) findViewById(R.id.kcal);
			TextView salud = (TextView) findViewById(R.id.salud);
            
            titulo.setText(ingrediente.getNombre());
            
            ingredinte_tipo.setText(ingrediente.getTipo().getDescripcion());
            kcal.setText(Double.toString(ingrediente.getKcal()));
            //TODO: implementar salud en la base de datos
            salud.setText("75%");
            
            TextView tituloRecetaCon = (TextView) findViewById(R.id.recetas_con);
            tituloRecetaCon.setText("Recetas con " + ingrediente.getNombre());
            
            List<Receta> recetas = RecetaDBA.getRecetasByIngrediente(IngredinteView.this, idIngrediente);
    		this.adapterRecetas = new ResultadosAdapter(IngredinteView.this,  R.layout.list_recetas, recetas);
    		ListView listaRecetas = (ListView) findViewById(R.id.recetasi);
    		listaRecetas.setAdapter(this.adapterRecetas);
    		
    		listaRecetas.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    				Receta r = adapterRecetas.getItem(position);
    				
    				Intent i = new Intent(IngredinteView.this, RecetaView.class);
    				i.putExtra("idReceta", r.getIdReceta());
    				IngredinteView.this.startActivity(i);
    			}
    			
    		});
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingrediente;
	}
}
