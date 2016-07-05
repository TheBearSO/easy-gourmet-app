package com.easygourmet.app;

import java.util.List;

import com.easygourmet.beans.RecetaCategoria;
import com.easygourmet.db.DBHelper;
import com.easygourmet.db.RecetaCategoriaDBA;
import com.easygourmet.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentTabRecetas extends Fragment {
	
	private DBHelper helper;
	
	/** Es un adapter de {@link com.easygourmet.beans.RecetaCategoria}. Se utiliza en la tab titulada "Recetas" */
	private ArrayAdapter<RecetaCategoria> adapterRecetaCategorias;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_tab_recetas, container, false);
		
		this.helper = new DBHelper(v.getContext());
		
		initRecetasCategorias(v);
		
		return v;
	}
	
	/**
	  * Inicializa todos los elementos y eventos utilizados en la tab "Recetas".
	 */
	private void initRecetasCategorias(View v){
		
		List<RecetaCategoria> recetaCategorias = RecetaCategoriaDBA.getAllRecetasCategorias(this.helper, RecetaCategoria.FIELD_NAME_descripcion, true);
		this.adapterRecetaCategorias = new ArrayAdapter<RecetaCategoria>(v.getContext(),  R.layout.list_recetas_categorias, R.id.descripcion, recetaCategorias);
		ListView listViewCategorias = (ListView) v.findViewById(R.id.listViewRecetasCategorias);
		listViewCategorias.setAdapter(adapterRecetaCategorias);
		
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RecetaCategoria rc = FragmentTabRecetas.this.adapterRecetaCategorias.getItem(position);
				Intent i = new Intent(getActivity(), Resultados.class);
				i.putExtra("idCategoria", rc.getId());
				FragmentTabRecetas.this.startActivity(i);
			}
	    });
	}
}
