package br.com.suporte.suporte.model;

import jakarta.persistence.*; // Se der erro no jakarta, mude para javax.persistence.*

@Entity
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoProblema; // O nome exato que usaremos no Controller
    private String status;
    private String descricao;

    // Construtor vazio
    public Solicitacao() {}

    // --- GETTERS E SETTERS OBRIGATÓRIOS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}