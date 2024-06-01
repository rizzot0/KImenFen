package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Model.Profesor;
import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.AnotacionRepository;
import com.kimenFen.cl.Repository.ApoderadoRepository;

import com.kimenFen.cl.Repository.ProfesorRepository;
import com.kimenFen.cl.Service.AlumnoService;
import com.kimenFen.cl.Service.ProfesorService;
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
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private AnotacionRepository anotacionRepository;

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

    @GetMapping("/profesores")
    public String listarProfesores(Model model) {
        model.addAttribute("profesores", profesorRepository.findAll());
        return "lista-profesores";
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

    @GetMapping("/profesores/nuevo")
    public String nuevoProfesor(Model model) {
        model.addAttribute("profesor", new Profesor());
        return "nuevo-profesor";
    }

    @GetMapping("/alumnos/nuevo")
    public String nuevoAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        model.addAttribute("rol", "administrador");
        return "nuevo-alumno";
    }

    @GetMapping("/apoderados/nuevo")
    public String nuevoApoderado(Model model) {
        model.addAttribute("apoderado", new Apoderado());
        return "nuevo-apoderado";
    }

    @GetMapping("/anotacion-alumno/{id}")
    public String nuevaAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("rol", "administrador");
        }
        return "anotacion-alumno";
    }

    @PostMapping("/profesores/guardar")
    public String guardarProfesor(@ModelAttribute Profesor profesor, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-profesor";
        }
        profesorRepository.save(profesor);
        return "redirect:/administrador/profesores";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno";
        }
        alumnoRepository.save(alumno);
        return "redirect:/administrador/alumnos";
    }

    @PostMapping("/apoderados/guardar")
    public String guardarApoderado(@ModelAttribute Apoderado apoderado, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-apoderado";
        }
        apoderadoRepository.save(apoderado);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/agregar-anotacion")
    public String guardarAnotacion(@RequestParam Long id, @RequestParam String texto) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            Anotacion anotacion = new Anotacion();
            anotacion.setTexto(texto);
            anotacion.setAlumno(alumno);
            anotacionRepository.save(anotacion);
        }
        return "redirect:/administrador/ver-anotaciones/" + id;
    }


    @GetMapping("/asociar-alumno-apoderado")
    public String FormularioAsociarAlumnoApoderado(Model model) {
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
    public String editarAlumno(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return "redirect:/administrador/alumnos";
        }
        model.addAttribute("alumno", alumno);
        model.addAttribute("rol", "administrador");
        return "editar-alumno";
    }

    @GetMapping("/editar-apoderado/{id}")
    public String editarApoderado(@PathVariable("id") Long id, Model model) {
        Apoderado apoderado = apoderadoRepository.findById(id).orElse(null);
        if (apoderado == null) {
            return "redirect:/administrador/apoderados";
        }
        model.addAttribute("apoderado", apoderado);
        return "editar-apoderado";
    }


    @GetMapping("/editar-profesor/{id}")
    public String editarProfesor(@PathVariable("id") Long id, Model model) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return "redirect:/administrador/profesores";
        }
        model.addAttribute("profesor", profesor);
        return "editar-profesor";
    }

    @GetMapping("/editar-anotacion/{id}")
    public String editarAnotacion(@PathVariable("id") Long id, Model model) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            model.addAttribute("anotacion", anotacion);
        }
        return "editar-anotacion";
    }

    @PostMapping("/actualizar-alumno")
    public String actualizarAlumno(@ModelAttribute Alumno alumno) {
        alumnoRepository.save(alumno);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/actualizar-apoderado")
    public String actualizarApoderado(@ModelAttribute Apoderado apoderado) {
        apoderadoRepository.save(apoderado);
        return "redirect:/administrador/apoderados";
    }

    @PostMapping("/actualizar-profesor")
    public String actualizarProfesor(@ModelAttribute Profesor profesor) {
        profesorRepository.save(profesor);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/actualizar-anotacion")
    public String actualizarAnotacion(@ModelAttribute Anotacion anotacion) {
        anotacionRepository.save(anotacion);
        return "redirect:/administrador/ver-anotaciones/" + anotacion.getAlumno().getId();
    }


    @GetMapping("/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoRepository.deleteById(id);
        return "redirect:/administrador/alumnos";
    }

    @GetMapping("/eliminar-apoderado/{id}")
    public String eliminarApoderado(@PathVariable("id") Long id) {
        apoderadoRepository.deleteById(id);
        return "redirect:/administrador/apoderados";
    }

    @GetMapping("/eliminar-profesor/{id}")
    public String eliminarProfesor(@PathVariable("id") Long id) {
        profesorRepository.deleteById(id);
        return "redirect:/administrador/profesores";
    }

    @GetMapping("/eliminar-anotacion/{id}")
    public String eliminarAnotacion(@PathVariable("id") Long id) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            Long alumnoId = anotacion.getAlumno().getId();
            anotacionRepository.deleteById(id);
            return "redirect:/administrador/ver-anotaciones/" + alumnoId;
        }
        return "redirect:/administrador/profesores";
    }

}

