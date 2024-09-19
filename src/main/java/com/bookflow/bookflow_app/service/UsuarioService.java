package com.bookflow.bookflow_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Usuario;
import com.bookflow.bookflow_app.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario){
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    private void validarUsuario(Usuario usuario){
        //validar campos obrigatórios
        if(usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O nome de usuário é obrigatório.");
        }
        if (usuario.getLogin() == null || usuario.getLogin().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O login do usuário é obrigatório.");      
        }
        if (usuario.getSenha() == null|| usuario.getSenha().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O nome de usuário é obrigatório.");
        }
        Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
        if(usuarioExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Já existe usuário cadastrado com este login.");
        }
    }

    public List<Usuario> listarTodosOsUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(long id){
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = buscarUsuarioPorId(id);

        if(usuarioExistente.isPresent()){
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setLogin(usuarioAtualizado.getLogin());
            usuario.setSenha(usuarioAtualizado.getSenha());
            return usuarioRepository.save(usuario);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário não encontrado!");            
        }
    }

    public void deletarUsuario(Long id) {
        Optional<Usuario> usuario = buscarUsuarioPorId(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
}
