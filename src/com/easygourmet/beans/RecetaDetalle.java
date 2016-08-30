package com.easygourmet.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase RecetaDetalle, la cual hace referencia a cada uno de los detalles de una receta en particular.
 */
@DatabaseTable(tableName = RecetaDetalle.TABLE_NAME)
public class RecetaDetalle {
	
	/** La Constante TABLE_NAME. */
	public static final String TABLE_NAME = "recetasDetalles";
	
	/** The Constant FIELD_NAME_idDetalle. */
	public static final String FIELD_NAME_idDetalle = "idDetalle";
	
	/** Es el id auto incremental que indetifica de manera única a cada detalle de receta. */
	@DatabaseField(generatedId = true)
	private int idDetalle;
	
	/** La receta a la que hace referencia el detalle. */
	@DatabaseField(foreign = true, foreignColumnName = "idReceta")
	private Receta receta;
	
	/** La cantidad de ingrediente que lleva el detalle. La misma se concatenará a la unidad de medida del ingrediente */
	@DatabaseField(canBeNull = false)
	private float cantidad;
	
	@DatabaseField(canBeNull = false)
	private String unidadMedida;
	
	/** Si es TRUE quiere decir que el ingrediente es indispensable para esa receta. */
	@DatabaseField
	private boolean esIndispensable;

	/** El ingrediente a la que hace referencia el detalle. */
	@DatabaseField(foreign = true, foreignColumnName = "idIngrediente", canBeNull = false)
	private Ingrediente ingrediente;
	
	@DatabaseField
	private int version;
	
	/**
	 * Constructor vacío.
	 */
	public RecetaDetalle() {}
	
	

	/**
	 * @param idDetalle
	 * @param receta
	 * @param cantidad
	 * @param unidadMedida
	 * @param esIndispensable
	 * @param ingrediente
	 */
	public RecetaDetalle(RecetaDetalle r) {
		this.idDetalle = r.getIdDetalle();
		this.receta = r.getReceta();
		this.cantidad = r.getCantidad();
		this.unidadMedida = r.getUnidadMedida();
		this.esIndispensable = r.isEsIndispensable();
		this.ingrediente = r.getIngrediente();
	}



	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	

	public boolean isEsIndispensable() {
		return esIndispensable;
	}

	public void setEsIndispensable(boolean esIndispensable) {
		this.esIndispensable = esIndispensable;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(cantidad);
		result = prime * result + (esIndispensable ? 1231 : 1237);
		result = prime * result + idDetalle;
		result = prime * result
				+ ((ingrediente == null) ? 0 : ingrediente.hashCode());
		result = prime * result + ((receta == null) ? 0 : receta.hashCode());
		result = prime * result
				+ ((unidadMedida == null) ? 0 : unidadMedida.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecetaDetalle other = (RecetaDetalle) obj;
		if (Float.floatToIntBits(cantidad) != Float
				.floatToIntBits(other.cantidad))
			return false;
		if (esIndispensable != other.esIndispensable)
			return false;
		if (idDetalle != other.idDetalle)
			return false;
		if (ingrediente == null) {
			if (other.ingrediente != null)
				return false;
		} else if (!ingrediente.equals(other.ingrediente))
			return false;
		if (receta == null) {
			if (other.receta != null)
				return false;
		} else if (!receta.equals(other.receta))
			return false;
		if (unidadMedida == null) {
			if (other.unidadMedida != null)
				return false;
		} else if (!unidadMedida.equals(other.unidadMedida))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "- " + cantidad + unidadMedida + " " + this.ingrediente.getNombre();
	}
	
}
