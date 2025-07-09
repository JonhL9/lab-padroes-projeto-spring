package dio.design_pattern.service.impl;

import java.util.Optional;

import dio.design_pattern.model.Cliente;
import dio.design_pattern.model.ClienteRepository;
import dio.design_pattern.model.Endereco;
import dio.design_pattern.model.EnderecoRepository;
import dio.design_pattern.service.ClienteService;
import dio.design_pattern.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    public Iterable<Endereco> listarEnderecos() {
        // Buscar todos os Clientes.
        return enderecoRepository.findAll();
    }
    @Override
    public Iterable<Cliente> buscarTodos() {
        // Buscar todos os Clientes.
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        // Buscar Cliente por ID.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            if (isNull(cliente.getId())){
                cliente.setId(id);
            }
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        // Deletar Cliente por ID.
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }

}
