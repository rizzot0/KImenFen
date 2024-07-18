package com.kimenFen.cl.Service;

import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Repository.ApoderadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ApoderadoService {
    @Autowired
    private ApoderadoRepository apoderadoRepository;

}
