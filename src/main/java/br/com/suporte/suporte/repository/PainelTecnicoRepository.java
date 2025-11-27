package br.com.suporte.suporte.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.suporte.suporte.model.PainelTecnico;
import java.util.Optional; // <--- Importante adicionar isso

@Repository
public interface PainelTecnicoRepository extends CrudRepository<PainelTecnico, Long>{

    // Este método busca o Painel Técnico baseado no ID da Solicitação vinculada
    Optional<PainelTecnico> findBySolicitacaoId(Long idSolicitacao);

}