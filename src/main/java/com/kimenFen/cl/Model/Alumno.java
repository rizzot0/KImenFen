package com.kimenFen.cl.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Alumno {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rut;
    private String telefonoApoderado;
    private String direccion;
    private String curso;
    

    @ManyToOne
    @JoinColumn(name = "apoderado_id")
    private Apoderado apoderado;
    
    @ElementCollection
    private List<String> anotaciones = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "alumno")
    private List<Nota> notas;

    public Alumno() {}

    public Alumno(String nombre, String apellidoPaterno, String apellidoMaterno, String rut, String telefonoApoderado,String direccion,String curso) {
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

	public List<String> getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(List<String> anotaciones) {
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
