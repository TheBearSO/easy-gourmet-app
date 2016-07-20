package com.easygourmet.beans;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase Ingrediente, la cual hace referencia a todos los ingredientes existentes para cocinar.
 */
@DatabaseTable(tableName = Ingrediente.TABLE_NAME)
public class Ingrediente {
	
	/** La Constante TABLE_NAME. */
	public static final String TABLE_NAME = "ingredientes";
	
	/** La Constante FIELD_NAME_idIngrediente. */
	public static final String FIELD_NAME_idIngrediente = "idIngrediente";
	
	/** La Constante FIELD_NAME_nombre. */
	public static final String FIELD_NAME_nombre = "nombre";
	
	/** La Constante FIELD_NAME_vegetariano. */
	public static final String FIELD_NAME_vegetariano = "vegetariano";
	
	/** La Constante FIELD_NAME_vegano. */
	public static final String FIELD_NAME_vegano = "vegano";
	
	/** La Constante FIELD_NAME_celiaco. */
	public static final String FIELD_NAME_celiaco = "celiaco";
	
	/** EL id auto-incremental que identifica de manera única a cada ingrediente. */
	@DatabaseField(generatedId = true, canBeNull = false)
	private int idIngrediente;
	
	/** El nombre. */
	@DatabaseField(width = 40, canBeNull = false, unique = true)
	private String nombre;
	
	/** El tipo. */
	@DatabaseField(foreign = true, foreignColumnName = "id", canBeNull = false)
	private IngredienteTipo tipo;
	
	/** El coste del ingrediente. Puede ser (ALTO, MEDIO o BAJO) */
	@DatabaseField
	private String coste;
	
	/** Si es TRUE indica que el ingrediente es apto para vegetarianos. */
	@DatabaseField
	private boolean vegetariano;
	
	/** Si es TRUE indica que el ingrediente es apto para veganos. */
	@DatabaseField
	private boolean vegano;
	
	/** Si es TRUE indica que el ingrediente es apto para celiacos. */
	@DatabaseField
	private boolean celiaco;
	
	/** Las kcal. */
	@DatabaseField
	private double kcal;
	
	/** La Salud del alimento. */
	@DatabaseField
	private int salud;
	
	/** Los detalles en los que aparece el ingrediente. */
	@ForeignCollectionField(eager = true)
	private ForeignCollection<RecetaDetalle> detalles;

	/**
	 * Constructor Vacío.
	*/
	public Ingrediente() {}

	public int getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(int idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public IngredienteTipo getTipo() {
		return tipo;
	}

	public void setTipo(IngredienteTipo tipo) {
		this.tipo = tipo;
	}

	public String getCoste() {
		return coste;
	}

	public void setCoste(String coste) {
		this.coste = coste;
	}

	public boolean isVegetariano() {
		return vegetariano;
	}

	public void setVegetariano(boolean vegetariano) {
		this.vegetariano = vegetariano;
	}

	public boolean isVegano() {
		return vegano;
	}

	public void setVegano(boolean vegano) {
		this.vegano = vegano;
	}

	public boolean isCeliaco() {
		return celiaco;
	}

	public void setCeliaco(boolean celiaco) {
		this.celiaco = celiaco;
	}

	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}
	
	public int getSalud() {
		return salud;
	}

	public void setSalud(int salud) {
		this.salud = salud;
	}

	public ForeignCollection<RecetaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(ForeignCollection<RecetaDetalle> detalles) {
		this.detalles = detalles;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (celiaco ? 1231 : 1237);
		result = prime * result + ((coste == null) ? 0 : coste.hashCode());
		result = prime * result
				+ ((detalles == null) ? 0 : detalles.hashCode());
		result = prime * result + idIngrediente;
		long temp;
		temp = Double.doubleToLongBits(kcal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + salud;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + (vegano ? 1231 : 1237);
		result = prime * result + (vegetariano ? 1231 : 1237);
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
		Ingrediente other = (Ingrediente) obj;
		if (celiaco != other.celiaco)
			return false;
		if (coste == null) {
			if (other.coste != null)
				return false;
		} else if (!coste.equals(other.coste))
			return false;
		if (detalles == null) {
			if (other.detalles != null)
				return false;
		} else if (!detalles.equals(other.detalles))
			return false;
		if (idIngrediente != other.idIngrediente)
			return false;
		if (Double.doubleToLongBits(kcal) != Double
				.doubleToLongBits(other.kcal))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (salud != other.salud)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (vegano != other.vegano)
			return false;
		if (vegetariano != other.vegetariano)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.nombre;
	}


	
}
