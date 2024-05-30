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
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ApoderadoRepository apoderadoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping("/alumnos")
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("rol", "administrador");
        return "lista-alumnos";
    }

    @GetMapping("/apoderados")
    public String listarApoderados(Model model) {
        model.addAttribute("apoderados", apoderadoRepository.findAll());
        return "lista-apoderado";
    }

    @GetMapping("/alumnos/nuevo")
    public String mostrarFormularioAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        model.addAttribute("rol", "administrador");
        return "nuevo-alumno";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno";
        }
        alumnoRepository.save(alumno);
        return "redirect:/administrador/alumnos";
    }

    @GetMapping("/apoderados/nuevo")
    public String mostrarFormularioNuevoApoderado(Model model) {
        model.addAttribute("apoderado", new Apoderado());
        return "nuevo-apoderado";
    }

    @PostMapping("/apoderados/guardar")
    public String guardarApoderado(@ModelAttribute Apoderado apoderado, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-apoderado";
        }
        apoderadoRepository.save(apoderado);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/asociar-alumno-apoderado")
    public String mostrarFormularioAsociarAlumnoApoderado(Model model) {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<Apoderado> apoderados = apoderadoRepository.findAll();

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("apoderados", apoderados);

        return "asociar-alumno-apoderado";
    }

    @PostMapping("/asociar-alumno-apoderado")
    public String asociarAlumnoApoderado(@RequestParam Long alumnoId, @RequestParam Long apoderadoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        Apoderado apoderado = apoderadoRepository.findById(apoderadoId).orElse(null);

        if (alumno != null && apoderado != null) {
            alumno.setApoderado(apoderado);
            alumnoRepository.save(alumno);
        }

        return "redirect:/administrador/menu";
    }

    @GetMapping("/editar-alumno/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return "redirect:/administrador/alumnos";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("rol", "administrador");
        return "editar-alumno";
    }

    @PostMapping("/actualizar-alumno")
    public String actualizarAlumno(@ModelAttribute Alumno alumno) {
        alumnoRepository.save(alumno);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoRepository.deleteById(id);
        return "redirect:/administrador/alumnos";
    }

    @GetMapping("/anotacion-alumno/{id}")
    public String agregarAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("rol", "administrador");
        }
        return "anotacion-alumno";
    }

    @PostMapping("/agregar-anotacion")
    public String guardarAnotacion(@RequestParam Long id, @RequestParam String anotacion) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            alumno.getAnotaciones().add(anotacion);
            alumnoRepository.save(alumno);
        }
        return "redirect:/administrador/alumnos";
    }

    @GetMapping("/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", alumno.getAnotaciones());
            model.addAttribute("rol", "administrador");
        }
        return "ver-anotaciones";
    }
}

