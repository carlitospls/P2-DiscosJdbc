package es.daw.discosjdbc.controller;

import es.daw.discosjdbc.service.GeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("generos", generoService.listar());
        return "generos/lista";
    }
}
