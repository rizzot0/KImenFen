package com.kimenFen.cl.Controllers;

import java.util.List;

import com.kimenFen.cl.Service.AlumnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfesorController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AlumnoService alumnoService;
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

    @GetMapping("/profesor/editar-alumno/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        model.addAttribute("alumno", alumno);
        return "editar-alumno-profesor";
    }

    @PostMapping("/profesor/actualizar-alumno")
    public String actualizarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-alumno-profesor";
        }
        alumnoService.actualizarAlumno(alumno);
        return "redirect:/profesor/alumnos"; // Redirige a la lista de alumnos para el profesor después de la actualización
    }

    @GetMapping("/profesor/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoService.eliminarAlumno(id);
        return "redirect:/profesor/alumnos";
    }

    @GetMapping("/profesor/anotacion-alumno/{id}")
    public String mostrarFormularioAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        if (alumno == null) {
            return "redirect:/profesor/alumnos";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("rol", "administrador");
        return "anotacion-alumno-profesor";
    }

    @PostMapping("/profesor/agregar-anotacion")
    public String agregarAnotacion(@RequestParam Long id, @RequestParam String anotacion) {
        alumnoService.agregarAnotacion(id, anotacion);
        return "redirect:/profesor/alumnos";
    }

    @GetMapping("/profesor/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", alumno.getAnotaciones());
        }
        return "ver-anotaciones-profesor";
    }

}
