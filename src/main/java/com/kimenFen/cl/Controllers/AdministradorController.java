package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.ApoderadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdministradorController {
	
	//Repositorios
	@Autowired
    private ApoderadoRepository apoderadoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    
    //Ver lista de alumnos
    @GetMapping("/administrador/alumnos")
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "lista-alumnos-admin";
    }

    //Crear un nuevo alumno 
    @GetMapping("/administrador/alumnos/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "nuevo-alumno-admin";
    }

    
    @PostMapping("/administrador/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno-admin";
        }
        alumnoRepository.save(alumno);
        return "redirect:/administrador/alumnos";
    }
    
    //Crear nuevo apoderado
    @GetMapping("/administrador/apoderados/nuevo")
    public String mostrarFormularioNuevoApoderado(Model model) {
        model.addAttribute("apoderado", new Apoderado());
        return "nuevo-apoderado";
    }

    @PostMapping("/administrador/apoderados/guardar")
    public String guardarApoderado(@ModelAttribute Apoderado apoderado, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-apoderado";
        }
        apoderadoRepository.save(apoderado);
        return "redirect:/administrador/menu";
    }
}
