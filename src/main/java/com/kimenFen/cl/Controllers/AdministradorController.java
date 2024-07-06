package com.kimenFen.cl.Controllers;

import com.kimenFen.cl.Model.*;
import com.kimenFen.cl.Repository.*;
import com.kimenFen.cl.Service.NotaService;
import com.kimenFen.cl.Security.SecurityConfig;
import com.kimenFen.cl.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private NotaService notaService;



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
    public String guardarProfesor(@ModelAttribute Profesor profesor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevo-profesor";
        }

        String rut = profesor.getRut().replace(".", "").replace("-", "");
        if (!Modulo11.verificar(rut)) {
            model.addAttribute("error", "RUT inválido");
            return "nuevo-profesor";
        }

        profesorRepository.save(profesor);
        //securityConfig.createUser(profesor.getRut(), "PROFESOR");
        return "redirect:/administrador/menu";
    }

    @PostMapping("/apoderados/guardar")
    public String guardarApoderado(@ModelAttribute Apoderado apoderado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevo-apoderado";
        }

        String rut = apoderado.getRut().replace(".", "").replace("-", "");
        if (!Modulo11.verificar(rut)) {
            model.addAttribute("error", "RUT inválido");
            return "nuevo-apoderado";
        }

        apoderadoRepository.save(apoderado);
        //securityConfig.createUser(apoderado.getRut(), "APODERADO");
        return "redirect:/administrador/menu";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute("alumno") Alumno alumno) {
        alumnoRepository.save(alumno);
        return "redirect:/administrador/menu";
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

    @PostMapping("/asociar-alumno")
    public String asociarAlumnoApoderado(@RequestParam("alumnoId") String alumnoId,
                                         @RequestParam("apoderadoId") String apoderadoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        Apoderado apoderado = apoderadoRepository.findById(apoderadoId).orElse(null);
        if (alumno != null && apoderado != null) {
            alumno.setApoderado(apoderado);
            alumnoRepository.save(alumno);
        }
        return "redirect:/administrador/menu";
    }

    @GetMapping("/editar-alumno/{id}")
    public String editarAlumno(@PathVariable("id") String id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            return "redirect:/administrador/alumnos";
        }
        model.addAttribute("alumno", alumno);
        return "editar-alumno";
    }
    @GetMapping("/editar-apoderado/{id}")
    public String editarApoderado(@PathVariable("id") String id, Model model) {
        Apoderado apoderado = apoderadoRepository.findById(id).orElse(null);
        if (apoderado == null) {
            return "redirect:/administrador/apoderados";
        }
        model.addAttribute("apoderado", apoderado);
        return "editar-apoderado";
    }

    @GetMapping("/editar-profesor/{id}")
    public String editarProfesor(@PathVariable("id") String id, Model model) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return "redirect:/administrador/profesores";
        }
        model.addAttribute("profesor", profesor);
        return "editar-profesor";
    }



    @GetMapping("/editar-anotacion/{id}")
    public String editarAnotacionAdmin(@PathVariable("id") String id, Model model, Principal principal) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            model.addAttribute("anotacion", anotacion);
            model.addAttribute("rol", "ROLE_ADMIN");
            return "editar-anotacion";
        }
        return "redirect:/administrador/menu";
    }

    @PostMapping("/actualizar-anotacion")
    public String actualizarAnotacionAdmin(@RequestParam String id, @RequestParam String texto) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            anotacion.setTexto(texto);
            anotacionRepository.save(anotacion);
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
    public String eliminarAlumno(@PathVariable("id") String id) {
        alumnoRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-apoderado/{id}")
    public String eliminarApoderado(@PathVariable("id") String id) {
        apoderadoRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }


    @GetMapping("/eliminar-profesor/{id}")
    public String eliminarProfesor(@PathVariable("id") String id) {
        profesorRepository.deleteById(id);
        return "redirect:/administrador/menu";
    }

    @GetMapping("/eliminar-anotacion/{id}")
    public String eliminarAnotacion(@PathVariable("id") String id) {
        Anotacion anotacion = anotacionRepository.findById(id).orElse(null);
        if (anotacion != null) {
            alumnoRepository.findById(anotacion.getAlumno().getId()).ifPresent(alumno -> {
                alumno.getAnotaciones().remove(anotacion);
                alumnoRepository.save(alumno);
            });
            anotacionRepository.deleteById(id);
        }
        return "redirect:/administrador/menu";
    }

    @GetMapping("/agregar-nota/{id}")
    public String mostrarFormularioNota(@PathVariable("id") String id, Model model) {
        Alumno alumno = alumnoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id de alumno inválido: " + id));
        Nota nota = new Nota();
        nota.setAlumno(alumno);
        model.addAttribute("nota", nota);
        model.addAttribute("rol", "ROLE_ADMIN");
        return "nueva-nota";
    }

    @PostMapping("/agregar-nota")
    public String agregarNota(@ModelAttribute("nota") Nota nota, BindingResult result) {
        if (result.hasErrors()) {
            return "nueva-nota";
        }
        notaRepository.save(nota);
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

    @GetMapping("/ver-asociaciones")
    public String verAsociaciones(Model model) {
        List<Alumno> alumnosConApoderados = alumnoRepository.findAll();
        model.addAttribute("alumnosConApoderados", alumnosConApoderados);
        return "ver-asociaciones";
    }


}
