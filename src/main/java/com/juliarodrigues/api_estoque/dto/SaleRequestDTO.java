package com.juliarodrigues.api_estoque.dto;

import java.util.List;

public class SaleRequestDTO {

    private Long clienteId;
    private List<ItemRequestDTO> itens;

    public SaleRequestDTO() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public List<ItemRequestDTO> getItens() { return itens; }
    public void setItens(List<ItemRequestDTO> itens) { this.itens = itens; }
}
