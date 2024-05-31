package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Profesor;
import com.kimenFen.cl.Repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor saveProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public Profesor getProfesorById(Long id) {
        return profesorRepository.findById(id).orElse(null);
    }
}
