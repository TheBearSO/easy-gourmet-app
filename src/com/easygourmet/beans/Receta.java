package com.easygourmet.beans;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase Receta, la cual hace referencia a cada una de las recetas cargadas en el sistema.
 */
@DatabaseTable(tableName = Receta.TABLE_NAME)
public class Receta {
	
	/** La Constante TABLE_NAME. */
	public static final String TABLE_NAME = "recetas";
	
	/** La Constante FIELD_NAME_idReceta. */
	public static final String FIELD_NAME_idReceta = "idReceta";
	
	/** La Constante FIELD_NAME_nombre. */
	public static final String FIELD_NAME_nombre= "nombre";
	
	/** La Constante FIELD_NAME_vegetariano. */
	public static final String FIELD_NAME_vegetariano = "vegetariano";
	
	/** La Constante FIELD_NAME_vegano. */
	public static final String FIELD_NAME_vegano = "vegano";
	
	/** La Constante FIELD_NAME_celiaco. */
	public static final String FIELD_NAME_celiaco = "celiaco";
	
	/**
	 * Es el id auto incremental de la receta.
	*/
	@DatabaseField(generatedId = true)
	private int idReceta;
	
	/**
	 * Es el nombre principal de la receta.
	*/
	@DatabaseField(canBeNull = false, width = 30, unique = true)
	private String nombre;
	
	/**
	 * Es la descripcion general de la receta. Se utiliza en la activity 'Resultados'.
	*/
	@DatabaseField(canBeNull = false, width = 200)
	private String descripcion;
	
	/**
	 * Es el modo de preparación la receta.
	*/
	@DatabaseField(canBeNull = false, width = 2000)
	private String preparacion;
	
	/** El tiempo de preparación. */
	@DatabaseField(canBeNull = false, width = 20)
	private String tiempo;
	
	/** La dificultad en la preparación. */
	@DatabaseField(canBeNull = false, width = 40)
	private String dificultad;
	
	@DatabaseField
	private boolean vegetariano;
	
	@DatabaseField
	private boolean vegano;
	
	@DatabaseField
	private boolean celiaco;
	
	/**
	 * Es la cantidad de porciones en numeros enteros positivos que rinde la receta.
	*/
	@DatabaseField(defaultValue = "1")
	private int porciones;
	
	/**
	 * Es el id de la categoria a la que pertenece la receta.
	*/
	@DatabaseField(foreign = true, foreignColumnName = RecetaCategoria.FIELD_NAME_id, canBeNull = false)
	private RecetaCategoria categoria;
	
	/**
	 * Es el id auto incremental de la receta.
	*/
	@ForeignCollectionField(eager = true)
	private ForeignCollection<RecetaDetalle> detalles;
	
	/**
	 * Consturctor Vacío.
	*/
	public Receta() {}

	public int getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(int idReceta) {
		this.idReceta = idReceta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getPreparacion() {
		return preparacion;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public int getPorciones() {
		return porciones;
	}

	public void setPorciones(int porciones) {
		this.porciones = porciones;
	}

	public RecetaCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(RecetaCategoria categoria) {
		this.categoria = categoria;
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

	public ForeignCollection<RecetaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(ForeignCollection<RecetaDetalle> detalles) {
		this.detalles = detalles;
	}


	public int getSalud(){
		double salud = 0;
		Ingrediente i;
		int j = 0;
		for(RecetaDetalle rd : this.detalles){
			j++;
			i = rd.getIngrediente();
			salud += i.getKcal();
		} 
		salud = salud / j; 
		salud = (salud * 100) / 500;
		if(salud < 500) return (int) salud;
		else return (int) Math.floor(100);
	}

}
