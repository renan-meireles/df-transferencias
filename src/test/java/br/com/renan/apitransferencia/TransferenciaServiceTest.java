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
import br.com.renan.apitransferencia.model.Transferencia;
import br.com.renan.apitransferencia.repository.ClienteRepository;
import br.com.renan.apitransferencia.repository.TransferenciaRepository;
import br.com.renan.apitransferencia.service.TransferenciaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferenciaServiceTest {

	@Autowired
	private TransferenciaRepository transfRepo;

	@Autowired
	private TransferenciaService transfService;

	@Autowired
	private ClienteRepository clienteRepo;

	private Cliente cliente;
	private Cliente cliente2;
	private Transferencia transf;
	private Transferencia transfSemSaldo;
	private Transferencia transfMaxValor;

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
		cliente2.setSaldoConta(500.00);

		clienteRepo.save(cliente2);

		transf = new Transferencia();
		transf.setContaOrigem("010123400");
		transf.setContaDestino("010123401");
		transf.setValorTransferencia(100.00);

		transfMaxValor = new Transferencia();
		transfMaxValor.setContaOrigem("010123400");
		transfMaxValor.setContaDestino("010123401");
		transfMaxValor.setValorTransferencia(1100.00);

		transfSemSaldo = new Transferencia();
		transfSemSaldo.setContaOrigem("010123401");
		transfSemSaldo.setContaDestino("010123400");
		transfSemSaldo.setValorTransferencia(600.00);
	}

	@Test
	public void deveEfetuarTransferenciaComSucesso() {

		Transferencia transfResponse = transfService.efetuaTransferenciaContas(transf);

		Assertions.assertThat(transfResponse.getId()).isNotNull();
		Assertions.assertThat(transfResponse.getStatus()).isEqualTo("SUCCESS");
		Assertions.assertThat(transfResponse.getDetalhes()).isEqualTo("Transfêrencia efetuada com sucesso");
	}

	@Test(expected = ResponseStatusException.class)
	public void deveEfetuarTransferenciaComErroSemSaldo() {

		Transferencia transfResponse = transfService.efetuaTransferenciaContas(transfSemSaldo);

		Assertions.assertThat(transfResponse.getId()).isNotNull();
		Assertions.assertThat(transfResponse.getStatus()).isEqualTo("FAILED");
		Assertions.assertThat(transfResponse.getDetalhes())
				.isEqualTo("Transfêrencia não efetuada - Motivo: Saldo Insuficiente");
	}

	@Test(expected = ResponseStatusException.class)
	public void deveEfetuarTransferenciaComErroMaiorValorMax() {

		Transferencia transfResponse = transfService.efetuaTransferenciaContas(transfMaxValor);

		Assertions.assertThat(transfResponse.getId()).isNotNull();
		Assertions.assertThat(transfResponse.getStatus()).isEqualTo("FAILED");
		Assertions.assertThat(transfResponse.getDetalhes())
				.isEqualTo("Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00");
	}

	@Test
	public void deveBuscarTransferenciasPorConta() {
		transfSemSaldo.setStatus("FAILED");
		transfSemSaldo.setDetalhes("Transfêrencia não efetuada - Motivo: Saldo Insuficiente");
		transfRepo.save(transfSemSaldo);

		transfMaxValor.setStatus("FAILED");
		transfMaxValor.setDetalhes("Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00");
		transfRepo.save(transfMaxValor);

		transf.setStatus("SUCCESS");
		transf.setDetalhes("Transfêrencia efetuada com sucesso");
		transfRepo.save(transf);

		List<Transferencia> lista1 = transfService.buscaTransferenciaPorConta(cliente.getNumeroConta());

		List<Transferencia> lista2 = transfService.buscaTransferenciaPorConta(cliente2.getNumeroConta());

		Assertions.assertThat(lista1.size()).isEqualTo(2);
		Assertions.assertThat(lista2.size()).isEqualTo(1);

		Assertions.assertThat(lista1.get(0).getId()).isNotNull();
		Assertions.assertThat(lista1.get(0).getDataTransferencia()).isNotNull();
		Assertions.assertThat(lista1.get(0).getStatus()).isEqualTo(transfMaxValor.getStatus());
		Assertions.assertThat(lista1.get(0).getDetalhes()).isEqualTo(transfMaxValor.getDetalhes());
		Assertions.assertThat(lista1.get(1).getId()).isNotNull();
		Assertions.assertThat(lista1.get(1).getDataTransferencia()).isNotNull();
		Assertions.assertThat(lista1.get(1).getStatus()).isEqualTo(transf.getStatus());
		Assertions.assertThat(lista1.get(1).getDetalhes()).isEqualTo(transf.getDetalhes());
		Assertions.assertThat(lista2.get(0).getId()).isNotNull();
		Assertions.assertThat(lista2.get(0).getDataTransferencia()).isNotNull();
		Assertions.assertThat(lista2.get(0).getStatus()).isEqualTo(transfSemSaldo.getStatus());
		Assertions.assertThat(lista2.get(0).getDetalhes()).isEqualTo(transfSemSaldo.getDetalhes());

	}

	@Test(expected = ResponseStatusException.class)
	public void deveBuscarTransferenciasPorContaSemRetorno() {

		List<Transferencia> lista1 = transfService.buscaTransferenciaPorConta(cliente.getNumeroConta());

		Assertions.assertThat(lista1.size()).isEqualTo(2);

	}

	@After
	public void clean() {
		clienteRepo.deleteAll();
		transfRepo.deleteAll();
	}

}
