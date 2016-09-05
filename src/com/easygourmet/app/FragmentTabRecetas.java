package com.easygourmet.app;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.easygourmet.beans.Receta;
import com.easygourmet.db.RecetaDBA;
import com.easygourmet.main.R;
import com.easygourmet.ui.ResultadosAdapter;

public class FragmentTabRecetas extends Fragment {
	
	/** Es un adapter que se utiliza en la tab titulada "Recetas" */
	private ResultadosAdapter adapterRecetas;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tab_recetas, container, false);
		
		initRecetas(v);
		
		return v;
	}
	
	/**
	  * Inicializa todos los elementos y eventos utilizados en la tab "Recetas".
	 */
	private void initRecetas(View v){
		
		List<Receta> recetas = RecetaDBA.getAllWhere(v.getContext());
		this.adapterRecetas = new ResultadosAdapter(v.getContext(),  R.layout.list_recetas, recetas);
		ListView listaRecetas = (ListView) v.findViewById(R.id.recetas);
		listaRecetas.setAdapter(this.adapterRecetas);
		
		listaRecetas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Receta r = adapterRecetas.getItem(position);
				
				Intent i = new Intent(getActivity(), RecetaView.class);
				i.putExtra("idReceta", r.getIdReceta());
				FragmentTabRecetas.this.startActivity(i);
			}
			
		});
	}
}
