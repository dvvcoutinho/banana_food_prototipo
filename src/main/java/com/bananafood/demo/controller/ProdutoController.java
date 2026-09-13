package com.bananafood.demo.controller;

import com.bananafood.demo.model.Produto;
import com.bananafood.demo.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/menu")
    public String menu(
            @RequestParam(name = "categoria", defaultValue = "todos") String categoria,
            @RequestParam(name = "busca", required = false) String busca,
            Model model
    ) {
        List<Produto> produtos = produtoService.filtrar(categoria, busca);
        model.addAttribute("produtos", produtos);
        model.addAttribute("categoriaAtiva", categoria);
        model.addAttribute("termoBusca", busca != null ? busca : "");
        model.addAttribute("totalProdutos", produtos.size());
        return "menu";
    }

    @GetMapping("/produtos/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            return "redirect:/menu";
        }
        model.addAttribute("produto", produto);
        return "detalhes";
    }

    @GetMapping("/produtos/novo")
    public String novoFormulario(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new Produto());
        }
        return "cadastro";
    }

    @PostMapping("/produtos")
    public String salvar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Sobremesa \"" + produto.getNome() + "\" cadastrada com sucesso no cardápio!");
        return "redirect:/menu";
    }

    @PostMapping("/produtos/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            produtoService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Sobremesa \"" + produto.getNome() + "\" removida do cardápio!");
        }
        return "redirect:/menu";
    }
}
