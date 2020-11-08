package br.com.renan.apitransferencia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Entity
@Table
@Data
public class Transferencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String contaOrigem;
	
	@Column(nullable = false)
	private String contaDestino;
	
	@Column(nullable = false)
	private double valorTransferencia;
	
	@Column
	private Date dataTransferencia = new Date();
	
	@Column
	private String status;
	
	@Column
	private String detalhes;
	
	@Version
    private Long version;

}
