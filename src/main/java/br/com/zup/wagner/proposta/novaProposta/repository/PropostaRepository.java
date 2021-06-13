package br.com.zup.wagner.proposta.novaProposta.repository;

import br.com.zup.wagner.proposta.novaProposta.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);
}
