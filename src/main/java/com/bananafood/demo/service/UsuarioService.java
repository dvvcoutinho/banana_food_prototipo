package com.bananafood.demo.service;

import com.bananafood.demo.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new CopyOnWriteArrayList<>();

    public UsuarioService() {
        usuarios.add(new Usuario("Davi Cliente", "davi@exemplo.com", "123456", "toffee"));
    }

    public List<Usuario> listarTodos() {
        return usuarios;
    }

    public void salvar(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || senha == null) return false;
        return usuarios.stream()
                .anyMatch(u -> email.equalsIgnoreCase(u.getEmail()) && senha.equals(u.getSenha()));
    }
}
