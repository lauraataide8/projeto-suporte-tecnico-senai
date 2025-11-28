package br.com.suporte.suporte.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.suporte.suporte.model.PainelTecnico;
import java.util.Optional;

@Repository
public interface PainelTecnicoRepository extends CrudRepository<PainelTecnico, Long>{

        Optional<PainelTecnico> findByIdSolicitacao(Long idSolicitacao);

}