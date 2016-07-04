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
	

	public ForeignCollection<RecetaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(ForeignCollection<RecetaDetalle> detalles) {
		this.detalles = detalles;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.nombre;
	}


	
}
