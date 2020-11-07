package br.com.renan.apitransferencia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renan.apitransferencia.model.Transferencia;
import br.com.renan.apitransferencia.service.TransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/transference")
@Tag(name = "Transfêrencia", description = "Endpoints com as operações ref. transfêrencias e historico de transfêrencias")
public class TransferenciaController {

	@Autowired
	TransferenciaService transferenciaService;

	@Operation(summary = "Transfêrencia entre Contas", description = "Efetua a transfêrencia entre contas de dois clientes cadastrados conforme dados informados na requisição", tags = {
			"transferencia" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Transfêrencia efetuada com sucesso", content = @Content(schema = @Schema(implementation = Transferencia.class))),
			@ApiResponse(responseCode = "500", description = "'Transfêrencia não efetuada - Motivo: Valor acima do maximo permitido R$ 1.000,00' ou 'Transfêrencia não efetuada - Motivo: Saldo Insuficiente'") })
	@PostMapping
	public ResponseEntity<Transferencia> efetuarTransferencia(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informações para efetuar a transfêrencia. Não pode ser nulo ou vazio.", content = @Content(schema = @Schema(implementation = Transferencia.class)), required = true) @RequestBody Transferencia transferencia) {
		return ResponseEntity.ok().body(transferenciaService.efetuaTransferenciaContas(transferencia));
	}

	@Operation(summary = "Busca Transfêrencia pelo Numero da Conta de Origem", description = "Retorna todas as transfêrencias efetuadas pela conta de origem informada", tags = {
			"transferencia" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Transferencia.class)))),
			@ApiResponse(responseCode = "404", description = "Transfêrencias feitas por este cliente não foram encontradas na base de dados.") })
	@GetMapping("/account/{contaOrigem}")
	public ResponseEntity<List<Transferencia>> buscaPorConta(
			@Parameter(description = "Numero da Conta do Cliente de Origem da transfêrencia. Deve ser informado.", required = true) @PathVariable Long contaOrigem) {
		return ResponseEntity.ok().body(transferenciaService.buscaTransferenciaPorConta(contaOrigem));
	}

}
