package com.juliarodrigues.api_estoque.repository;

import com.juliarodrigues.api_estoque.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
