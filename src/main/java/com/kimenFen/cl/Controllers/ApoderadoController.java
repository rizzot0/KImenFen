package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Model.Nota;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.AnotacionRepository;
import com.kimenFen.cl.Repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/apoderado")
public class ApoderadoController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AnotacionRepository anotacionRepository;
    @Autowired
    private NotaRepository notaRepository;

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
        return "redirect:/administrador/menu";
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
        return "redirect:/administrador/alumnos";
    }

}
