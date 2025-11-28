package br.com.suporte.suporte.model;

import jakarta.persistence.*;
// Se estiver usando Spring Boot 2, troque por "javax.persistence.*"

@Entity
public class PainelTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- CAMPO CORRIGIDO para alinhamento com o Repositório ---
    // Mapeamento direto da chave estrangeira (ID da Solicitação)
    @Column(name = "solicitacao_id", unique = true, nullable = false)
    private Long idSolicitacao;

    private String tecnicoResponsavel;
    private String observacoes;
    private String status; // Ex: Em andamento, Concluído
    private String acao;   // Ex: Troca de peça, Formatação

    // --- CONSTRUTOR ---
    public PainelTecnico() {
    }

    // --- GETTERS E SETTERS COMPLETOS ---

    // ID do Painel Técnico
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ID da Solicitação (campo chave para busca no repositório)
    public Long getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(Long idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    // Técnico Responsável
    public String getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    // Observações
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Ação
    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }
}