package com.kimenFen.cl.Controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfesorController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);

    @GetMapping("/profesor/alumnos/nuevo")
    public String mostrarFormularioNuevoAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "nuevo-alumno";
    }

    @PostMapping("/profesor/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno";
        }
        alumnoRepository.save(alumno);
        logger.info("Alumno guardado correctamente: " + alumno.toString());
        return "redirect:/profesor/menu";  
    }
    
    @GetMapping("/profesor/alumnos")
    public String listarAlumnos(Model model) {
        List<Alumno> alumnos = alumnoRepository.findAll();
        model.addAttribute("alumnos", alumnos);
        return "lista-alumnos";
    }

}
