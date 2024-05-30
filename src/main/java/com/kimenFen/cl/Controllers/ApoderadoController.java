package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ApoderadoController{

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AlumnoService alumnoService;


    @GetMapping("/apoderado/alumnos")
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("rol", "apoderado");
        return "lista-alumnos";
    }


    @GetMapping("/apoderado/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", alumno.getAnotaciones());
            model.addAttribute("rol", "apoderado");
        } else {
            return "redirect:/apoderado/alumnos";
        }
        return "ver-anotaciones";
    }

}