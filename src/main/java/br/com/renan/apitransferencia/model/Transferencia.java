package br.com.renan.apitransferencia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Table
@Data
public class Transferencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo contaOrigem é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private String contaOrigem;
	
	@NotBlank(message = "O campo contaDestino é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private String contaDestino;
	
	@NotNull(message = "O campo valorTransferencia é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private Double valorTransferencia;
	
	@Column
	private Date dataTransferencia;
	
	@Column
	private String status;
	
	@Column
	private String detalhes;
	
	@Version
    private Long version;

}
