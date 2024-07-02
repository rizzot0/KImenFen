package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Nota;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotaRepository extends MongoRepository<Nota, Long> {
}

