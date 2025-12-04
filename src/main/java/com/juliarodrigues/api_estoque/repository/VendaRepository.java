package com.juliarodrigues.api_estoque.repository;

import com.juliarodrigues.api_estoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
