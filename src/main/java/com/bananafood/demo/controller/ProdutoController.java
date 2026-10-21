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
        model.addAttribute("produto", new Produto());
        model.addAttribute("isEdicao", false);
        model.addAttribute("tags", produtoService.listarTags());
        model.addAttribute("usuarios", produtoService.listarUsuarios());
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
    public String salvar(@Valid Produto produto, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdicao", false);
            model.addAttribute("tags", produtoService.listarTags());
            model.addAttribute("usuarios", produtoService.listarUsuarios());
            return "cadastro";
        }
        produtoService.cadastrar(produto);
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
        model.addAttribute("tags", produtoService.listarTags());
        model.addAttribute("usuarios", produtoService.listarUsuarios());
        return "cadastro";
    }

    @PostMapping("/produtos/{id}/editar")
    public String atualizar(@PathVariable Long id, @Valid Produto produto, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            produto.setId(id);
            model.addAttribute("isEdicao", true);
            model.addAttribute("tags", produtoService.listarTags());
            model.addAttribute("usuarios", produtoService.listarUsuarios());
            return "cadastro";
        }
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        if (produtoAtualizado == null) {
            redirectAttributes.addFlashAttribute("erro", "Sobremesa não encontrada para edição.");
            return "redirect:/menu";
        }
        redirectAttributes.addFlashAttribute("sucesso",
                "Sobremesa \"" + produtoAtualizado.getNome() + "\" atualizada com sucesso!");
        return "redirect:/menu";
    }

    @PostMapping({ "/produtos/{id}/excluir", "/produtos/excluir/{id}" })
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Produto produtoEncontrado = produtoService.buscarPorId(id);
        if (produtoEncontrado == null) {
            redirectAttributes.addFlashAttribute("erro", "Sobremesa não encontrada para exclusão.");
            return "redirect:/menu";
        }
        boolean foiExcluidoComSucesso = produtoService.excluir(id);
        if (foiExcluidoComSucesso) {
            redirectAttributes.addFlashAttribute("sucesso",
                    "Sobremesa \"" + produtoEncontrado.getNome() + "\" removida do cardápio!");
        } else {
            redirectAttributes.addFlashAttribute("erro",
                    "Sobremesas em destaque não podem ser excluídas. Remova o destaque antes de excluir.");
        }
        return "redirect:/menu";
    }
}
