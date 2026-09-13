package com.bananafood.demo.service;

import com.bananafood.demo.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final List<Produto> produtos = new CopyOnWriteArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public ProdutoService() {
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        salvar(new Produto(
                sequence.incrementAndGet(),
                "Banoffee Suprema com Calda de Toffee Dourado",
                "doces",
                16.50,
                "🍌",
                "Camadas crocantes de biscoito, banana fresca selecionada, doce de leite artesanal e calda toffee escorrendo.",
                "Toffee Dourado",
                true
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Bolo de Banana Caramelizada com Calda Quente",
                "bolos",
                26.90,
                "🎂",
                "Massa fofinha com centro quente e erupção de calda cremosa e bananas flambadas.",
                "Caramelo Quente",
                true
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Panquecas Americanas de Banana com Mel Dourado",
                "sobremesas",
                19.50,
                "🥞",
                "Torre de panquecas fofas com fatias de banana e cascata de mel silvestre puro com brilho.",
                "Mel Nobre",
                false
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Banana Split Suprema com Calda Duo Chocolate & Caramelo",
                "sobremesas",
                24.90,
                "🍨",
                "Sorvetes cremosos com banana fatiada e cascata de chocolate nobre e caramelo salgado.",
                "Duo Chocolate & Caramelo",
                true
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Waffle Belga com Banana Flambada e Canela",
                "sobremesas",
                22.00,
                "🧇",
                "Waffle dourado e crocante coberto por bananas douradas na manteiga e calda de açúcar mascavo.",
                "Caramelo de Canela",
                false
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Pudim Tradicional com Calda Espelhada de Caramelo",
                "doces",
                14.00,
                "🍮",
                "Pudim aveludado sem furinhos regado por uma farta calda de açúcar caramelizado brilhante.",
                "Caramelo Espelhado",
                false
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Smoothie de Banana & Morango com Borda de Calda",
                "bebidas",
                15.00,
                "🥤",
                "Bebida ultra cremosa e refrescante servida em copo decorado com calda de frutas vermelhas.",
                "Frutas Vermelhas",
                false
        ));

        salvar(new Produto(
                sequence.incrementAndGet(),
                "Cappuccino Banana Toffee Cremoso",
                "bebidas",
                13.90,
                "☕",
                "Café expresso especial com leite vaporizado, aroma suave de banana e fios de calda toffee.",
                "Toffee Especial",
                false
        ));
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    public List<Produto> listarDestaques() {
        return produtos.stream()
                .filter(Produto::isDestaque)
                .collect(Collectors.toList());
    }

    public Produto buscarPorId(Long id) {
        if (id == null) return null;
        return produtos.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    public List<Produto> filtrar(String categoria, String busca) {
        return produtos.stream()
                .filter(p -> {
                    boolean matchCategoria = (categoria == null || categoria.isBlank() || "todos".equalsIgnoreCase(categoria))
                            || categoria.equalsIgnoreCase(p.getCategoria());

                    boolean matchBusca = (busca == null || busca.isBlank())
                            || p.getNome().toLowerCase().contains(busca.toLowerCase())
                            || p.getDescricao().toLowerCase().contains(busca.toLowerCase())
                            || p.getCalda().toLowerCase().contains(busca.toLowerCase());

                    return matchCategoria && matchBusca;
                })
                .collect(Collectors.toList());
    }

    public void salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(sequence.incrementAndGet());
            if (produto.getEmoji() == null || produto.getEmoji().isBlank()) {
                produto.setEmoji(definirEmojiPadrao(produto.getCategoria()));
            }
            produtos.add(produto);
        } else {
            for (int i = 0; i < produtos.size(); i++) {
                if (produtos.get(i).getId().equals(produto.getId())) {
                    produtos.set(i, produto);
                    return;
                }
            }
            produtos.add(produto);
        }
    }

    public void excluir(Long id) {
        produtos.removeIf(p -> p.getId().equals(id));
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
