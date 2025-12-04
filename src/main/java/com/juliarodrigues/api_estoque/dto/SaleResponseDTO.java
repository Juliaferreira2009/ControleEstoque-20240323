package com.juliarodrigues.api_estoque.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaleResponseDTO {
    private Long vendaId;
    private LocalDateTime dataHora;
    private BigDecimal total;

    public SaleResponseDTO() {}

    public SaleResponseDTO(Long vendaId, LocalDateTime dataHora, BigDecimal total) {
        this.vendaId = vendaId;
        this.dataHora = dataHora;
        this.total = total;
    }

    public Long getVendaId() { return vendaId; }
    public void setVendaId(Long vendaId) { this.vendaId = vendaId; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
