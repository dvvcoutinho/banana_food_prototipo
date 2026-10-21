package com.bananafood.demo.service;

import com.bananafood.demo.model.Usuario;
import com.bananafood.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return usuarioRepository.findById(id).orElse(null);
    }

    public boolean emailJaCadastrado(String email) {
        if (email == null) {
            return false;
        }
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente == null) {
            return null;
        }
        usuarioExistente.setNome(dadosAtualizados.getNome());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());
        usuarioExistente.setSenha(dadosAtualizados.getSenha());
        return usuarioRepository.save(usuarioExistente);
    }

    public boolean excluir(Long id) {
        if (buscarPorId(id) == null) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || senha == null) return false;
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        return usuario != null && senha.equals(usuario.getSenha());
    }
}
