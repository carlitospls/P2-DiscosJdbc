package es.daw.discosjdbc.controller;

import es.daw.discosjdbc.dto.DiscoForm;
import es.daw.discosjdbc.exception.DiscoDuplicadoException;
import es.daw.discosjdbc.model.Disco;
import es.daw.discosjdbc.service.GeneroService;
import es.daw.discosjdbc.service.DiscoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// URLs: GET /discos | GET /discos/nuevo | GET /discos/editar/{codigo}
//       POST /discos/guardar | POST /discos/borrar/{codigo}
@Controller
@RequestMapping("/discos")
@RequiredArgsConstructor
public class DiscoController {

    private final DiscoService discoService;
    private final GeneroService generoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("discos", discoService.listar());
        return "discos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("discoForm", new DiscoForm());
        model.addAttribute("generos", generoService.listar());
        return "discos/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) {
        Disco disco = discoService.buscarPorCodigo(codigo);
        model.addAttribute("discoForm", DiscoForm.toDto(disco));
        model.addAttribute("generos", generoService.listar());
        return "discos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("discoForm") DiscoForm discoForm,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("generos", generoService.listar());
            return "discos/formulario";
        }

        try {
            if (discoForm.getCodigo() == null) {
                Disco creado = discoService.crear(discoForm.toModelEntity());
                redirectAttributes.addFlashAttribute("mensaje",
                        "Disco creado con código " + creado.getCodigo());
            } else {
                discoService.actualizar(discoForm.toModelEntity());
                redirectAttributes.addFlashAttribute("mensaje",
                        "Disco " + discoForm.getNombre() + " actualizado");
            }
        }
        catch(DiscoDuplicadoException e){
                result.rejectValue("nombre", "", e.getMessage());
                return "discos/formulario";
            }
            return "redirect:/discos";
        }

    @PostMapping("/borrar/{codigo}")
    public String borrar(@PathVariable Integer codigo, RedirectAttributes redirectAttributes) {
        discoService.borrar(codigo);
        redirectAttributes.addFlashAttribute("mensaje", "Disco " + codigo + " borrado");
        return "redirect:/discos";
    }
}

