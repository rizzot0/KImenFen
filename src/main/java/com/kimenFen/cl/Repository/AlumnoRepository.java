package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Alumno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends MongoRepository<Alumno, String> {
}