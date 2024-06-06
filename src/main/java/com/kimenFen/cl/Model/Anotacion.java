package com.kimenFen.cl.Model;


import jakarta.persistence.*;


@Entity
public class Anotacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    public Anotacion(Long id, String texto, Alumno alumno) {
        this.id = id;
        this.texto = texto;
        this.alumno = alumno;
    }

    public Anotacion() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
