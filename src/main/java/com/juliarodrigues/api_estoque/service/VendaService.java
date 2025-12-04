package com.juliarodrigues.api_estoque.service;

import com.juliarodrigues.api_estoque.dto.ItemRequestDTO;
import com.juliarodrigues.api_estoque.dto.SaleRequestDTO;
import com.juliarodrigues.api_estoque.dto.SaleResponseDTO;
import com.juliarodrigues.api_estoque.exception.InsufficientStockException;
import com.juliarodrigues.api_estoque.exception.ResourceNotFoundException;
import com.juliarodrigues.api_estoque.model.*;
import com.juliarodrigues.api_estoque.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;

    public VendaService(VendaRepository vendaRepository,
                        ClienteRepository clienteRepository,
                        ProdutoRepository produtoRepository,
                        ItemVendaRepository itemVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    @Transactional
    public SaleResponseDTO registrarVenda(SaleRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado: " + dto.getClienteId()));

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataHora(LocalDateTime.now());
        venda.setTotal(BigDecimal.ZERO);

        List<ItemVenda> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemRequestDTO itemReq : dto.getItens()) {
            Long prodId = itemReq.getProdutoId();
            Integer qtd = itemReq.getQuantidade();
            if (qtd == null || qtd <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para produto " + prodId);
            }

            Produto produto = produtoRepository.findByIdForUpdate(prodId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + prodId));

            if (produto.getEstoque() == null || produto.getEstoque() < qtd) {
                throw new InsufficientStockException("Estoque insuficiente para produto " + produto.getNome() + " (id=" + prodId + ")");
            }

            produto.setEstoque(produto.getEstoque() - qtd);
            produtoRepository.save(produto);

            java.math.BigDecimal precoUnit = produto.getPreco();
            java.math.BigDecimal itemTotal = precoUnit.multiply(java.math.BigDecimal.valueOf(qtd));
            total = total.add(itemTotal);

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(qtd);
            itemVenda.setPrecoUnitario(precoUnit);
            itemVenda.setVenda(venda);
            itens.add(itemVenda);
        }

        venda.setTotal(total);
        venda.setItens(itens);

        Venda vendaSalva = vendaRepository.save(venda);

        return new SaleResponseDTO(vendaSalva.getId(), vendaSalva.getDataHora(), vendaSalva.getTotal());
    }
}
