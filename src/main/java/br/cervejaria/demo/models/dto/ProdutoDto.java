package br.cervejaria.demo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {
    private String nome;
    private String marca;
    private Double valor;
    private String quantidade;
    private String descricao;

}
