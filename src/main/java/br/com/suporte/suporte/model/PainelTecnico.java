package br.com.suporte.suporte.model;

import jakarta.persistence.*;
// SE DER ERRO NO "jakarta", troque por "javax.persistence.*"
// (depende da versão do seu Spring Boot)

@Entity
public class PainelTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tecnicoResponsavel;
    private String observacoes;
    private String status; // Ex: Em andamento, Concluído
    private String acao;   // Ex: Troca de peça, Formatação

    // --- RELACIONAMENTO IMPORTANTE ---
    // Isso cria a chave estrangeira "solicitacao_id" no banco de dados.
    // É o que permite usar o findBySolicitacaoId no repositório.
    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    // --- CONSTRUTORES ---
    public PainelTecnico() {
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(String tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }
}