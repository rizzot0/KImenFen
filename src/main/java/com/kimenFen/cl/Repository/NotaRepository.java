package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Nota;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends MongoRepository<Nota, String> {
    List<Nota> findByAlumnoId(String alumnoId);
}
