package br.com.renan.apitransferencia.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.model.Transferencia;
import br.com.renan.apitransferencia.repository.TransferenciaRepository;

@Service
public class TransferenciaService {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TransferenciaRepository transferenciaRepo;
	
	private static final double vlrMaxTransferencia = 1000.00;
	private static final String ok = "SUCCESS";
	private static final String falha = "FAILED";

	public List<Transferencia> buscaTransferenciaPorConta(Long contaOrigem) {
		List<Transferencia> listaTransferencias = transferenciaRepo.findAllByContaOrigemOrderByDataTransferenciaDesc(contaOrigem);
		if(listaTransferencias.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfêrencias feitas por este cliente não foram encontradas na base de dados.");
		}
		return listaTransferencias;
	}

	public Transferencia efetuaTransferenciaContas(Transferencia transferencia) {

		Cliente clienteOrigem = clienteService.buscarClientePorNumeroConta(transferencia.getContaOrigem());
		Cliente clienteDestino = clienteService.buscarClientePorNumeroConta(transferencia.getContaDestino());

		transferencia.setDataTransferencia(new Date());
		transferencia.setStatus(falha);
		
		if (transferencia.getValorTransferencia() > vlrMaxTransferencia) {
			transferencia.setDetalhes("Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00");
			transferenciaRepo.save(transferencia);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00");
		} else if (clienteOrigem.getSaldoConta() < transferencia.getValorTransferencia()) {
			transferencia.setDetalhes("Transfêrencia não efetuada - Motivo: Saldo Insuficiente");
			transferenciaRepo.save(transferencia);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transfêrencia não efetuada - Motivo: Saldo Insuficiente");
		} else {
			transferencia.setStatus(ok);
			transferencia.setDetalhes("Transfêrencia efetuada com sucesso");
			clienteService.atualizarSaldoCliente(clienteDestino, transferencia.getValorTransferencia() , "C");
			clienteService.atualizarSaldoCliente(clienteOrigem, transferencia.getValorTransferencia(), "D");
			return transferenciaRepo.save(transferencia);
		}
		 
	}

}
