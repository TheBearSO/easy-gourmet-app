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
import android.widget.EditText;
import android.widget.ListView;

import com.easygourmet.beans.Usuario;
import com.easygourmet.db.UsuariosDBA;
import com.easygourmet.main.R;
import com.easygourmet.ui.UsuariosAdapter;

public class FragmentTabUsuarios extends Fragment {
	
	private UsuariosAdapter usuariosAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tab_usuarios, container, false);
		
		initUsuarios(v);
		
		return v;
	}
	
	/**
	  * Inicializa todos los elementos y eventos utilizados en la tab "Ingredientes".
	 */
	private void initUsuarios(View v){
		
		ListView listViewIngredintes = (ListView) v.findViewById(R.id.listViewUsuarios);
		List<Usuario> usuarios = UsuariosDBA.getAllUsuarios(v.getContext(), Usuario.FIELD_NAME_username, true);
		this.usuariosAdapter = new UsuariosAdapter(v.getContext(), R.layout.list_usuarios, usuarios);
		listViewIngredintes.setAdapter(this.usuariosAdapter);
		
		listViewIngredintes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Usuario usuario = FragmentTabUsuarios.this.usuariosAdapter.getItem(position);
				Intent i = new Intent(getActivity(), UsuarioView.class);
				i.putExtra("id", usuario.getId());
				i.putExtra("username", usuario.getUsername());
				FragmentTabUsuarios.this.startActivity(i);
			}
	    });
		
		//Buscar Ingredintes...
		EditText inputSearch = (EditText) v.findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {
           
           @Override
           public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
        	   FragmentTabUsuarios.this.usuariosAdapter.getFilter().filter(cs);   
           }
            
           @Override
           public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            
           @Override
           public void afterTextChanged(Editable arg0) {}
		});
	}
}