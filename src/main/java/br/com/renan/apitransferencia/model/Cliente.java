package br.com.renan.apitransferencia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


import lombok.Data;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"numeroConta"})})
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo nome é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "O campo numeroConta é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private String numeroConta;
	
	@NotNull(message = "O campo saldoConta é obrigatório e deve ser preenchido")
	@Column(nullable = false)
	private Double saldoConta;
	
	@Version
    private Long version;
	
}
