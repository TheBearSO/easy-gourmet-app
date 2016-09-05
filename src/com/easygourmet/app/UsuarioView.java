package com.easygourmet.app;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygourmet.beans.Usuario;
import com.easygourmet.db.DBHelper;
import com.easygourmet.main.R;
import com.easygourmet.ui.ResultadosAdapter;
import com.easygourmet.utils.Utils;
import com.j256.ormlite.dao.Dao;

public class UsuarioView extends Activity {
	
	private ResultadosAdapter adapterRecetas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuarios_vista);
		

		overridePendingTransition(R.drawable.slide_in, R.drawable.slide_out);
		
		//Obtengo parametro pasado como argumento a la activity
		Bundle b = getIntent().getExtras();
		Integer id = b.getInt("id");
		String username = b.getString("username");
		
		//INIT ACTIVITY
		initUsuarioVista(id);
	}
	
	private Usuario initUsuarioVista(final int id){
		
		Usuario usuario = null;
		
		try {
			Dao<Usuario, Integer> usuariosDao = DBHelper.getHelper(UsuarioView.this).getDao(Usuario.class);
			usuario = usuariosDao.queryForId(id);
			
			ImageView image = (ImageView) findViewById(R.id.usuarios_image);
			TextView username = (TextView) findViewById(R.id.usuarios_username);
			TextView descripcion = (TextView) findViewById(R.id.usuarios_descripcion);
			TextView url = (TextView) findViewById(R.id.usuarios_url);
            
			String imageName = "usuarios/" + usuario.getUsername();
			Utils.loadImage(this, imageName, 100, 100, image, R.drawable.load_placeholder);
            username.setText(usuario.getUsername());
            descripcion.setText(usuario.getDescripcion());
            url.setText(usuario.getUrl());
            
//            TextView tituloRecetaCon = (TextView) findViewById(R.id.recetas_con);
//            tituloRecetaCon.setText("Recetas con " + ingrediente.getNombre());
            
//            List<Receta> recetas = RecetaDBA.getRecetasByIngrediente(UsuarioView.this, idIngrediente);
//    		this.adapterRecetas = new ResultadosAdapter(UsuarioView.this,  R.layout.list_recetas_small, recetas);
//    		ListView listaRecetas = (ListView) findViewById(R.id.recetasi);
//    		listaRecetas.setAdapter(this.adapterRecetas);
//    		
//    		listaRecetas.setOnItemClickListener(new OnItemClickListener() {
//
//    			@Override
//    			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//    				Receta r = adapterRecetas.getItem(position);
//    				
//    				Intent i = new Intent(UsuarioView.this, RecetaView.class);
//    				i.putExtra("idReceta", r.getIdReceta());
//    				UsuarioView.this.startActivity(i);
//    			}
//    			
//    		});
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
}
