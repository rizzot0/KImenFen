package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Alumno obtenerAlumnoPorId(String id) {
        Optional<Alumno> optionalAlumno = alumnoRepository.findById(id);
        return optionalAlumno.orElse(null);
    }

    public void actualizarAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public void eliminarAlumno(String id) {
        alumnoRepository.deleteById(id);
    }

    public void agregarAnotacion(String id, Anotacion anotacion) {
        Alumno alumno = obtenerAlumnoPorId(id);
        if (alumno != null) {
            alumno.getAnotaciones().add(anotacion);
            alumnoRepository.save(alumno);
        }
    }

    public List<Anotacion> obtenerAnotaciones(String id) {
        Alumno alumno = obtenerAlumnoPorId(id);
        return alumno != null ? alumno.getAnotaciones() : new ArrayList<>();
    }
}
