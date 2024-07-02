package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Anotacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotacionRepository extends MongoRepository<Anotacion, String> {
}
