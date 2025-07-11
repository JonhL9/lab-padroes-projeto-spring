package dio.design_pattern.service;

import dio.design_pattern.model.Produto;

public interface ProdutoService {
    Iterable<Produto> buscarTodos();

    Produto buscarPorId(Long id);

    void inserir(Produto produto);

    void atualizar(Long id, Produto produto);

    void deletar(Long id);
}
