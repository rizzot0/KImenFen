package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Apoderado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApoderadoRepository extends MongoRepository<Apoderado, String> {
    Optional<Apoderado> findByRut(String rut);
}

