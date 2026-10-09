package com.bananafood.demo.controller;

import com.bananafood.demo.model.Produto;
import com.bananafood.demo.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
            Model model) {
        List<Produto> produtos = produtoService.filtrar(categoria, busca);
        model.addAttribute("produtos", produtos);
        model.addAttribute("categoriaAtiva", categoria);
        model.addAttribute("termoBusca", busca != null ? busca : "");
        model.addAttribute("totalProdutos", produtos.size());
        return "menu";
    }

    @GetMapping("/produtos/novo")
    public String novoFormulario(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new Produto());
        }
        model.addAttribute("isEdicao", false);
        return "cadastro";
    }

    @GetMapping("/produtos/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Sobremesa não encontrada no cardápio.");
            return "redirect:/menu";
        }
        model.addAttribute("produto", produto);
        return "detalhes";
    }

    @PostMapping({ "/produtos", "/produtos/novo" })
    public String salvar(@Valid @ModelAttribute("produto") Produto produto, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdicao", false);
            return "cadastro";
        }
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("sucesso",
                "Sobremesa \"" + produto.getNome() + "\" cadastrada com sucesso no cardápio!");
        return "redirect:/menu";
    }

    @GetMapping("/produtos/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Sobremesa não encontrada para edição.");
            return "redirect:/menu";
        }
        model.addAttribute("produto", produto);
        model.addAttribute("isEdicao", true);
        return "cadastro";
    }

    @PostMapping("/produtos/{id}/editar")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("produto") Produto produto,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            produto.setId(id);
            model.addAttribute("isEdicao", true);
            return "cadastro";
        }
        produto.setId(id);
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("sucesso",
                "Sobremesa \"" + produto.getNome() + "\" atualizada com sucesso!");
        return "redirect:/menu";
    }

    @PostMapping({ "/produtos/{id}/excluir", "/produtos/excluir/{id}" })
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            produtoService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso",
                    "Sobremesa \"" + produto.getNome() + "\" removida do cardápio!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Sobremesa não encontrada para exclusão.");
        }
        return "redirect:/menu";
    }
}
