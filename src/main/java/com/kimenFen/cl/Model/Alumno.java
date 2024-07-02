package com.kimenFen.cl.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "alumnos")
public class Alumno {

	@Id
	private String id;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String rut;
	private String telefonoApoderado;
	private String direccion;
	private String curso;

	@DBRef
	private Apoderado apoderado;

	@DBRef
	private List<Anotacion> anotaciones = new ArrayList<>();

	@DBRef
	private List<Nota> notas = new ArrayList<>();

	public Alumno() {}

	public Alumno(String nombre, String apellidoPaterno, String apellidoMaterno, String rut, String telefonoApoderado, String direccion, String curso) {
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.rut = rut;
		this.telefonoApoderado = telefonoApoderado;
		this.direccion = direccion;
		this.curso = curso;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
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

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getTelefonoApoderado() {
		return telefonoApoderado;
	}

	public void setTelefonoApoderado(String telefonoApoderado) {
		this.telefonoApoderado = telefonoApoderado;
	}

	public List<Anotacion> getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(List<Anotacion> anotaciones) {
		this.anotaciones = anotaciones;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public Apoderado getApoderado() {
		return apoderado;
	}

	public void setApoderado(Apoderado apoderado) {
		this.apoderado = apoderado;
	}
}
