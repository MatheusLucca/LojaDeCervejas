package br.cervejaria.demo.models.entity;

public record Produto(
        Integer id,
        String nome,
        String marca,
        Double valor,
        String quantidade,
        String descricao
) {
}
