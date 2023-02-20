package br.cervejaria.demo.models.mapper;

import br.cervejaria.demo.models.dto.ProdutoDto;
import br.cervejaria.demo.models.entity.Produto;

/*
Aqui utilizei uma classe helper para fazer as criações e atualizações necessárias
 */
public class ProdutoMapper {

    private ProdutoMapper(){}

    /*
    Um conceito legal que permite reaproveitar código é a sobrecarga de metodos
    https://www.javatpoint.com/method-overloading-in-java
     */
    public static Produto fromDtoToEntity(ProdutoDto produtoDto){
        return fromDtoToEntity(null, produtoDto);
    }

    public static Produto fromDtoToEntity(Integer id, ProdutoDto produtoDto){
        return new Produto(
                id,
                produtoDto.getNome(),
                produtoDto.getMarca(),
                produtoDto.getValor(),
                produtoDto.getQuantidade(),
                produtoDto.getDescricao()
        );
    }
    public static Produto updateId(Integer id, Produto produto){
        return new Produto(
                id,
                produto.nome(),
                produto.marca(),
                produto.valor(),
                produto.quantidade(),
                produto.descricao()
        );
    }

}
