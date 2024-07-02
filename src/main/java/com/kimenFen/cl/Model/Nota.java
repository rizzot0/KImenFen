package com.kimenFen.cl.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notas")
public class Nota {

	@Id
	private String id;

	private String asignatura;
	private Double calificacion;

	@DBRef
	private Alumno alumno;

	public Nota() {}

	public Nota(String asignatura, Double calificacion, Alumno alumno) {
		this.asignatura = asignatura;
		this.calificacion = calificacion;
		this.alumno = alumno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
}
