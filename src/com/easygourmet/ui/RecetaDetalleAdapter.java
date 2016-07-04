package com.easygourmet.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.easygourmet.beans.RecetaDetalle;
import com.easygourmet.main.R;

/**
 * Custom adapter de la clase {@link com.easygourmet.beans.RecetaDetalle}.
 */
public class RecetaDetalleAdapter extends ArrayAdapter<RecetaDetalle>{

	/**
	 * El contexto de la aplicación
	 */
	private final Context context;
    
	/**
     * La lista de recetas
     */
	private final List<RecetaDetalle> recetas;
    
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
	public RecetaDetalleAdapter(Context context, int resource, List<RecetaDetalle> recetas) {
		
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
        View row = convertView;
        List_RecetasDetalleXML list_recetas = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            list_recetas = new List_RecetasDetalleXML();
            list_recetas.list_recetas_detalles_cantidad = (TextView) row.findViewById(R.id.list_recetas_detalles_cantidad);
            list_recetas.list_recetas_detalles_nombreIngrediente = (TextView) row.findViewById(R.id.list_recetas_detalles_nombreIngrediente);
            
            row.setTag(list_recetas);
        }
        else
        {
            list_recetas = (List_RecetasDetalleXML)row.getTag();
        }

        RecetaDetalle r = recetas.get(position);

        String cantidad = Integer.toString((int) r.getCantidad()) + " " + r.getUnidadMedida() + ".";
        list_recetas.list_recetas_detalles_cantidad.setText(cantidad);
        list_recetas.list_recetas_detalles_nombreIngrediente.setText(r.getIngrediente().getNombre());
        
        return row;
    }

	/**
     * Clase estática utilizada para mapear los elementos del 'list_recetas_detalle.xml' ordenadamente.
    */
    static class List_RecetasDetalleXML
    {
        TextView list_recetas_detalles_cantidad;
        TextView list_recetas_detalles_nombreIngrediente;
    }

}
