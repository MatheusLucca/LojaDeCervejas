package br.cervejaria.demo.models.repository;

import java.util.ArrayList;
import java.util.List;
import br.cervejaria.demo.models.entity.Produto;
import br.cervejaria.demo.models.dto.ProdutoDto;

public class ProdutoRepository {
    private List<Produto> produtos = new ArrayList<Produto>();

    public List<Produto>findAll(){
        return produtos;
    }

    public Produto findById(Integer id){
        for(Produto produto: produtos){
            if(produto.getId().equals(id)){
                return produto;
            }
        }
        return null;
    }


    public Produto save(Produto newProduto){
        int index = -1;
        if (newProduto.getId() == null){
            if (produtos.isEmpty()){
                newProduto.setId(1);
            }else {
                newProduto.setId(produtos.get(produtos.size()-1).getId() + 1);
            }

            produtos.add(newProduto);
        }else{
            index = produtos.indexOf(newProduto);
            if(index != -1){
                produtos.set(index, newProduto);
            }
        }
        return newProduto;
    }

    public void delete(Produto produto){
        produtos.remove(produto);
    }

}
