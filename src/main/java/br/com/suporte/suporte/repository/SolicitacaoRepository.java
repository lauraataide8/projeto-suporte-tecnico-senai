package br.com.suporte.suporte.repository;

import br.com.suporte.suporte.model.Solicitacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository // <--- ISSO É ESSENCIAL PARA O ERRO SUMIR
public interface SolicitacaoRepository extends CrudRepository<Solicitacao, Long> {
    // Não precisa de código extra aqui por enquanto
}