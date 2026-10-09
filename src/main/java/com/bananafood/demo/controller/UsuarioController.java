package com.bananafood.demo.controller;

import com.bananafood.demo.model.Usuario;
import com.bananafood.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/login-cadastro", "/login", "/cadastro"})
    public String loginCadastro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "login-cadastro";
    }

    @PostMapping("/cadastrar-usuario")
    public String cadastrar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "login-cadastro";
        }
        usuarioService.salvar(usuario);
        redirectAttributes.addFlashAttribute("sucesso", "Conta criada com sucesso para " + usuario.getNome() + "! Agora você pode entrar.");
        return "redirect:/login-cadastro?aba=login";
    }
}

