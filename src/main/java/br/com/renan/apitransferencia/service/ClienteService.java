package br.com.renan.apitransferencia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;

	public Cliente cadastrarCliente(Cliente cliente) {

		Optional<Cliente> clienteExists = clienteRepo.findByNumeroConta(cliente.getNumeroConta());
		if (clienteExists.isPresent()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Já existe um Cliente cadastrado com o numero de conta: " + cliente.getNumeroConta());
		} else {
			return clienteRepo.save(cliente);
		}

	}

	public List<Cliente> buscarTodosClientes() {
		List<Cliente> listaClientes = clienteRepo.findAll();
		if (listaClientes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não existem clientes cadastrados na base de dados.");
		}
		return listaClientes;
	}

	public Cliente buscaClientePorId(Long id) {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		if (cliente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Cliente ID: " + id + " não encontrado na base de dados.");
		}
		return cliente.get();
	}

	public Cliente buscarClientePorNumeroConta(String numeroConta) {
		Optional<Cliente> cliente = clienteRepo.findByNumeroConta(numeroConta);
		if (cliente.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Cliente Conta Nº" + numeroConta + " não encontrado na base de dados.");
		}
		return cliente.get();
	}

	@Transactional
	public Cliente atualizarSaldoCliente(Cliente cliente, double valorTransferencia, String flagDebitoCredito) {
		Cliente verificaCliente = this.buscarClientePorNumeroConta(cliente.getNumeroConta());
		if (verificaCliente != null) {
			double valorAtualizado = "D".equals(flagDebitoCredito)
					? verificaCliente.getSaldoConta() - valorTransferencia
					: verificaCliente.getSaldoConta() + valorTransferencia;
			verificaCliente.setSaldoConta(valorAtualizado);
		}
		return clienteRepo.save(verificaCliente);
	}
}
