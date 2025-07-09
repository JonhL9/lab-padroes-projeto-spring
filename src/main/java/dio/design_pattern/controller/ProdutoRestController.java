package dio.design_pattern.controller;

import dio.design_pattern.model.Produto;
import dio.design_pattern.model.ProdutoRepository;
import dio.design_pattern.service.ProdutoService;
import dio.design_pattern.service.impl.ProdutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtos")
public class ProdutoRestController {
    @Autowired
    private ProdutoService produtoService;
    @GetMapping
    public ResponseEntity<Iterable<Produto>> listarProdutos(){
        return ResponseEntity.ok(((ProdutoServiceImpl) produtoService).buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<Produto> inserir(@RequestBody Produto produto){
        produtoService.inserir(produto);
        return ResponseEntity.ok(produto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto){
        produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        produtoService.deletar(id);
        return  ResponseEntity.ok().build();
    }
}
