package com.bananafood.demo.service;

import com.bananafood.demo.model.Produto;
import com.bananafood.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        if (produtoRepository.count() == 0) {
            salvar(new Produto(
                    null,
                    "Banoffee Suprema com Calda de Toffee Dourado",
                    "doces",
                    16.50,
                    "🍌",
                    "Camadas crocantes de biscoito, banana fresca selecionada, doce de leite artesanal e calda toffee escorrendo.",
                    "Toffee Dourado",
                    true
            ));

            salvar(new Produto(
                    null,
                    "Bolo de Banana Caramelizada com Calda Quente",
                    "bolos",
                    26.90,
                    "🎂",
                    "Massa fofinha com centro quente e erupção de calda cremosa e bananas flambadas.",
                    "Caramelo Quente",
                    true
            ));

            salvar(new Produto(
                    null,
                    "Panquecas Americanas de Banana com Mel Dourado",
                    "sobremesas",
                    19.50,
                    "🥞",
                    "Torre de panquecas fofas com fatias de banana e cascata de mel silvestre puro com brilho.",
                    "Mel Nobre",
                    false
            ));

            salvar(new Produto(
                    null,
                    "Banana Split Suprema com Calda Duo Chocolate & Caramelo",
                    "sobremesas",
                    24.90,
                    "🍨",
                    "Sorvetes cremosos com banana fatiada e cascata de chocolate nobre e caramelo salgado.",
                    "Duo Chocolate & Caramelo",
                    true
            ));

            salvar(new Produto(
                    null,
                    "Waffle Belga com Banana Flambada e Canela",
                    "sobremesas",
                    22.00,
                    "🧇",
                    "Waffle dourado e crocante coberto por bananas douradas na manteiga e calda de açúcar mascavo.",
                    "Caramelo de Canela",
                    false
            ));

            salvar(new Produto(
                    null,
                    "Pudim Tradicional com Calda Espelhada de Caramelo",
                    "doces",
                    14.00,
                    "🍮",
                    "Pudim aveludado sem furinhos regado por uma farta calda de açúcar caramelizado brilhante.",
                    "Caramelo Espelhado",
                    false
            ));

            salvar(new Produto(
                    null,
                    "Smoothie de Banana & Morango com Borda de Calda",
                    "bebidas",
                    15.00,
                    "🥤",
                    "Bebida ultra cremosa e refrescante servida em copo decorado com calda de frutas vermelhas.",
                    "Frutas Vermelhas",
                    false
            ));

            salvar(new Produto(
                    null,
                    "Cappuccino Banana Toffee Cremoso",
                    "bebidas",
                    13.90,
                    "☕",
                    "Café expresso especial com leite vaporizado, aroma suave de banana e fios de calda toffee.",
                    "Toffee Especial",
                    false
            ));
        }
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarDestaques() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return produtoRepository.findById(id).orElse(null);
    }

    public List<Produto> filtrar(String categoria, String busca) {
        return produtoRepository.filtrar(categoria, busca);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        if (produto.getEmoji() == null || produto.getEmoji().trim().isEmpty()) {
            produto.setEmoji(definirEmojiPadrao(produto.getCategoria()));
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public boolean excluir(Long id) {
        if (id != null && produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String definirEmojiPadrao(String categoria) {
        if (categoria == null) return "🍌";
        return switch (categoria.toLowerCase()) {
            case "bolos" -> "🎂";
            case "sobremesas" -> "🥞";
            case "bebidas" -> "🥤";
            default -> "🍌";
        };
    }
}


