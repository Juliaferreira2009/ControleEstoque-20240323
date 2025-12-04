package com.juliarodrigues.api_estoque.controller;

import com.juliarodrigues.api_estoque.dto.SaleRequestDTO;
import com.juliarodrigues.api_estoque.dto.SaleResponseDTO;
import com.juliarodrigues.api_estoque.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> registrarVenda(@RequestBody SaleRequestDTO dto) {
        SaleResponseDTO response = vendaService.registrarVenda(dto);
        return ResponseEntity.ok(response);
    }
}
