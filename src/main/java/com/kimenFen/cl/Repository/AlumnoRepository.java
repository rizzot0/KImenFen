package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Apoderado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends MongoRepository<Alumno, String> {
    List<Alumno> findByApoderado(Apoderado apoderado);
}
