package com.kimenFen.cl.Model;

import jakarta.persistence.*;

@Entity
public class Profesor {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nombre;
	    private String apellido;
	    private String rut;
	    private String telefono;

		public Profesor(Long id, String nombre, String apellido, String rut, String telefono) {
			this.id = id;
			this.nombre = nombre;
			this.apellido = apellido;
			this.rut = rut;
			this.telefono = telefono;
		}

	public Profesor() {

	}


	public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		public String getRut() {
			return rut;
		}
		public void setRut(String rut) {
			this.rut = rut;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
	    
	    
}
