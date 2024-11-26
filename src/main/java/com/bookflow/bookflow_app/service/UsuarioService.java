package com.bookflow.bookflow_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.bookflow.bookflow_app.model.Usuario;
import com.bookflow.bookflow_app.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        // validar campos obrigatórios
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome de usuário é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email do usuário é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha é obrigatória.");
        }
        if (usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF é obrigatório.");
        }

        if (!validarCpf(usuario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF inválido.");
        }

        if (usuario.getDataNascimento() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de nascimento é obrigatória.");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(usuario.getCpf());
        if (usuarioExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe usuário cadastrado com este CPF.");
        }
    }

    private boolean validarCpf(String cpf) {
        // Verifica se o CPF possui exatamente 11 dígitos numéricos
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Verifica se todos os números do CPF são iguais (ex: 111.111.111-11)
        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
                || cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            return false;
        }

        // Valida os dois últimos dígitos verificadores
        int soma = 0;
        int peso = 10;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }
        int primeiroDigito = (soma * 10) % 11;
        if (primeiroDigito == 10) {
            primeiroDigito = 0;
        }
        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        soma = 0;
        peso = 11;

        // Calcula o segundo dígito verificador
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
        }
        int segundoDigito = (soma * 10) % 11;
        if (segundoDigito == 10) {
            segundoDigito = 0;
        }
        if (segundoDigito != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    public List<Usuario> listarTodosOsUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    @Transactional
    public Usuario atualizarUsuario(String cpf, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = buscarUsuarioPorCpf(cpf);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());
            return usuarioRepository.save(usuario);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    @Transactional
    public void deletarUsuario(String cpf) {
        Optional<Usuario> usuario = buscarUsuarioPorCpf(cpf);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(cpf);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    public Usuario validarCredenciais(String cpf, String senha) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(cpf);

        if (usuarioExistente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
        }

        Usuario usuario = usuarioExistente.get();

        if (!usuario.getSenha().equals(senha)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
        }

        return usuario; 
    }
}
