package dio.design_pattern.service.impl;

import dio.design_pattern.model.Produto;
import dio.design_pattern.model.ProdutoRepository;
import dio.design_pattern.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Override
    public Iterable<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.get();
    }

    @Override
    public void inserir(Produto produto) {
        produtoRepository.save(produto);
    }

    @Override
    public void atualizar(Long id, Produto produto) {
        Optional<Produto> clienteBd = produtoRepository.findById(id);
        if (clienteBd.isPresent()) {
            if (isNull(produto.getId())){
                produto.setId(id);
            }
            produtoRepository.save(produto);
        }
    }

    @Override
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
