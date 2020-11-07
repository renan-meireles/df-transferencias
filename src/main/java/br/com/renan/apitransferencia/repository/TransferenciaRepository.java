package br.com.renan.apitransferencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.renan.apitransferencia.model.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>{
	List<Transferencia> findAllByContaOrigemOrderByDataTransferenciaDesc(Long contaOrigem);
}
