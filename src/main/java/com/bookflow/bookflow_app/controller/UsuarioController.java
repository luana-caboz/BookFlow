package com.bookflow.bookflow_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookflow.bookflow_app.model.Usuario;
import com.bookflow.bookflow_app.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);    
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosOsUsuarios(){
        List<Usuario> usuarios = usuarioService.listarTodosOsUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Usuario> buscarUsuarioPorCpf(@PathVariable String cpf){
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorCpf(cpf);
        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String cpf, @RequestBody Usuario usuarioAtualizado){
        try{
            Usuario usuario = usuarioService.atualizarUsuario(cpf, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String cpf) {
        usuarioService.deletarUsuario(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
