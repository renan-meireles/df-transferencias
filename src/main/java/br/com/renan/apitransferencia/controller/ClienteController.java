package br.com.renan.apitransferencia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.renan.apitransferencia.model.Cliente;
import br.com.renan.apitransferencia.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Cliente", description = "Endpoints com as operações ref. cadastro de clientes e buscas de clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Operation(summary = "Cadastra Clientes ", description = "Efetua o cadastro de clientes conforme dados informados na requisição", tags = {
			"cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(responseCode = "400", description = "Campos obrigatórios não informados corretamente", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "500", description = "Já existe um Cliente cadastrado com o numero de conta: {numeroConta}", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping
	public ResponseEntity<Cliente> adicionarCliente(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cliente para cadastrar. Não pode ser nulo ou vazio.", content = @Content(schema = @Schema(implementation = Cliente.class)), required = true) @RequestBody @Valid Cliente cliente) {

		Cliente clienteRegistrado = clienteService.cadastrarCliente(cliente);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/customer").path("/{id}")
				.buildAndExpand(clienteRegistrado.getId()).toUri();

		return ResponseEntity.created(uri).body(clienteRegistrado);
	}

	@Operation(summary = "Busca Clientes Cadastrados", description = "Busca todos os clientes cadastrados.", tags = {
			"cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))) })
	@GetMapping
	public ResponseEntity<List<Cliente>> listaClientes() {
		return ResponseEntity.ok().body(clienteService.buscarTodosClientes());
	}

	@Operation(summary = "Busca Cliente por ID", description = "Retorna um único cliente cadastrado, conforme ID da requisição", tags = {
			"cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(responseCode = "404", description = "Cliente ID: {id} não encontrado na base de dados.", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscaPorId(
			@Parameter(description = "ID do Cliente. Deve ser informado.", required = true) @PathVariable Long id) {
		return ResponseEntity.ok().body(clienteService.buscaClientePorId(id));
	}

	@Operation(summary = "Busca Cliente por Numero da Conta ", description = "Retorna um único cliente cadastrado, conforme Numero da Conta informado na requisição", tags = {
			"cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(schema = @Schema(implementation = Cliente.class))),
			@ApiResponse(responseCode = "404", description = "Cliente Conta Nº: {numeroConta} não encontrado na base de dados.", content = @Content(schema = @Schema(hidden = true))) })
	@GetMapping("/account/{numeroConta}")
	public ResponseEntity<Cliente> buscaPorConta(
			@Parameter(description = "Numero da Conta do Cliente. Deve ser informado.", required = true) @PathVariable String numeroConta) {

		return ResponseEntity.ok().body(clienteService.buscarClientePorNumeroConta(numeroConta));
	}
}
