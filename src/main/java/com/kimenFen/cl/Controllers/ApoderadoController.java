package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.Alumno;
import com.kimenFen.cl.Model.Anotacion;
import com.kimenFen.cl.Model.Apoderado;
import com.kimenFen.cl.Model.Nota;
import com.kimenFen.cl.Repository.AlumnoRepository;
import com.kimenFen.cl.Repository.AnotacionRepository;
import com.kimenFen.cl.Repository.ApoderadoRepository;
import com.kimenFen.cl.Repository.NotaRepository;
import com.kimenFen.cl.Service.ApoderadoService;
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
import java.util.Optional;

@Controller
@RequestMapping("/apoderado")
public class ApoderadoController {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private ApoderadoRepository apoderadoRepository;
    @Autowired
    private AnotacionRepository anotacionRepository;
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private ApoderadoService apoderadoService;

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

    @GetMapping("/alumnos-apoderado")
    public String listarAlumnosApoderado(Model model) {
        // Obtener el usuario actual
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        // Buscar el apoderado por su rut (username)
        Optional<Apoderado> apoderadoOptional = apoderadoRepository.findByRut(username);
        if (apoderadoOptional.isPresent()) {
            Apoderado apoderado = apoderadoOptional.get();
            // Obtener la lista de alumnos asociados al apoderado
            List<Alumno> alumnos = alumnoRepository.findByApoderado(apoderado);
            model.addAttribute("alumnos", alumnos);
            model.addAttribute("rol", "ROLE_APODERADO");
        } else {
            model.addAttribute("alumnos", List.of());
        }

        return "lista-alumnos-asociados";
    }

}
