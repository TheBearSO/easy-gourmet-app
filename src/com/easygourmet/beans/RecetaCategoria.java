package com.easygourmet.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase RecetaCategoria, la cual hace referencia a cada una de las categorías que puede tener las recetas.
 */
@DatabaseTable(tableName = RecetaCategoria.TABLE_NAME)
public class RecetaCategoria {
	
	/** La Constante TABLE_NAME. */
	public static final String TABLE_NAME = "recetasCategorias";
	
	/** La Constante FIELD_NAME_id. */
	public static final String FIELD_NAME_id = "id";
	
	/** La Constante FIELD_NAME_descripcion. */
	public static final String FIELD_NAME_descripcion = "descripcion";
	
	/** Es el id auto incremental que indetifica de manera única a cada categoría de receta. */
	@DatabaseField(generatedId = true)
	private int id;
	
	/** La descripcion o el nombre de la categoría. */
	@DatabaseField(width = 200, canBeNull = false)
	private String descripcion;
	
	/**
	 * Constructor vacío.
	 */
	public RecetaCategoria() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.descripcion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecetaCategoria other = (RecetaCategoria) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
