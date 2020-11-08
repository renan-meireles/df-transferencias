package br.com.renan.apitransferencia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.Data;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"numeroConta"})})
@Data
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String numeroConta;
	
	@Column(nullable = false)
	private double saldoConta;
	
	@Version
    private Long version;
	
}
