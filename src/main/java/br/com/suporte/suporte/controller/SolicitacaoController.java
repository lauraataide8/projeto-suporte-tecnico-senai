package br.com.suporte.suporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // <--- Faltava importar isso

import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class SolicitacaoController {

    @Autowired
    private SolicitacaoRepository sr;

    // Método para ABRIR a página (GET)
    @GetMapping(value="/solicitacao")
    public String solicitacao() {
        return "solicitacao";
    }

    // Método para RECEBER os dados do formulário (POST)
    @PostMapping(value="/solicitacao") // <--- Faltava essa linha!
    public String salvarSolicitacao(Solicitacao solicitacao) {

        // DICA: Se o formulário não manda o status, defina como "Aberto" por padrão
        // Isso ajuda no filtro do Painel Técnico depois.
        if (solicitacao.getStatus() == null || solicitacao.getStatus().isEmpty()) {
            solicitacao.setStatus("Aberto");
        }

        sr.save(solicitacao);

        // Redireciona para a mesma página para limpar o formulário
        return "redirect:/solicitacao";
    }
}