package br.cervejaria.demo.controllers;


import br.cervejaria.demo.models.dto.ProdutoDto;
import br.cervejaria.demo.models.entity.Produto;
import br.cervejaria.demo.models.mapper.ProdutoMapper;
import br.cervejaria.demo.models.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
Utilizando MVC é legal dar uma estudada sobre service-layer, ela ajuda a resolver vários problemas e melhorar a organização do seu código
a medida que as regras de négocio crescem, permitindo até reaproveitar o código dessas lógicas em diferentes pontos
Leia esse artigo https://blog1.westagilelabs.com/why-to-use-service-layer-in-spring-mvc-5f4fc52643c0
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {


    private final ProdutoRepository produtoRepository;

    /*
     Usando spring, deixe ele gerenciar a instanciação dos objetos para você
     Leia esse artigo https://www.geeksforgeeks.org/spring-difference-between-inversion-of-control-and-dependency-injection/
     Esse aqui vai te dar uma visão legal de como o spring funciona https://docs.spring.io/spring-framework/docs/current/reference/html/core.html
     Com o spring boot você simplifica essa injeção através do de annotations
     */
    ProdutoController(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    // Não utilize "Object" isso deixa o seu código muito genérico e de dificil manutenção
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Integer id) {
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        // Aqui você utiliza text/plain, deveria trabalhar puramente com application/json, retorne um objeto
        // Nesses casos poderia utilizar exceptions para resolver o problema, ou como ficou apenas não ter um body e o client deve entender o 404 como not found
        // No caso de exception leia https://www.baeldung.com/exception-handling-for-rest-with-spring  e https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
        // Use o .isEmpty() para simplificar a leitura do código
        if(produtoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoExists.get());
    }
    @PostMapping
    public ResponseEntity<Produto> saveProdutos(@RequestBody ProdutoDto produtoDto) {
        /*
        Utilizar o lombok é mais simples e mais limpo se você utilizar "builder" para fazer essa criação do objeto
        Mas há a possibilidade aqui de utilizar o "record" feature do java 14 que permite a criação de objetos imutaveis
        É legal também não duplicar código, criar uma função mapper para fazer a criação simplifica muito o trabalho e diminui a repetição
         */
        return ResponseEntity.status(HttpStatus.CREATED) // Aqui você pode utilizar o CREATED que é o http status 201 para indicar que o recurso foi criado
                .body(produtoRepository.save(
                        ProdutoMapper.fromDtoToEntity(produtoDto)
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduto(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto){
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        // Aqui você utiliza text/plain, deveria trabalhar puramente com application/json, retorne um objeto
        // Nesses casos poderia utilizar exceptions para resolver o problema, ou como ficou apenas não ter um body e o client deve entender o 404 como not found
        // No caso de exception leia https://www.baeldung.com/exception-handling-for-rest-with-spring  e https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
        // Use o .isEmpty() para simplificar a leitura do código
        if(produtoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        /*
        Utilizar o lombok é mais simples e mais limpo se você utilizar "builder" para fazer essa criação do objeto
        Mas há a possibilidade aqui de utilizar o "record" feature do java 14 que permite a criação de objetos imutaveis
        É legal também não duplicar código, criar uma função mapper para fazer a criação simplifica muito o trabalho e diminui a repetição
         */
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(
                ProdutoMapper.fromDtoToEntity(produtoExists.get().id(), produtoDto)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto (@PathVariable Integer id){
        Optional<Produto> produtoExists = Optional.ofNullable(produtoRepository.findById(id));

        // Aqui você utiliza text/plain, deveria trabalhar puramente com application/json, retorne um objeto
        // Nesses casos poderia utilizar exceptions para resolver o problema, ou como ficou apenas não ter um body e o client deve entender o 404 como not found
        // No caso de exception leia https://www.baeldung.com/exception-handling-for-rest-with-spring  e https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
        // Use o ".isEmpty()" para simplificar a leitura do código
        if(produtoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        produtoRepository.delete(produtoExists.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
