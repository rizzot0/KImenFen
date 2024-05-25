package com.kimenFen.cl.Repository;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Apoderado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ApoderadoRepository extends JpaRepository<Apoderado,Long>{

}
