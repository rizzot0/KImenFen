package com.kimenFen.cl.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "anotaciones")
public class Anotacion {

    @Id
    private String id;
    private String texto;

    @DBRef
    private Alumno alumno;

    public Anotacion() {}

    public Anotacion(String id, String texto, Alumno alumno) {
        this.id = id;
        this.texto = texto;
        this.alumno = alumno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
