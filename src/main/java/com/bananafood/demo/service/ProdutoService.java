package com.bananafood.demo.service;

import com.bananafood.demo.model.Produto;
import com.bananafood.demo.model.Tag;
import com.bananafood.demo.model.Usuario;
import com.bananafood.demo.repository.ProdutoRepository;
import com.bananafood.demo.repository.TagRepository;
import com.bananafood.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          TagRepository tagRepository,
                          UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarDestaques() {
        return produtoRepository.findAll();
    }

    public List<Tag> listarTags() {
        return tagRepository.findAll();
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
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

    public Produto cadastrar(Produto produto) {
        if (produto.getStatus() == null || produto.getStatus().trim().isEmpty()) {
            produto.setStatus("DISPONIVEL");
        }
        if (produto.getEmoji() == null || produto.getEmoji().trim().isEmpty()) {
            produto.setEmoji(definirEmojiPadrao(produto.getCategoria()));
        }
        produto.setUsuario(buscarUsuario(produto.getUsuario()));
        produto.setTags(buscarTags(produto.getTags()));
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto dadosAtualizados) {
        Produto produtoExistente = buscarPorId(id);
        if (produtoExistente == null) {
            return null;
        }
        produtoExistente.setNome(dadosAtualizados.getNome());
        produtoExistente.setCategoria(dadosAtualizados.getCategoria());
        produtoExistente.setPreco(dadosAtualizados.getPreco());
        produtoExistente.setEmoji(dadosAtualizados.getEmoji());
        produtoExistente.setDescricao(dadosAtualizados.getDescricao());
        produtoExistente.setCalda(dadosAtualizados.getCalda());
        produtoExistente.setDestaque(dadosAtualizados.isDestaque());
        produtoExistente.setUsuario(buscarUsuario(dadosAtualizados.getUsuario()));
        produtoExistente.setTags(buscarTags(dadosAtualizados.getTags()));
        return produtoRepository.save(produtoExistente);
    }

    public boolean excluir(Long id) {
        Produto produto = buscarPorId(id);
        if (produto == null || produto.isDestaque()) {
            return false;
        }
        produtoRepository.deleteById(id);
        return true;
    }

    private Usuario buscarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        return usuarioRepository.findById(usuario.getId()).orElse(null);
    }

    private List<Tag> buscarTags(List<Tag> tags) {
        List<Tag> tagsEncontradas = new ArrayList<>();
        if (tags == null) {
            return tagsEncontradas;
        }
        for (Tag tag : tags) {
            if (tag != null && tag.getId() != null) {
                Tag tagEncontrada = tagRepository.findById(tag.getId()).orElse(null);
                if (tagEncontrada != null) {
                    tagsEncontradas.add(tagEncontrada);
                }
            }
        }
        return tagsEncontradas;
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
