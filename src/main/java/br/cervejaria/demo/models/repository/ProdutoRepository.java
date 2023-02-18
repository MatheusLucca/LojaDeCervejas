package br.cervejaria.demo.models.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import br.cervejaria.demo.models.entity.Produto;
import br.cervejaria.demo.models.mapper.ProdutoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository {

    /*
    Aqui você tem um problema de performance, pois utilizar lista te obriga a percorrer todos os objetos para buscar o que você precisa
    para esse cenário em que você tem um id como chave uma tabela hash é uma estrutura de dados melhor para você trabalhar
    Por isso mude aqui para HashMap
    Procure sobre as diferenças entre map, list, set, ex: https://www.java67.com/2013/01/difference-between-set-list-and-map-in-java.html#:~:text=List%20in%20Java%20provides%20ordered,key%2Dvalue%20pair%20and%20hashing.

    Essa alteração até facilitou a implementação das operações aqui
     */
    private final Map<Integer, Produto> produtos = new TreeMap<>();

    /*
    Criando uma variavel de sequence te permite não precisar buscar qual o produto de maior Id
     */
    private Integer sequence = 1;

    public List<Produto>findAll(){
        return produtos.values().stream().toList();
    }

    public Produto findById(Integer id){
        return produtos.get(id);
    }


    public Produto save(Produto newProduto){
        int index = -1;
        if (newProduto.id() == null){
            newProduto = ProdutoMapper.updateId(sequence, newProduto);
            produtos.put(sequence, newProduto);
            sequence++;
        }else{
            produtos.put(index, newProduto);
        }
        return newProduto;
    }

    public void delete(Produto produto){
        produtos.remove(produto.id());
    }

}
