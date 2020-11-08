package br.com.renan.apitransferencia;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.repository.ClienteRepository;
import br.com.renan.apitransferencia.service.ClienteService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteServiceTest {

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private ClienteService clienteService;

	private Cliente cliente;
	private Cliente cliente2;

	@Before
	public void init() {

		cliente = new Cliente();
		cliente.setNome("Teste Renan 1");
		cliente.setNumeroConta("010123400");
		cliente.setSaldoConta(1110.00);

		clienteRepo.save(cliente);

		cliente2 = new Cliente();
		cliente2.setNome("Teste Renan 2");
		cliente2.setNumeroConta("010123401");
		cliente2.setSaldoConta(1110.00);

		clienteRepo.save(cliente2);
	}

	@Test
	public void deveBuscarListaClientes() {

		List<Cliente> lista = clienteService.buscarTodosClientes();

		Assertions.assertThat(lista).isNotNull();
		Assertions.assertThat(lista.get(0).getId()).isNotNull();
		Assertions.assertThat(lista.get(1).getId()).isNotNull();

	}

	@Test(expected = ResponseStatusException.class)
	public void deveBuscarListaClientesELancarException() {
		this.clean();

		List<Cliente> lista = clienteService.buscarTodosClientes();
		Assertions.assertThat(lista).isNotNull();

	}

	@Test
	public void deveCadastrarClienteComSucesso() {
		this.clean();
		Cliente clienteResponse = clienteService.cadastrarCliente(cliente);

		Assertions.assertThat(clienteResponse.getId()).isNotNull();
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());
		Assertions.assertThat(clienteResponse.getNumeroConta()).isEqualTo(cliente.getNumeroConta());
		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta());

	}

	@Test(expected = ResponseStatusException.class)
	public void deveCadastrarClienteELancarException() {

		Cliente clienteResponse = clienteService.cadastrarCliente(cliente);
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());

	}

	@Test
	public void deveBuscarClientePorIdComSucesso() {

		Cliente clienteResponse = clienteService.buscaClientePorId(cliente.getId());

		Assertions.assertThat(clienteResponse.getId()).isNotNull();
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());
		Assertions.assertThat(clienteResponse.getNumeroConta()).isEqualTo(cliente.getNumeroConta());
		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta());

	}

	@Test(expected = ResponseStatusException.class)
	public void deveBuscarClientePorIdELancarException() {
		this.clean();
		Cliente clienteResponse = clienteService.buscaClientePorId(cliente.getId());

		Assertions.assertThat(clienteResponse.getId()).isNotNull();
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());
		Assertions.assertThat(clienteResponse.getNumeroConta()).isEqualTo(cliente.getNumeroConta());
		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta());

	}

	@Test
	public void deveBuscarClientePorContaComSucesso() {

		Cliente clienteResponse = clienteService.buscarClientePorNumeroConta(cliente.getNumeroConta());

		Assertions.assertThat(clienteResponse.getId()).isNotNull();
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());
		Assertions.assertThat(clienteResponse.getNumeroConta()).isEqualTo(cliente.getNumeroConta());
		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta());

	}

	@Test(expected = ResponseStatusException.class)
	public void deveBuscarClientePorELancarException() {
		this.clean();
		Cliente clienteResponse = clienteService.buscarClientePorNumeroConta(cliente.getNumeroConta());

		Assertions.assertThat(clienteResponse.getId()).isNotNull();
		Assertions.assertThat(clienteResponse.getNome()).isEqualTo(cliente.getNome());
		Assertions.assertThat(clienteResponse.getNumeroConta()).isEqualTo(cliente.getNumeroConta());
		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta());

	}

	@Test
	public void deveAtualizarSaldoClienteDebito() {

		Cliente clienteResponse = clienteService.atualizarSaldoCliente(cliente, 111.99, "D");

		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta() - 111.99);

	}

	@Test
	public void deveAtualizarSaldoClienteCredito() {

		Cliente clienteResponse = clienteService.atualizarSaldoCliente(cliente, 111.99, "C");

		Assertions.assertThat(clienteResponse.getSaldoConta()).isEqualTo(cliente.getSaldoConta() + 111.99);
		System.out.println(clienteResponse.getVersion());
	}

	@After
	public void clean() {
		clienteRepo.deleteAll();
	}
}
