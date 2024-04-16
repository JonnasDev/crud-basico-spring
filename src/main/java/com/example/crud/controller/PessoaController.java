package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.crud.entity.PessoaEntity;
import com.example.crud.repository.PessoaRepository;
import com.example.crud.exceptions.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<PessoaEntity> listarPessoas() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaEntity> getPessoaById(@PathVariable Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> ResponseEntity.ok().body(pessoa))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaEntity> criarPessoa(@RequestBody PessoaEntity pessoa) {
      PessoaEntity novaPessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaEntity> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaEntity pessoa) {
        PessoaEntity pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));

        pessoaExistente.setNome(pessoa.getNome());
        pessoaExistente.setIdade(pessoa.getIdade());
        PessoaEntity pessoaAtualizada = pessoaRepository.save(pessoaExistente);
        return ResponseEntity.ok().body(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPessoa(@PathVariable Long id) {
        pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada: " + id));

        pessoaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}