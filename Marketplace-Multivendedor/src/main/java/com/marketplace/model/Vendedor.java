package com.marketplace.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vendedores")
public class Vendedor {
	
	@Id
    private String id;
	
	 private String nombre;
	    private String correo;

	    public Vendedor() {
	    }

	    public Vendedor(String id, String nombre, String correo) {
	        this.id = id;
	        this.nombre = nombre;
	        this.correo = correo;
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getCorreo() {
			return correo;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}
	
}
