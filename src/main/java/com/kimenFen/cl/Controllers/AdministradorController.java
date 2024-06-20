package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Model.Profesor;
import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.AnotacionRepository;
import com.kimenFen.cl.Repository.ApoderadoRepository;
import com.kimenFen.cl.Repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String rol = userDetails.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("rol", rol);
        }
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "lista-alumnos";
    }

    @GetMapping("/apoderados")
    public String listarApoderados(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String rol = userDetails.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("rol", rol);
        }
        model.addAttribute("apoderados", apoderadoRepository.findAll());
        return "lista-apoderado";
    }

    @GetMapping("/profesores")
    public String listarProfesores(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String rol = userDetails.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("rol", rol);
        }
        model.addAttribute("profesores", profesorRepository.findAll());
        return "lista-profesores";
    }

    @GetMapping("/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", alumno.getAnotaciones());
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
        return "nuevo-alumno";
    }

    @GetMapping("/apoderados/nuevo")
    public String nuevoApoderado(Model model) {
        model.addAttribute("apoderado", new Apoderado());
        return "nuevo-apoderado";
    }


    @PostMapping("/profesores/guardar")
    public String guardarProfesor(@ModelAttribute Profesor profesor, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-profesor";
        }
        profesorRepository.save(profesor);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "nuevo-alumno";
        }
        alumnoRepository.save(alumno);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/anotacion-alumno/{id}")
    public String agregarAnotacion(@PathVariable("id") Long id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String rol = userDetails.getAuthorities().iterator().next().getAuthority();
                model.addAttribute("rol", rol);
            }
        }
        return "anotacion-alumno";
    }

    @PostMapping("/agregar-anotacion")
    public String guardarAnotacion(@RequestParam Long id, @RequestParam String texto, @RequestParam String rol) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            Anotacion anotacion = new Anotacion();
            anotacion.setTexto(texto);
            anotacion.setAlumno(alumno);
            anotacionRepository.save(anotacion);
        }
        return "redirect:/administrador/menu";
    }

    @GetMapping("/asociar-alumno-apoderado")
    public String formularioAsociarAlumnoApoderado(Model model) {
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
            return "editar-anotacion";
        }
        return "redirect:/administrador/menu";
    }

    @PostMapping("/editar-anotacion")
    public String actualizarAnotacion(@RequestParam Long id, @RequestParam String texto) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            anotacion.setTexto(texto);
            anotacionRepository.save(anotacion);
            return "redirect:/administrador/menu";
        }
        return "redirect:/administrador/menu";
    }

    @PostMapping("/alumnos/actualizar")
    public String actualizarAlumno(@ModelAttribute Alumno alumno, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-alumno";
        }
        alumnoRepository.save(alumno);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/apoderados/actualizar")
    public String actualizarApoderado(@ModelAttribute Apoderado apoderado, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-apoderado";
        }
        apoderadoRepository.save(apoderado);
        return "redirect:/administrador/menu";
    }

    @PostMapping("/profesores/actualizar")
    public String actualizarProfesor(@ModelAttribute Profesor profesor, BindingResult result) {
        if (result.hasErrors()) {
            return "editar-profesor";
        }
        profesorRepository.save(profesor);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-alumno/{id}")
    public String eliminarAlumno(@PathVariable("id") Long id) {
        alumnoRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-apoderado/{id}")
    public String eliminarApoderado(@PathVariable("id") Long id) {
        apoderadoRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-profesor/{id}")
    public String eliminarProfesor(@PathVariable("id") Long id) {
        profesorRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }
//a
    @GetMapping("/eliminar-anotacion/{id}")
    public String eliminarAnotacion(@PathVariable("id") Long id) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            Long alumnoId = anotacion.getAlumno().getId();
            anotacionRepository.deleteById(id);
            return "redirect:/administrador/menu";
        }
        return "redirect:/administrador/menu";
    }
}
