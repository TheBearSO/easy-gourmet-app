package com.easygourmet.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.easygourmet.beans.Receta;
import com.easygourmet.beans.RecetaDetalle;
import com.easygourmet.db.RecetaDBA;
import com.easygourmet.main.R;
import com.easygourmet.ui.RecetaDetalleAdapter;
import com.easygourmet.utils.Utils;

public class RecetaView extends Activity {
	
	private RecetaDetalleAdapter adapterRecetaDetalles;
	
	private List<RecetaDetalle> detalles;
	
	private ListView listViewDetalles;
	
	private int procionesIniciales;
	
	private int porciones;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recetas_vista);
		

		overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		
		Bundle b = getIntent().getExtras();
		int idReceta = b.getInt("idReceta");
		boolean recetaDelDia = b.getBoolean("recetaDelDia");
		
		Receta receta;
		if(!recetaDelDia){
			receta = RecetaDBA.getRecetaById(idReceta, RecetaView.this);
		}else {
			receta = RecetaDBA.getRecetaDelDia(RecetaView.this);
		}
		
		setRecetaToView(receta);
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.recetas_vista, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	private void setRecetaToView(Receta r){
		ScrollView sv = (ScrollView) findViewById(R.id.scrollView_activity_recetas_vista);
		
		final ImageView receta_image = (ImageView) sv.findViewById(R.id.receta_image);
		Utils.loadImage(getApplicationContext(), String.valueOf(r.getIdReceta()), 400, 300, receta_image, R.drawable.load_placeholder);
	
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
		
		porciones = procionesIniciales = r.getPorciones();
		receta_porciones.setText(Integer.toString(porciones));
		
		receta_salud.setText(Integer.toString(r.getSalud()) + "% Sano");
		
		receta_preparacion.setText(r.getPreparacion());
		
		//lista de ingredinetes de la receta vista
		this.detalles = new ArrayList<>(r.getDetalles());
		adapterRecetaDetalles = new RecetaDetalleAdapter(this, R.layout.list_recetas_detalles, new ArrayList<>(r.getDetalles()));
		
		listViewDetalles = (ListView) findViewById(R.id.listViewDetalles);
		listViewDetalles.setAdapter(adapterRecetaDetalles);
		Utils.setListViewHeightBasedOnChildren(listViewDetalles);
		
		listViewDetalles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RecetaDetalle r = adapterRecetaDetalles.getItem(position);
				
				Intent i = new Intent(RecetaView.this, IngredinteView.class);
				i.putExtra("idIngrediente", r.getIngrediente().getIdIngrediente());
				RecetaView.this.startActivity(i);
				
			}
		});
		
		Button plus = (Button) findViewById(R.id.recetas_vista_button_porciones_plus);
		plus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(porciones == 20) {
					return;
				}
				TextView receta_porciones = (TextView) findViewById(R.id.receta_porciones);
				porciones++;
				receta_porciones.setText(Integer.toString(porciones));
				calcuadorProciones();
			}
		});
		
		Button minus = (Button) findViewById(R.id.recetas_vista_button_porciones_minus);
		minus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(porciones == 1) {
					return;
				}
				TextView receta_porciones = (TextView) findViewById(R.id.receta_porciones);
				porciones--;
				calcuadorProciones();
				receta_porciones.setText(Integer.toString(porciones));
			}
		});
		
	}
	
	private void calcuadorProciones(){
		//TODO: Refactorizar esto
		List<RecetaDetalle> list = new ArrayList<>(this.detalles.size());
		for(RecetaDetalle r: this.detalles) list.add(new RecetaDetalle(r));
		
		float cantidad;
		for(RecetaDetalle rd : list){
			cantidad = ( (porciones * rd.getCantidad()) / procionesIniciales);
			cantidad = Math.round(cantidad);
			if(cantidad == 0){
				cantidad = 1;
			}
			rd.setCantidad(cantidad);
		}
		
		adapterRecetaDetalles = new RecetaDetalleAdapter(this, R.layout.list_recetas_detalles, list);
		listViewDetalles.setAdapter(adapterRecetaDetalles);
	}
	
}
