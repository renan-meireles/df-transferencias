package br.com.renan.apitransferencia;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.renan.apitransferencia.controller.ClienteController;
import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

	@Autowired
	ClienteController clienteController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ClienteRepository clienteRepo;

	private Cliente cliente1;
	private Cliente cliente2;

	@Before
	public void init() {

		cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("Teste Renan");
		cliente1.setNumeroConta("010123400");
		cliente1.setSaldoConta(1100.00);

		cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Teste Renan 2");
		cliente2.setNumeroConta("010123401");
		cliente2.setSaldoConta(500.00);
	}

	@Test
	public void deveCadastrarClienteComSucessoRetorna201() {
		clienteRepo.deleteAll();

		ResponseEntity<Cliente> response = restTemplate.postForEntity("/api/v1/customers", cliente1, Cliente.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
		Assertions.assertThat(response.getBody().getId()).isNotNull();
		clienteRepo.deleteAll();

	}
	
	@Test
	public void deveCadastrarClienteJaExistenteRetorna500() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/customers", cliente1, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradosSucessoRetorna200() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradosFalhaNaoExisteRetorna404() {
		clienteRepo.deleteAll();
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradoPorIdSucessoRetorna200() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers/"+cliente1.getId(), String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		
		ResponseEntity<String> response2 = restTemplate.getForEntity("/api/v1/customers/"+cliente2.getId(), String.class);
		Assertions.assertThat(response2.getStatusCodeValue()).isEqualTo(200);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradosPorIdFalhaNaoExisteRetorna404() {
		clienteRepo.deleteAll();
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers/99", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradoPorContaSucessoRetorna200() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers/account/"+cliente1.getNumeroConta(), String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		
		ResponseEntity<String> response2 = restTemplate.getForEntity("/api/v1/customers/account/"+cliente2.getNumeroConta(), String.class);
		Assertions.assertThat(response2.getStatusCodeValue()).isEqualTo(200);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarClientesCadastradosPorContaFalhaNaoExisteRetorna404() {
		clienteRepo.deleteAll();
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/customers/account/0101010110", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
		clienteRepo.deleteAll();
	}

}
