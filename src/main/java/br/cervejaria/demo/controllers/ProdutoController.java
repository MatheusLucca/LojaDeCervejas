package br.cervejaria.demo.controllers;


import br.cervejaria.demo.models.dto.ProdutoDto;
import br.cervejaria.demo.models.entity.Produto;
import br.cervejaria.demo.models.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    ProdutoRepository produtoRepository = new ProdutoRepository();

    @GetMapping
    public ResponseEntity<Object> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduto(@PathVariable Integer id) {
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        if(!produtoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoExists.get());
    }
    @PostMapping
    public ResponseEntity<Object> saveProdutos(@RequestBody ProdutoDto produtoDto) {
        Produto newProduto = new Produto();
        newProduto.setNome(produtoDto.getNome());
        newProduto.setMarca(produtoDto.getMarca());
        newProduto.setQuantidade(produtoDto.getQuantidade());
        newProduto.setValor(produtoDto.getValor());
        newProduto.setDescricao(produtoDto.getDescricao());

        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(newProduto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduto(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto){
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        if(!produtoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto");
        }

        Produto produtoEntity = produtoExists.get();

        produtoEntity.setNome(produtoDto.getNome());
        produtoEntity.setMarca(produtoDto.getMarca());
        produtoEntity.setQuantidade(produtoDto.getQuantidade());
        produtoEntity.setValor(produtoDto.getValor());
        produtoEntity.setDescricao(produtoDto.getDescricao());

        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto (@PathVariable Integer id){
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        if(!produtoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível encontrar o produto");
        }

        produtoRepository.delete(produtoExists.get());

        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }
}
