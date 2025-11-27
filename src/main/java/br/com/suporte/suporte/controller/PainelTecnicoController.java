package br.com.suporte.suporte.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.suporte.suporte.model.PainelTecnico;
import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.PainelTecnicoRepository;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class PainelTecnicoController {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private PainelTecnicoRepository painelTecnicoRepository;

    @GetMapping(value = "/painelTecnico")
    public String mostrarPainelTecnico(
            @RequestParam(value = "filtroTipo", required = false) String filtroTipo,
            @RequestParam(value = "filtroStatus", required = false) String filtroStatus,
            @RequestParam(value = "filtroAcao", required = false) String filtroAcao,
            Model model) {

        Iterable<Solicitacao> solicitacoes;

        if ((filtroTipo != null && !filtroTipo.isEmpty()) ||
                (filtroStatus != null && !filtroStatus.isEmpty()) ||
                (filtroAcao != null && !filtroAcao.isEmpty())) {

            solicitacoes = filtrarSolicitacoes(filtroTipo, filtroStatus, filtroAcao);
        } else {
            solicitacoes = solicitacaoRepository.findAll();
        }

        model.addAttribute("solicitacoes", solicitacoes);
        model.addAttribute("filtroTipo", filtroTipo);
        model.addAttribute("filtroStatus", filtroStatus);
        model.addAttribute("filtroAcao", filtroAcao);

        return "painelTecnico";
    }

    private Iterable<Solicitacao> filtrarSolicitacoes(String tipo, String status, String acao) {
        Iterable<Solicitacao> todasSolicitacoes = solicitacaoRepository.findAll();
        List<Solicitacao> solicitacoesFiltradas = new ArrayList<>();

        for (Solicitacao sol : todasSolicitacoes) {
            boolean atende = true;

            // 1. Verifica Tipo (usando getTipoProblema do Modelo)
            if (tipo != null && !tipo.isEmpty()) {
                if (sol.getTipoProblema() == null || !tipo.equalsIgnoreCase(sol.getTipoProblema())) {
                    atende = false;
                }
            }

            // 2. Verifica Status
            if (atende && status != null && !status.isEmpty()) {
                if (sol.getStatus() == null || !status.equalsIgnoreCase(sol.getStatus())) {
                    atende = false;
                }
            }

            // 3. Verifica Ação (consulta PainelTecnico)
            if (atende && acao != null && !acao.isEmpty()) {
                Optional<PainelTecnico> ptOpt = painelTecnicoRepository.findBySolicitacaoId(sol.getId());

                if (ptOpt.isPresent()) {
                    PainelTecnico pt = ptOpt.get();
                    if (pt.getAcao() == null || !acao.equalsIgnoreCase(pt.getAcao())) {
                        atende = false;
                    }
                } else {
                    atende = false; // Se não tem painel, não tem ação
                }
            }

            if (atende) {
                solicitacoesFiltradas.add(sol);
            }
        }
        return solicitacoesFiltradas;
    }

    // Método para processar o formulário de "Assumir/Resolver"
    @PostMapping("/assumirSolicitacao")
    public String processarAssumirSolicitacao(
            @RequestParam Long idSolicitacao,
            @RequestParam String tecnicoResponsavel,
            @RequestParam String observacoes) {

        Optional<Solicitacao> solOpt = solicitacaoRepository.findById(idSolicitacao);

        if (solOpt.isPresent()) {
            Solicitacao sol = solOpt.get();

            // Busca painel existente ou cria novo
            PainelTecnico painel = painelTecnicoRepository.findBySolicitacaoId(idSolicitacao)
                    .orElse(new PainelTecnico());

            painel.setTecnicoResponsavel(tecnicoResponsavel);
            painel.setObservacoes(observacoes);
            painel.setStatus("Em Andamento");
            painel.setAcao("Assumida");
            painel.setSolicitacao(sol); // Vincula o objeto

            painelTecnicoRepository.save(painel);

            sol.setStatus("Em Andamento");
            solicitacaoRepository.save(sol);
        }

        return "redirect:/painelTecnico";
    }
}