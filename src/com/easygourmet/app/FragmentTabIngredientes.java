package com.easygourmet.app;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.dao.IngredientesDAO;
import com.easygourmet.db.DBHelper;
import com.easygourmet.main.R;

public class FragmentTabIngredientes extends Fragment {
	
	private DBHelper helper;
	
	/** Es un adapter de {@link com.easygourmet.beans.Ingrediente}. Se utiliza en la tab titulada "Ingrtedientes"*/	
	private ArrayAdapter<Ingrediente> adapterIngredientes;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tab_ingredientes, container, false);
		
		this.helper = new DBHelper(v.getContext());
		
		//TABS INGREDIENTES
		List<Ingrediente> ingredientes = IngredientesDAO.getAllIntredientes(this.helper, Ingrediente.FIELD_NAME_nombre, true);
		this.adapterIngredientes = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes, R.id.nombre_ingrediente, ingredientes);
		
		initIngredintes(v);
		
		return v;
	}
	
	/**
	  * Inicializa todos los elementos y eventos utilizados en la tab "Ingredientes".
	 */
	private void initIngredintes(View v){
			
		//Buscar Ingredintes...
		EditText inputSearch = (EditText) v.findViewById(R.id.inputSearch);
       inputSearch.addTextChangedListener(new TextWatcher() {
           
           @Override
           public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
        	   FragmentTabIngredientes.this.adapterIngredientes.getFilter().filter(cs);   
           }
            
           @Override
           public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            
           @Override
           public void afterTextChanged(Editable arg0) {}
       });
       
       //ListView de Ingredintes
       ListView listViewIngredintes = (ListView) v.findViewById(R.id.listViewIngredintes);
       listViewIngredintes.setAdapter(this.adapterIngredientes);
		
       listViewIngredintes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Ingrediente ingredinete = FragmentTabIngredientes.this.adapterIngredientes.getItem(position);
				Intent i = new Intent(getActivity(), IngredinteView.class);
				i.putExtra("idIngrediente", ingredinete.getIdIngrediente());
				FragmentTabIngredientes.this.startActivity(i);
			}
	    });
	}
}