package br.com.suporte.suporte.repository;

import java.util.List; // NOVO IMPORT
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.suporte.suporte.model.Solicitacao;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    // Método para a funcionalidade de FILTRAR POR STATUS no Painel Técnico
    public List<Solicitacao> findByStatus(String status);

    // Método para a funcionalidade de FILTRAR POR TIPO DE PROBLEMA no Painel Técnico
    public List<Solicitacao> findByTipoProblema(String tipoProblema);
}