package br.com.bonslivros.BonsLivros.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PaginaInicial {
    @RequestMapping("/")
    @ResponseBody
    public String paginaInicial() {
        return "Olá Mundo!";
    }

    @RequestMapping("/ola")
    @ResponseBody
    public String paginaInicial(String nome) {
        return "Olá " + nome + "!";
    }
}
