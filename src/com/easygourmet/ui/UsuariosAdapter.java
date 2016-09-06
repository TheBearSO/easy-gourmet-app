package com.easygourmet.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.easygourmet.beans.Usuario;
import com.easygourmet.main.R;
import com.easygourmet.utils.ImageUtils;
import com.easygourmet.utils.Utils;

/**
 * Custom adapter de la clase {@link com.easygourmet.beans.Receta}.
 */
public class UsuariosAdapter extends ArrayAdapter<Usuario> {

	/**
	 * El contexto de la aplicación
	 */
	private final Context context;
    
	/**
     * La lista de usuarios
     */
    private List<Usuario> usuarios;
    
    /**
     * El id del resource
     */
    private final int layoutResourceId;
    
	
    /**
     * Constructor. Hace un super a la clase de la que hereda {@link android.widget.ArrayAdapter}. Además setea los campos 
     * final <code>context</code>, <code>usuarios</code>, <code>layoutResourceId</code>.
     * 
     * @param context El contexto de la aplicación
     * @param resource El id del resource
     * @param usuarios La lista de usuarios
     */
    public UsuariosAdapter(Context context, int resource, List<Usuario> usuarios) {
		
		super(context, resource, usuarios);
		
		this.context = context;
	    this.usuarios = usuarios;
	    this.layoutResourceId = resource;
	    
	    int size = Math.min(10, usuarios.size());
	    Usuario u;
	    for (int i = 0; i < size; i++) {
	    	u = usuarios.get(i);
	    	Utils.preLoadImage(context, ("usuarios/" + u.getUsername()), 100, 100);
		}
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolderUsuario viewHolderUsuario = null;

        if(convertView == null){

	        viewHolderUsuario = new ViewHolderUsuario();
	        
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            
            viewHolderUsuario.list_usuarios_image = (ImageView) convertView.findViewById(R.id.list_usuarios_image);
            viewHolderUsuario.list_usuarios_username = (TextView) convertView.findViewById(R.id.list_usuarios_username);
            viewHolderUsuario.list_usuarios_recetas = (TextView) convertView.findViewById(R.id.list_usuarios_recetas);
            
            convertView.setTag(viewHolderUsuario);
            
        }else{
        	viewHolderUsuario = (ViewHolderUsuario) convertView.getTag();
        }
        
        Usuario u = usuarios.get(position);
        
        Utils.loadImage(
    		this.context, 
    		ImageUtils.generateFileName(Usuario.TABLE_NAME, u.getUsername()), 
    		100, 
    		100, 
    		viewHolderUsuario.list_usuarios_image, 
    		R.drawable.load_placeholder
    	);
        viewHolderUsuario.list_usuarios_username.setText(Utils.capitalizeFully(u.getUsername()));
        viewHolderUsuario.list_usuarios_recetas.setText((u.getRecetas() == null ? 0 : u.getRecetas().size()) + " Recetas");
	      
        return convertView;
    }

    /**
     * Clase estática utilizada para mapear los elementos del 'list_recetas.xml' ordenadamente.
    */
    static class ViewHolderUsuario
    {
    	ImageView list_usuarios_image;
        TextView list_usuarios_username;
        TextView list_usuarios_recetas;
        //TextView list_usuarios_descripcion;
    }
    
//    public Filter getFilter() {
//    	return customFilter;
//    };
//    
//    Filter customFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//         FilterResults filterResults = new FilterResults();   
//         ArrayList<Usuario> tempList = new ArrayList<Usuario>();
//         if(constraint != null && usuarios !=null) {
//            for (Usuario u : usuarios) {
//             	if(u.getUsername().contains(constraint) ){
//                    tempList.add(u);
//             	}
//			}
//                
//            filterResults.values = tempList;
//            filterResults.count = tempList.size();
//          }
//          return filterResults;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void publishResults(CharSequence contraint, FilterResults results) {
//        	usuarios = (ArrayList<Usuario>) results.values;
//          	if (results.count > 0) {
//          		notifyDataSetChanged();
//          	} else {
//          		notifyDataSetInvalidated();
//          	}  
//        }
//      };
    
	
}
