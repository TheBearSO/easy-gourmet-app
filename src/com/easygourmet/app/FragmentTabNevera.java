package com.easygourmet.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easygourmet.beans.Ingrediente;
import com.easygourmet.beans.UserSettings;
import com.easygourmet.db.IngredientesDBA;
import com.easygourmet.db.UserSettingsDBA;
import com.easygourmet.main.R;
import com.easygourmet.ui.RowLayout;
import com.easygourmet.utils.Utils;

public class FragmentTabNevera extends Fragment {
	
	/** Es un adapter de {@link com.easygourmet.beans.Ingrediente}.
	 *  Se utiliza en la tab titulada "Que hay en la nevera?", es el adapter del autocomplete */
	private ArrayAdapter<Ingrediente> adapterAutocompleteIngredientesNevera;
	
	/** Es la lista de Ingredientes que utiliza el adapter del autocomplete.  */
	private List<Ingrediente> ingredientesNevera;
	
	/** Es el autocomplete de la  tab titulada "Que hay en la nevera?" */
	private AutoCompleteTextView autoCompleteIngredientes;
	
	/** Es el layout que contiene los labels de ingredientesElegidos. */
	private RowLayout layoutIngredientesElegidosNevera;
	
	private List<Ingrediente> ingredientesElegidos = new ArrayList<>();
	
