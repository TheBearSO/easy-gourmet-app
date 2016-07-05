package com.easygourmet.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.UserSettings;
import com.easygourmet.db.DBHelper;
import com.easygourmet.db.IngredientesDBA;
import com.easygourmet.db.UserSettingsDBA;
import com.easygourmet.main.R;
import com.easygourmet.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentTabNevera extends Fragment {
	
	private DBHelper helper;
	
	/** Es un adapter de {@link com.easygourmet.beans.Ingrediente}.
	 *  Se utiliza en la tab titulada "Que hay en la nevera?", es el adapter del autocomplete */
	private ArrayAdapter<Ingrediente> adapterIngredientesNevera;
	
	/** Es un adapter de {@link com.easygourmet.beans.RecetaCategoria}. 
	 * Se utiliza en la tab titulada "Que hay en la nevera?", debajo del autocomplete, 
	 * es el adapter de la lista de ingredientes elegidos por el usuario */
	private ArrayAdapter<Ingrediente> adapterIngredientesElegidos;
	
	/** Es la lista de Ingredientes que utiliza el adapter del autocomplete.  */
	private List<Ingrediente> ingredientesNevera;
	
	/** Es la listview de ingredientesElegidos. */
	private static ListView listViewNevera;
	
	/** Es el autocomplete de la  tab titulada "Que hay en la nevera?" */
	private AutoCompleteTextView autoCompleteIngredientes;
	
	/**Es la lista de ingredientes elegidos por el usuario */ 
	private static List<Ingrediente> ingredientesElegidos = new ArrayList<>();
	
	public FragmentTabNevera (){}
	
	public FragmentTabNevera (DBHelper helper){
		this.helper = helper;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tab_nevera, container, false);
		
		//Setea la letra customizada del titulo de la app
		TextView textView = (TextView) v.findViewById(R.id.tabNevera_titulo);
		Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
		textView.setTypeface(typeFace);
		
		//Obtengo los valores de los checkbox's en el layout/activity_menu_principal.xml
        UserSettings us = UserSettingsDBA.getUserSettings(getHelper(v));
        setCheckboxes(us, v);
        
        //AUTOCMPLETE
  		this.ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(
  			getHelper(v), Ingrediente.FIELD_NAME_nombre, true, us.isCheckBoxVegetariano(), us.isCheckBoxVegano(), us.isCheckBoxCeliaco()
  		);
		
		this.adapterIngredientesNevera = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes, R.id.nombre_ingrediente, this.ingredientesNevera);
		this.adapterIngredientesNevera.setNotifyOnChange(true);
		
		
		
		initNevera(v);
		
		return v;
	}
	
	
	/**
	 * Inicializa todos los elementos y eventos utilizados en la tab "Que hay en la nevera?".
	*/
	private void initNevera(View v){
		
		this.adapterIngredientesElegidos = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes_elegidos, R.id.nombre_ingrediente, ingredientesElegidos);
		this.adapterIngredientesElegidos.setNotifyOnChange(true);
		
		this.autoCompleteIngredientes = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteIngredientesNevera);
		listViewNevera = (ListView) v.findViewById(R.id.listViewNevera);
		
		listViewNevera.setAdapter(adapterIngredientesElegidos);
        Utils.setListViewHeightBasedOnChildren(listViewNevera);
		
        this.autoCompleteIngredientes.setAdapter(FragmentTabNevera.this.adapterIngredientesNevera);
        
        this.autoCompleteIngredientes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Ingrediente i = FragmentTabNevera.this.adapterIngredientesNevera.getItem(position);
				
				if(!ingredientesElegidos.contains(i)){
					ingredientesElegidos.add(i);
					FragmentTabNevera.this.adapterIngredientesElegidos.notifyDataSetChanged();
					Utils.setListViewHeightBasedOnChildren(listViewNevera);
				}
				FragmentTabNevera.this.autoCompleteIngredientes.setText("");
			}
		});
        
        listViewNevera.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapterIngredientesElegidos.remove(adapterIngredientesElegidos.getItem(position));
				Utils.setListViewHeightBasedOnChildren(listViewNevera);
			}
        });
        
        Button buttonNevera = (Button) v.findViewById(R.id.buttonNevera);
        buttonNevera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(ingredientesElegidos.size() > 0){
					//DBUtils.setUserSettings(FragmentTabNevera.this, FragmentTabNevera.this.helper);
					ArrayList<Integer> ingredinetesElegidos = new ArrayList<Integer>();
					for(Ingrediente ingrediente : ingredientesElegidos){
						ingredinetesElegidos.add(ingrediente.getIdIngrediente());
					}
				
					Intent i = new Intent(v.getContext(), Resultados.class);
					i.putIntegerArrayListExtra("ingredientesElegidos", ingredinetesElegidos);
					FragmentTabNevera.this.startActivity(i);
				}
			}
		});
        
        TextView buttonRecetaDelDia = (TextView) v.findViewById(R.id.buttonRecetaDelDia);
        buttonRecetaDelDia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//DBUtils.setUserSettings(FragmentTabNevera.this, FragmentTabNevera.this.helper);
				Intent i = new Intent(v.getContext(), RecetaView.class);
				i.putExtra("recetaDelDia", true);
				FragmentTabNevera.this.startActivity(i);
			}
		});
	}
	
	
	/**
	 * Inicializa los chekboxes del menu principal y sus eventos.
	 *
	 * @param us Recibe un objeto {@link com.easygourmet.beans.UserSettings} con los valores de los checkbox
	 * obtenidos desde la base de datos
	 */
	public void setCheckboxes(UserSettings us, final View v){
		
        CheckBox soyVegetariano = ((CheckBox) v.findViewById(R.id.checkBoxVegetariano));
        CheckBox soyVegano = ((CheckBox) v.findViewById(R.id.checkBoxVegano));
        CheckBox soyCeliaco = ((CheckBox) v.findViewById(R.id.checkBoxCeliaco));
        
        soyVegetariano.setChecked(us.isCheckBoxVegetariano());
        soyVegano.setChecked(us.isCheckBoxVegano());
        soyCeliaco.setChecked(us.isCheckBoxCeliaco());
        
        //SOY VEGETARIANO
        soyVegetariano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				int size = ingredientesElegidos.size();
				if(size > 0){
					Iterator<Ingrediente> it = ingredientesElegidos.iterator();
					while (it.hasNext()) {
					    Ingrediente i = it.next();
					    if(!i.isVegetariano()) it.remove();
					}
					
					adapterIngredientesElegidos = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes_elegidos, R.id.nombre_ingrediente, ingredientesElegidos);
					listViewNevera.setAdapter(adapterIngredientesElegidos);
					Utils.setListViewHeightBasedOnChildren(listViewNevera);
				}
				
				final DBHelper h = getHelper(v);
				final Activity a = getActivity();
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						UserSettings us = UserSettingsDBA.setUserSettings(a, h);
						FragmentTabNevera.this.ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(h, Ingrediente.FIELD_NAME_nombre, true, us.isCheckBoxVegetariano(), us.isCheckBoxVegano(), us.isCheckBoxCeliaco());

						return null;
					}
					
				}.execute();
				
						
				FragmentTabNevera.this.adapterIngredientesNevera = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes, R.id.nombre_ingrediente, FragmentTabNevera.this.ingredientesNevera);
				FragmentTabNevera.this.autoCompleteIngredientes.setAdapter(FragmentTabNevera.this.adapterIngredientesNevera);
			
			}
		});
        
        //SOY VEGANO
        soyVegano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				int size = ingredientesElegidos.size();
				if(size > 0){
					Iterator<Ingrediente> it = ingredientesElegidos.iterator();
					while (it.hasNext()) {
					    Ingrediente i = it.next();
					    if(!i.isVegano()) it.remove();
					}
					
					adapterIngredientesElegidos = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes_elegidos, R.id.nombre_ingrediente, ingredientesElegidos);
					listViewNevera.setAdapter(adapterIngredientesElegidos);
					Utils.setListViewHeightBasedOnChildren(listViewNevera);
				}
				
				final DBHelper h = getHelper(v);
				final Activity a = getActivity();
				
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						UserSettings us = UserSettingsDBA.setUserSettings(a, h);
						FragmentTabNevera.this.ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(h, Ingrediente.FIELD_NAME_nombre, true, us.isCheckBoxVegetariano(), us.isCheckBoxVegano(), us.isCheckBoxCeliaco());

						return null;
					}
					
				}.execute();
	
				FragmentTabNevera.this.adapterIngredientesNevera = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes, R.id.nombre_ingrediente, FragmentTabNevera.this.ingredientesNevera);
				FragmentTabNevera.this.autoCompleteIngredientes.setAdapter(FragmentTabNevera.this.adapterIngredientesNevera);
			}
		});
        
        //SOY CELIACO
        soyCeliaco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				int size = ingredientesElegidos.size();
				if(size > 0){
					Iterator<Ingrediente> it = ingredientesElegidos.iterator();
					while (it.hasNext()) {
					    Ingrediente i = it.next();
					    if(!i.isCeliaco()) it.remove();
					}
					adapterIngredientesElegidos = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes_elegidos, R.id.nombre_ingrediente, ingredientesElegidos);
					listViewNevera.setAdapter(adapterIngredientesElegidos);
					Utils.setListViewHeightBasedOnChildren(listViewNevera);
				}
				
				final DBHelper h = getHelper(v);
				final Activity a = getActivity();
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						UserSettings us = UserSettingsDBA.setUserSettings(a, h);
						FragmentTabNevera.this.ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(h, Ingrediente.FIELD_NAME_nombre, true, us.isCheckBoxVegetariano(), us.isCheckBoxVegano(), us.isCheckBoxCeliaco());

						return null;
					}
					
				}.execute();
				
				FragmentTabNevera.this.adapterIngredientesNevera = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_ingredientes, R.id.nombre_ingrediente, FragmentTabNevera.this.ingredientesNevera);
				FragmentTabNevera.this.autoCompleteIngredientes.setAdapter(FragmentTabNevera.this.adapterIngredientesNevera);
			}
		});
	}
	
	/**
	 * Devuelve el helper para trabajar sobre la base de datos.
	 *
	 * @return El helper
	*/
	public DBHelper getHelper(View v) {
		if(this.helper == null){
			this.helper = new DBHelper(v.getContext());
		}
		return this.helper;
	}
}
