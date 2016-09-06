package com.easygourmet.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygourmet.beans.Receta;
import com.easygourmet.beans.Usuario;
import com.easygourmet.main.R;
import com.easygourmet.utils.ImageUtils;
import com.easygourmet.utils.Utils;

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
    public ResultadosAdapter(Context context, int resource, List<Receta> recetas) {
		
		super(context, resource, recetas);
		
		this.context = context;
	    this.recetas = recetas;
	    this.layoutResourceId = resource;
	    
	    int size = Math.min(10, recetas.size());
	    Receta r;
	    for (int i = 0; i < size; i++) {
	    	r = recetas.get(i);
	    	String imageName = "recetas/" + r.getIdReceta();
	    	if(layoutResourceId == R.layout.list_recetas_small){
	    		Utils.preLoadImage(context, imageName, 160, 160);
	        }else{
	        	Utils.preLoadImage(context, imageName, 400, 300);
	        }
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolderReceta viewHolderReceta = null;

        if(convertView == null){

	        viewHolderReceta = new ViewHolderReceta();
	        
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            
            viewHolderReceta.resultados_image = (ImageView) convertView.findViewById(R.id.resultados_image);
            viewHolderReceta.nombre = (TextView) convertView.findViewById(R.id.nombre);
            viewHolderReceta.descripcion = (TextView) convertView.findViewById(R.id.descripcion);
            viewHolderReceta.tiempo = (TextView) convertView.findViewById(R.id.tiempo);
            viewHolderReceta.dificultad = (TextView) convertView.findViewById(R.id.dificultad);
            viewHolderReceta.porciones = (TextView) convertView.findViewById(R.id.porciones);
            viewHolderReceta.resultados_usuarios_image = (ImageView) convertView.findViewById(R.id.resultados_usuarios_image);
            
            convertView.setTag(viewHolderReceta);
            
        }else{
        	viewHolderReceta = (ViewHolderReceta) convertView.getTag();
        }
        
        Receta r = recetas.get(position);
        
        String imageName = ImageUtils.generateFileName(Receta.TABLE_NAME, String.valueOf(r.getIdReceta()));
        if(layoutResourceId == R.layout.list_recetas_small){
        	Utils.loadImage(
        		this.context, 
        		imageName, 
        		160, 
        		160, 
        		viewHolderReceta.resultados_image, 
        		R.drawable.load_placeholder
        	);
        }else{
        	Utils.loadImage(
        		this.context, 
        		imageName,
        		400, 
        		300, 
        		viewHolderReceta.resultados_image,
        		R.drawable.load_placeholder
        	);
        }
        
        viewHolderReceta.nombre.setText(r.getNombre());
        viewHolderReceta.descripcion.setText(r.getDescripcion());
        viewHolderReceta.tiempo.setText(r.getTiempo());
        viewHolderReceta.dificultad.setText(r.getDificultad());
        viewHolderReceta.porciones.setText(Integer.toString(r.getPorciones()));
        
    	Utils.loadImage(
    		this.context, 
    		ImageUtils.generateFileName(Usuario.TABLE_NAME, r.getUsuario().getUsername()), 
    		100, 
    		100, 
    		viewHolderReceta.resultados_usuarios_image, 
    		R.drawable.load_placeholder
    	);

	      
        return convertView;
    }

    /**
     * Clase estática utilizada para mapear los elementos del 'list_recetas.xml' ordenadamente.
    */
    static class ViewHolderReceta
    {
    	ImageView resultados_image;
        TextView nombre;
        TextView descripcion;
        TextView tiempo;
        TextView dificultad;
        TextView porciones;
        ImageView resultados_usuarios_image;
    }
	
}
