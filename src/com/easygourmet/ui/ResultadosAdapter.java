package com.easygourmet.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygourmet.beans.Receta;
import com.easygourmet.main.R;
import com.easygourmet.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Custom adapter de la clase {@link com.easygourmet.beans.Receta}.
 */
public class ResultadosAdapter extends ArrayAdapter<Receta>{

	/**
	 * El contexto de la aplicación
	 */
	private final Context context;
    
	/**
     * La lista de recetas
     */
    private final List<Receta> recetas;
    
    /**
     * El id del resource
     */
    private final int layoutResourceId;
    
	
    /**
     * Constructor. Hace un super a la clase de la que hereda {@link android.widget.ArrayAdapter}. Además setea los campos 
     * final <code>context</code>, <code>recetas</code>, <code>layoutResourceId</code>.
     * 
     * @param context El contexto de la aplicación
     * @param resource El id del resource
     * @param recetas La lista de recetas
     */
    public ResultadosAdapter(Context context, int resource,
			List<Receta> recetas) {
		
		super(context, resource, recetas);
		
		this.context = context;
	    this.recetas = recetas;
	    this.layoutResourceId = resource;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        List_RecetasXML list_recetas = null;
        TextView textViewSalud = null;

        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(layoutResourceId, parent, false);
            
            view.setTag(list_recetas);
            
        }
        
        Receta r = recetas.get(position);
        
        if(r != null){
	        list_recetas = new List_RecetasXML();
	        list_recetas.resultados_image = (ImageView) view.findViewById(R.id.resultados_image);
	        list_recetas.nombre = (TextView) view.findViewById(R.id.nombre);
	        list_recetas.descripcion = (TextView) view.findViewById(R.id.descripcion);
	        list_recetas.tiempo = (TextView) view.findViewById(R.id.tiempo);
	        list_recetas.dificultad = (TextView) view.findViewById(R.id.dificultad);
	        list_recetas.porciones = (TextView) view.findViewById(R.id.porciones);
	        
	        //textViewSalud = (TextView) view.findViewById(R.id.salud);;
	        list_recetas.salud = textViewSalud;
	        
	        Picasso.with(getContext()).load(Utils.getColudinaryURL(String.valueOf(r.getIdReceta()), 150, 150)).into(list_recetas.resultados_image);
	        
	        list_recetas.nombre.setText(r.getNombre());
	        list_recetas.descripcion.setText(r.getDescripcion());
	        list_recetas.tiempo.setText(r.getTiempo());
	        list_recetas.dificultad.setText(r.getDificultad());
	        list_recetas.porciones.setText(Integer.toString(r.getPorciones()));
	        
//	        if(textViewSalud != null){
//	        	int rojo = Color.rgb(178,34,34);
//	        	int amarillo = Color.rgb(190, 165, 0);
//	        	int verde = Color.rgb(0,100,0);
//	        	
//	        	int salud = r.getSalud();
//	        	if(salud > 67){
//	        		textViewSalud.setTextColor(verde);
//	        		list_recetas.salud.setText(salud + "% Sano");
//	        	}else if(salud > 33 && salud <= 67){
//	        		textViewSalud.setTextColor(amarillo);
//	        		list_recetas.salud.setText(salud + "% Sano");
//	        	}else{
//	        		textViewSalud.setTextColor(rojo);
//	        		list_recetas.salud.setText(salud + "% Sano");
//	        	}
//	        	
//	        }
        }
        
        return view;
    }

    /**
     * Clase estática utilizada para mapear los elementos del 'list_recetas.xml' ordenadamente.
    */
    static class List_RecetasXML
    {
    	ImageView resultados_image;
        TextView nombre;
        TextView descripcion;
        TextView tiempo;
        TextView dificultad;
        TextView porciones;
        TextView salud;
    }
	
}
