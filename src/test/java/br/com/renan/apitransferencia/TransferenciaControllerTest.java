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

import br.com.renan.apitransferencia.controller.TransferenciaController;
import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.model.Transferencia;
import br.com.renan.apitransferencia.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferenciaControllerTest {
	
	@Autowired
	TransferenciaController transfController;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	private Transferencia transf;
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
		
		transf = new Transferencia();
		transf.setContaOrigem("010123401");
		transf.setContaDestino("010123400");
		transf.setValorTransferencia(100.00);
		
	}
	
	@Test
	public void deveEfetuarTransferenciaSucessoRetorna200() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		
		ResponseEntity<Transferencia> response = restTemplate.postForEntity("/api/v1/transferences", transf, Transferencia.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200); 
		Assertions.assertThat(response.getBody().getId()).isNotNull();
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveEfetuarTransferenciaSemSucessoRetorna404(){
		transf.setContaOrigem("010101010");
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/transferences", transf, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404); 
	}
	
	@Test
	public void deveEfetuarTransferenciaSemSucessoMaiorQueMilRetorna500() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		transf.setValorTransferencia(1100.00);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/transferences", transf, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500); 
		clienteRepo.deleteAll();
	}

	@Test
	public void deveEfetuarTransferenciaSemSucessoSemSaldoRetorna500() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		transf.setValorTransferencia(600.00);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/transferences", transf, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500); 
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarTransferenciasSucessoRetorna200() {
		clienteRepo.deleteAll();
		clienteRepo.save(cliente1);
		clienteRepo.save(cliente2);
		String contaOrigem = transf.getContaOrigem();
		restTemplate.postForEntity("/api/v1/transferences", transf, String.class);
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/transferences/account/"+contaOrigem, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
		clienteRepo.deleteAll();
	}
	
	@Test
	public void deveBuscarTransferenciasSemRegistrosRetorna404() {
		String contaOrigem = "0101010110";
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/transferences/account/"+contaOrigem, String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}
}
