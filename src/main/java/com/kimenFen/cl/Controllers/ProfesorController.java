package com.kimenFen.cl.Controllers;

import java.security.Principal;
import java.util.List;

import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Model.Nota;
import com.kimenFen.cl.Repository.AnotacionRepository;
import com.kimenFen.cl.Repository.NotaRepository;
import com.kimenFen.cl.Service.AlumnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AnotacionRepository anotacionRepository;
    @Autowired
    private NotaRepository notaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProfesorController.class);

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

    @GetMapping("/anotacion-alumno/{id}")
    public String agregarAnotacion(@PathVariable("id") String id, Model model) {
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
    public String guardarAnotacion(@RequestParam String id, @RequestParam String texto, @RequestParam String rol) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            Anotacion anotacion = new Anotacion();
            anotacion.setTexto(texto);
            anotacion.setAlumno(alumno);
            anotacionRepository.save(anotacion);
        }
        return "redirect:/profesor/menu";
    }

    @GetMapping("/ver-anotaciones/{id}")
    public String verAnotaciones(@PathVariable("id") String id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            List<Anotacion> anotaciones = anotacionRepository.findByAlumno_Id(id);
            model.addAttribute("alumno", alumno);
            model.addAttribute("anotaciones", anotaciones);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String rol = userDetails.getAuthorities().iterator().next().getAuthority();
                model.addAttribute("rol", rol);
            }
            return "ver-anotaciones";
        }
        return "redirect:/profesor/menu";
    }

    @GetMapping("/editar-anotacion/{id}")
    public String editarAnotacionProfesor(@PathVariable("id") String id, Model model, Principal principal) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            model.addAttribute("anotacion", anotacion);
            model.addAttribute("rol", "ROLE_PROFESOR");
            return "editar-anotacion";
        }
        return "redirect:/profesor/menu";
    }

    @PostMapping("/actualizar-anotacion")
    public String actualizarAnotacionProfesor(@RequestParam String id, @RequestParam String texto) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            anotacion.setTexto(texto);
            anotacionRepository.save(anotacion);
        }
        return "redirect:/profesor/menu";
    }

    @GetMapping("/agregar-nota/{id}")
    public String mostrarFormularioNota(@PathVariable("id") String id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id de alumno inválido: " + id));
        Nota nota = new Nota();
        nota.setAlumno(alumno);
        model.addAttribute("nota", nota);
        model.addAttribute("rol", "ROLE_PROFESOR");
        return "nueva-nota";
    }

    @PostMapping("/agregar-nota")
    public String agregarNota(@ModelAttribute("nota") Nota nota, BindingResult result) {
        if (result.hasErrors()) {
            return "nueva-nota";
        }
        notaRepository.save(nota);
        return "redirect:/profesor/menu";
    }

    @GetMapping("/ver-notas/{id}")
    public String verNotas(@PathVariable("id") String id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno != null) {
            List<Nota> notas = notaRepository.findByAlumnoId(id);
            model.addAttribute("alumno", alumno);
            model.addAttribute("notas", notas);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String rol = userDetails.getAuthorities().iterator().next().getAuthority();
                model.addAttribute("rol", rol);
            }
            return "ver-notas";
        }
        return "redirect:/profesor/menu";
    }
}
