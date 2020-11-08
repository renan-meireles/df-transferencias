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

import br.com.renan.apitransferencia.model.Transferencia;
import br.com.renan.apitransferencia.repository.TransferenciaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferenciaRepositoryTest {

	@Autowired
	private TransferenciaRepository transferenciaRepo;

	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Transferencia transf;
	private Transferencia transf2;
	private Transferencia transf3;

	@Before
	public void init() {

		transf = new Transferencia();
		transf.setContaOrigem("010123400");
		transf.setContaDestino("010123401");
		transf.setValorTransferencia(110.00);

		transf2 = new Transferencia();
		transf2.setContaOrigem("010123400");
		transf2.setContaDestino("010123401");
		transf2.setValorTransferencia(3.99);

		transf3 = new Transferencia();
		transf3.setContaOrigem("010123401");
		transf3.setContaDestino("010123400");
		transf3.setValorTransferencia(17.00);
	}

	@Test
	public void deveRegistrarTransferencia() {

		transf = this.transferenciaRepo.save(transf);

		Assertions.assertThat(transf.getId()).isNotNull();
		Assertions.assertThat(transf.getContaOrigem()).isEqualTo("010123400");
		Assertions.assertThat(transf.getContaDestino()).isEqualTo("010123401");
		Assertions.assertThat(transf.getValorTransferencia()).isEqualTo(110.00);
		Assertions.assertThat(transf.getDataTransferencia()).isNotNull();
		Assertions.assertThat(transf.getStatus()).isNull();
		Assertions.assertThat(transf.getDetalhes()).isNull();
	}

	@Test
	public void deveBuscarTransferenciasPorConta1() {

		this.transferenciaRepo.save(transf);
		this.transferenciaRepo.save(transf2);

		List<Transferencia> lista = this.transferenciaRepo
				.findAllByContaOrigemOrderByDataTransferenciaDesc(transf.getContaOrigem());

		Assertions.assertThat(lista.size()).isEqualTo(2);
		Assertions.assertThat(lista.get(0).getValorTransferencia()).isEqualTo(110.00);
		Assertions.assertThat(lista.get(1).getValorTransferencia()).isEqualTo(3.99);

	}

	@Test
	public void deveBuscarTransferenciasPorConta2() {

		this.transferenciaRepo.save(transf3);

		List<Transferencia> lista = this.transferenciaRepo
				.findAllByContaOrigemOrderByDataTransferenciaDesc(transf3.getContaOrigem());

		Assertions.assertThat(lista.size()).isEqualTo(1);
		Assertions.assertThat(lista.get(0).getValorTransferencia()).isEqualTo(17.00);

	}
	
	@Test
	public void deveRegistrarTransferenciaContaOrigemNullELancarExcecao() {
		thrown.expect(Exception.class);
		this.transferenciaRepo.save(new Transferencia());
	}
	
	@Test
	public void deveRegistrarTransferenciaContaDestinoNullELancarExcecao() {
		thrown.expect(Exception.class);
		Transferencia transf4 = new Transferencia();
		transf4.setContaOrigem("010123401");
		transf4.setValorTransferencia(10.00);
		this.transferenciaRepo.save(transf4);
	}

	@After
	public void clean() {
		transferenciaRepo.deleteAll();
	}
}
