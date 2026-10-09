package com.bananafood.demo.service;

import com.bananafood.demo.model.Usuario;
import com.bananafood.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        if (usuarioRepository.count() == 0) {
            salvar(new Usuario("Davi Cliente", "davi@exemplo.com", "123456", "toffee"));
        }
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null) return Optional.empty();
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || senha == null) return false;
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent() && senha.equals(usuarioOpt.get().getSenha());
    }
}


