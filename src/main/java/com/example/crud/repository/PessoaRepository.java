package com.example.crud.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.entity.PessoaEntity;

public interface PessoaRepository extends JpaRepository<PessoaEntity, Long>{
  
}
