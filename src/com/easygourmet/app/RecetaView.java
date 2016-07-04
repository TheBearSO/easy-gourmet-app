package com.easygourmet.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.easygourmet.beans.Receta;
import com.easygourmet.beans.RecetaDetalle;
import com.easygourmet.beans.dao.RecetaDAO;
import com.easygourmet.db.DBHelper;
import com.easygourmet.main.R;
import com.easygourmet.ui.RecetaDetalleAdapter;
import com.easygourmet.utils.Utils;
import com.squareup.picasso.Picasso;

public class RecetaView extends ActionBarActivity {

	private DBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recetas_vista);
		
		Bundle b = getIntent().getExtras();
		int idReceta = b.getInt("idReceta");
		boolean recetaDelDia = b.getBoolean("recetaDelDia");
		
		this.helper = new DBHelper(RecetaView.this);
		
		Receta receta;
		if(!recetaDelDia){
			receta = RecetaDAO.getRecetaById(idReceta, this.helper);
		}else {
			receta = RecetaDAO.getRecetaDelDia(this.helper);
		}
		
		setRecetaToView(receta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recetas_vista, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setRecetaToView(Receta r){
		ScrollView sv = (ScrollView) findViewById(R.id.scrollView_activity_recetas_vista);
		
		System.out.println(r);
		
		ImageView receta_image = (ImageView) sv.findViewById(R.id.receta_image);
		String url = Utils.getColudinaryURL(String.valueOf(r.getIdReceta()), 100, 100);
		Picasso.with(getApplicationContext()).load(url).into(receta_image);
		
		TextView receta_nombre = (TextView) sv.findViewById(R.id.receta_nombre);
		
		TextView receta_categoria = (TextView) sv.findViewById(R.id.receta_categoria);
		//TextView receta_pais = (TextView) sv.findViewById(R.id.receta_pais);
		TextView receta_tiempo = (TextView) sv.findViewById(R.id.receta_tiempo);
		TextView receta_dificultad = (TextView) sv.findViewById(R.id.receta_dificultad);
		TextView receta_porciones = (TextView) sv.findViewById(R.id.receta_porciones);
		TextView receta_salud = (TextView) sv.findViewById(R.id.receta_salud);
		TextView receta_preparacion = (TextView) sv.findViewById(R.id.receta_preparacion);
		
		receta_nombre.setText(r.getNombre());
		receta_categoria.setText(r.getCategoria().getDescripcion());
		//receta_pais.setText(r.getPais().getNombre());
		receta_tiempo.setText(r.getTiempo());
		receta_dificultad.setText(r.getDificultad());
		receta_porciones.setText(Integer.toString(r.getPorciones()));
		receta_salud.setText(Integer.toString(r.getSalud()) + "% Sano");
		
		receta_preparacion.setText(r.getPreparacion());
		
		//lista de ingredinetes de la receta vista
		RecetaDetalleAdapter adapterRecetaDetalles = new RecetaDetalleAdapter(this, R.layout.list_recetas_detalles, new ArrayList<RecetaDetalle>(r.getDetalles()));
		
		LinearLayout layoutDetalles = (LinearLayout) findViewById(R.id.listViewDetalles);
		
		for (int i = 0; i < adapterRecetaDetalles.getCount(); i++) {
		     View view  = adapterRecetaDetalles.getView(i, null, null); 
		     layoutDetalles.addView(view);
		}
	}
	
}