	private CheckBox soyVegetariano;
	private CheckBox soyVegano;
	private CheckBox soyCeliaco;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_tab_nevera, container, false);
		
		//Setea la letra customizada del titulo de la app
		TextView textView = (TextView) v.findViewById(R.id.tabNevera_titulo);
		Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico.ttf");
		textView.setTypeface(typeFace);
		
		//Obtengo los valores de los checkbox's en el layout/activity_menu_principal.xml
       
        this.soyVegetariano = ((CheckBox) v.findViewById(R.id.checkBoxVegetariano));
    	this.soyVegano = ((CheckBox) v.findViewById(R.id.checkBoxVegano));
    	this.soyCeliaco = ((CheckBox) v.findViewById(R.id.checkBoxCeliaco));
    	UserSettings us = UserSettingsDBA.getUserSettings(v.getContext());
        initCheckboxes(us, v);
        
        //AUTOCMPLETE
  		this.ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(
  			v.getContext(), Ingrediente.FIELD_NAME_nombre, true, us.isCheckBoxVegetariano(), us.isCheckBoxVegano(), us.isCheckBoxCeliaco()
  		);
  		
  		//layoutIngredientesElegidosNevera
  		this.layoutIngredientesElegidosNevera = (RowLayout) v.findViewById(R.id.layoutIngredientesElegidosNevera);
		
		
  		this.adapterAutocompleteIngredientesNevera = new ArrayAdapter<Ingrediente>(v.getContext(),  R.layout.list_autocomplete, R.id.nombre_ingrediente, this.ingredientesNevera);
		this.adapterAutocompleteIngredientesNevera.setNotifyOnChange(true);
		
		
		
		initNevera(v);
		
		return v;
	}
	
	
	/**
	 * Inicializa todos los elementos y eventos utilizados en la tab "Que hay en la nevera?".
	*/
	private void initNevera(View v){
		
		this.autoCompleteIngredientes = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteIngredientesNevera);
		
        this.autoCompleteIngredientes.setAdapter(FragmentTabNevera.this.adapterAutocompleteIngredientesNevera);
        
        this.autoCompleteIngredientes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Ingrediente ingrediente = FragmentTabNevera.this.adapterAutocompleteIngredientesNevera.getItem(position);
				FragmentTabNevera.this.autoCompleteIngredientes.setText("");
				
				for (Ingrediente i : ingredientesElegidos) {
					if(ingrediente.getIdIngrediente() == i.getIdIngrediente()){
						return;
					}
				}
				
				Utils.hideKeyboard(getActivity());
				
				
				ingredientesElegidos.add(ingrediente);
				TextView tv = getNewTextView(ingrediente);
				layoutIngredientesElegidosNevera.addView(tv);
				tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						layoutIngredientesElegidosNevera.removeView(v);
						int j = 0;
						for (Ingrediente i : ingredientesElegidos) {
							if(v.getId() == i.getIdIngrediente()){
								ingredientesElegidos.remove(j);
								break;
							}
							j++;
						}
					}
				});
			}
		});
        
        Button buttonNevera = (Button) v.findViewById(R.id.buttonNevera);
        buttonNevera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//if(ingredientesElegidos.size() > 0){
					//DBUtils.setUserSettings(FragmentTabNevera.this, FragmentTabNevera.this.helper);
					ArrayList<Integer> ingredinetesElegidos = new ArrayList<Integer>();
					for(Ingrediente ingrediente : ingredientesElegidos){
						ingredinetesElegidos.add(ingrediente.getIdIngrediente());
					}
				
					Intent i = new Intent(v.getContext(), Resultados.class);
					i.putIntegerArrayListExtra("ingredientesElegidos", ingredinetesElegidos);
					FragmentTabNevera.this.startActivity(i);
				//}
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
	
	private TextView getNewTextView(Ingrediente i){
		TextView tv = new TextView(getActivity());
		tv.setId(i.getIdIngrediente());
		tv.setText(i.getNombre());
		tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cross, 0);
		tv.setCompoundDrawablePadding(5);
		tv.setPadding(12, 8, 12, 8);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setBackgroundColor(Color.parseColor("#dddddd"));
		
		return tv;
	}
	
	/**
	 * Inicializa los chekboxes del menu principal y sus eventos.
	 *
	 * @param us Recibe un objeto {@link com.easygourmet.beans.UserSettings} con los valores de los checkbox
	 * obtenidos desde la base de datos
	 */
	public void initCheckboxes(UserSettings us, final View v){
		
		//SETEO INICIAL
        soyVegetariano.setChecked(us.isCheckBoxVegetariano());
        soyVegano.setChecked(us.isCheckBoxVegano());
        soyCeliaco.setChecked(us.isCheckBoxCeliaco());
        
        //EVENTOS de CLICK de los checkboxes
        
        //SOY VEGETARIANO
        soyVegetariano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        	@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checkboxClick(v, 
						soyVegetariano.isChecked(), 
						soyVegano.isChecked(), 
						soyCeliaco.isChecked());
			}
		});
        
        //SOY VEGANO
        soyVegano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checkboxClick(v, 
						soyVegetariano.isChecked(), 
						soyVegano.isChecked(), 
						soyCeliaco.isChecked());
			}
		});
        
        //SOY CELIACO
        soyCeliaco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checkboxClick(v, 
						soyVegetariano.isChecked(), 
						soyVegano.isChecked(), 
						soyCeliaco.isChecked());
			}
		});
	}
	
	private void checkboxClick(final View v, final boolean soyVegetariano, final boolean soyVegano, final boolean soyCeliaco){
		
		//Remuevo los ingredientes de la lista de los elegidos por el usuario segun corresponda
		if(ingredientesElegidos.size() > 0){
			Iterator<Ingrediente> it = ingredientesElegidos.iterator();
			Ingrediente i;
			TextView tv;
			boolean remove;
			while (it.hasNext()) {
				i = it.next();
				remove = (soyVegetariano && !i.isVegetariano()) || 
						 (soyVegano && !i.isVegano()) 			|| 
						 (soyCeliaco && !i.isCeliaco());
				
	    		if(remove){
			    	it.remove();
			    	tv = (TextView) v.findViewById(i.getIdIngrediente());
			    	layoutIngredientesElegidosNevera.removeView(tv);
	    		}
	    	}
			    
		}
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				UserSettingsDBA.setUserSettings (v.getContext(), soyVegetariano, soyVegano, soyCeliaco);
				
				adapterAutocompleteIngredientesNevera.clear();
				ingredientesNevera = IngredientesDBA.getAllIntredientesWhere(v.getContext(), Ingrediente.FIELD_NAME_nombre, true, soyVegetariano, soyVegano, soyCeliaco);
				adapterAutocompleteIngredientesNevera.addAll(ingredientesNevera);
			    adapterAutocompleteIngredientesNevera.notifyDataSetChanged();
			    
				autoCompleteIngredientes.setAdapter(adapterAutocompleteIngredientesNevera);
				
				return null;
			}
			
		}.execute();
	}
	
}
