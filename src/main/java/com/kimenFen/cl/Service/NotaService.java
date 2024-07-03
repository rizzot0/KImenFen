package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Nota;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaService {
    @Autowired
    NotaRepository notaRepository;

    @Autowired
    AlumnoService alumnoService;

    public List<Nota> findByAlumnoId(String alumnoId) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(alumnoId);
        if (alumno != null) {
            return notaRepository.findByAlumnoId(alumnoId);
        }
        return null;
    }
}
