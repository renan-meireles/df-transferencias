package br.com.renan.apitransferencia;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Cliente cliente;
	private Cliente cliente2;
	
	@Before
	public void init() {
		
		cliente = new Cliente();
		cliente.setNome("Teste Renan 1");
		cliente.setNumeroConta("010123400");
		cliente.setSaldoConta(1100.00);
		
		cliente2 = new Cliente();
		cliente2.setNome("Teste Renan 2");
		cliente2.setNumeroConta("010123401");
		cliente2.setSaldoConta(100.95);
	}
	
	@Test
	public void deveCriarCliente() {
		
		cliente = this.clienteRepo.save(cliente);
		
		Assertions.assertThat(cliente.getId()).isNotNull();
		Assertions.assertThat(cliente.getNome()).isEqualTo("Teste Renan 1");
		Assertions.assertThat(cliente.getNumeroConta()).isEqualTo("010123400");
		Assertions.assertThat(cliente.getSaldoConta()).isEqualTo(1100.00);
	}
	
	@Test
	public void deveConsultarClientePorId() {
		
		this.clienteRepo.save(cliente);
		
		Assertions.assertThat(clienteRepo.findById(cliente.getId())).isPresent();

	}
	
	@Test
	public void deveConsultarClientePorNumeroConta() {
		
		this.clienteRepo.save(cliente2);
		Assertions.assertThat(clienteRepo.findByNumeroConta(cliente2.getNumeroConta())).isPresent();
		
	}
	
	@Test
	public void deveConsultarTodosClientesExistentes() {
		this.clienteRepo.save(cliente);
		this.clienteRepo.save(cliente2);
		
		List<Cliente> lista = this.clienteRepo.findAll();
		Assertions.assertThat(lista.size()).isEqualTo(2);
		Assertions.assertThat(lista.get(0).getNome()).isEqualTo("Teste Renan 1");
		Assertions.assertThat(lista.get(1).getNome()).isEqualTo("Teste Renan 2");
	}
	
	@Test
	public void deveAtualizarValorSaldo() {
		
		this.clienteRepo.save(cliente);
		cliente.setSaldoConta(990.00);
		this.clienteRepo.save(cliente);
		cliente = this.clienteRepo.findById(cliente.getId()).get();
		Assertions.assertThat(cliente.getNome()).isEqualTo("Teste Renan 1");
		Assertions.assertThat(cliente.getNumeroConta()).isEqualTo("010123400");
		Assertions.assertThat(cliente.getSaldoConta()).isEqualTo(990.00);

	}
	
	@Test
	public void deveCriarClienteNomeNullELancarExcecao() {
		thrown.expect(Exception.class);
		this.clienteRepo.save(new Cliente());
	}
	
	@Test
	public void deveCriarClienteContaNullELancarExcecao() {
		thrown.expect(Exception.class);
		Cliente cliente3 = new Cliente();
		cliente3.setNome("teste");
		cliente3.setSaldoConta(1.00);
		this.clienteRepo.save(cliente3);
	}
	
	@After
    public void clean() {
		clienteRepo.deleteAll();
    }
	
}
