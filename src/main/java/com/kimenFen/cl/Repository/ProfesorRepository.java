package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Profesor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends MongoRepository<Profesor, Long> {
}
