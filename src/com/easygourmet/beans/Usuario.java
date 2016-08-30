package com.easygourmet.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * La Clase Usuario, la cual hace referencia a todos los usuarios que suben recetas.
 */
@DatabaseTable(tableName = Usuario.TABLE_NAME)
public class Usuario {
	
	public static final String TABLE_NAME = "usuarios";
	
	public static final String FIELD_NAME_id = "id";
	public static final String FIELD_NAME_username = "username";
	public static final String FIELD_NAME_descripcion = "descripcion";
	public static final String FIELD_NAME_url = "url";
	public static final String FIELD_NAME_version = "version";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(width = 50, canBeNull = false, unique = true)
	private String username;
	
	@DatabaseField(width = 80)
	private String descripcion;
	
	@DatabaseField(width = 250)
	private String url;
	
	@DatabaseField
	private int version;
	
	public Usuario(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + id;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + version;
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
		Usuario other = (Usuario) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id != other.id)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username
				+ ", descripcion=" + descripcion + ", url=" + url
				+ ", version=" + version + "]";
	}
	
}
