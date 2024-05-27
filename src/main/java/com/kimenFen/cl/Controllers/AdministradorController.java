package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.ApoderadoRepository;

import com.kimenFen.cl.Service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;


@Controller
public class AdministradorController {
	
	//Repositorios
	@Autowired
    private ApoderadoRepository apoderadoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AlumnoService alumnoService;

    //Ver lista de alumnos
    @GetMapping("/administrador/alumnos")
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "lista-alumnos-admin";
    }

    //Ver lista de apoderados
    @GetMapping("/administrador/apoderados")
    public String listarApoderados(Model model) {
        model.addAttribute("apoderados", apoderadoRepository.findAll());
        return "lista-apoderado";
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

    @GetMapping("/administrador/asociar-alumno-apoderado")
    public String mostrarFormularioAsociarAlumnoApoderado(Model model) {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<Apoderado> apoderados = apoderadoRepository.findAll();

        System.out.println("Alumnos: " + alumnos.toString());
        System.out.println("Apoderados: " + apoderados.toString());

        return "asociar-alumno-apoderado";
    }

    @PostMapping("/administrador/asociar-alumno-apoderado")
    public String asociarAlumnoApoderado(@RequestParam Long alumnoId, @RequestParam Long apoderadoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        Apoderado apoderado = apoderadoRepository.findById(apoderadoId).orElse(null);

        if (alumno != null && apoderado != null) {
            alumno.setApoderado(apoderado);
            alumnoRepository.save(alumno);
        }

        return "redirect:/administrador/menu";
    }

    @GetMapping("/administrador/editar-alumno/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        model.addAttribute("alumno", alumno);
        return "editar-alumno-admin";
    }

    @PostMapping("/administrador/actualizar-alumno")
    public String actualizarAlumno(Alumno alumno) {
        alumnoService.actualizarAlumno(alumno);
        return "redirect:/administrador/alumnos"; // Redirige a la lista de alumnos para el administrador después de la actualización
    }

    @GetMapping("/administrador/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoService.eliminarAlumno(id);
        return "redirect:/administrador/alumnos";
    }


    @GetMapping("/administrador/anotacion-alumno/{id}")
    public String mostrarFormularioAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoService.obtenerAlumnoPorId(id);
        model.addAttribute("alumno", alumno);
        model.addAttribute("rol", "administrador");
        return "anotacion-alumno-admin";
    }

    @PostMapping("/administrador/agregar-anotacion")
    public String agregarAnotacion(@RequestParam Long id, @RequestParam String anotacion, Model model) {
        alumnoService.agregarAnotacion(id, anotacion);
        return "redirect:/administrador/alumnos";
    }

}
