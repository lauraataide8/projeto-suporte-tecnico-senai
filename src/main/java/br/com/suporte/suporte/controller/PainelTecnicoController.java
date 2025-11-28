package br.com.suporte.suporte.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class PainelTecnicoController {

    @Autowired
    private SolicitacaoRepository sr;

    // =========================================================
    // 1. LISTAR E FILTRAR SOLICITAÇÕES (GET /painelTecnico)
    // =========================================================
    @GetMapping("/painelTecnico")
    public String painelTecnico(
            @RequestParam(required = false) String filtroStatus,
            @RequestParam(required = false) String filtroTipo,
            Model model) {

        List<Solicitacao> solicitacoes;

        // Lógica de Filtragem (requer os métodos no SolicitacaoRepository)
        if (filtroStatus != null && !filtroStatus.isEmpty()) {
            solicitacoes = sr.findByStatus(filtroStatus);
        } else if (filtroTipo != null && !filtroTipo.isEmpty()) {
            solicitacoes = sr.findByTipoProblema(filtroTipo);
        } else {
            solicitacoes = sr.findAll(); // Requer que o Repository use JpaRepository
        }

        // Adiciona a lista e os filtros atuais ao Model
        model.addAttribute("solicitacoes", solicitacoes);
        model.addAttribute("filtroStatus", filtroStatus);
        model.addAttribute("filtroTipo", filtroTipo);

        return "PainelTecnico";
    }

    // =========================================================
    // 2. FLUXO DE EDIÇÃO (GET e POST)
    // =========================================================

    // 2.1 EXIBIR O FORMULÁRIO DE EDIÇÃO (GET /editarSolicitacao/{id})
    @GetMapping("/editarSolicitacao/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model, RedirectAttributes attributes) {

        Optional<Solicitacao> resultado = sr.findById(id);

        if (resultado.isPresent()) {
            // Adiciona a solicitação carregada ao Model
            model.addAttribute("solicitacao", resultado.get());
            return "EditarSolicitacao"; // Use o nome do arquivo HTML: EditarSolicitacao.html
        } else {
            attributes.addFlashAttribute("erro", "Solicitação não encontrada para edição.");
            return "redirect:/painelTecnico";
        }
    }

    // 2.2 SALVAR AS ALTERAÇÕES DO FORMULÁRIO (POST /editarSolicitacao)
    @PostMapping("/editarSolicitacao")
    public String salvarEdicao(Solicitacao solicitacao, RedirectAttributes attributes) {
        try {
            // O save() executa o UPDATE no registro existente (graças ao ID no objeto)
            sr.save(solicitacao);
            attributes.addFlashAttribute("mensagem", "Solicitação #" + solicitacao.getId() + " atualizada com sucesso.");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao atualizar solicitação. Tente novamente.");
        }
        return "redirect:/painelTecnico";
    }

    // =========================================================
    // 3. MUDANÇA DE STATUS RÁPIDA (Assumir e Concluir)
    // =========================================================

    // 3.1 ASSUMIR SOLICITAÇÃO (GET /assumirSolicitacao/{id})
    @GetMapping("/assumirSolicitacao/{id}")
    public String assumir(@PathVariable Long id, RedirectAttributes attributes) {
        Optional<Solicitacao> resultado = sr.findById(id);
        if (resultado.isPresent()) {
            Solicitacao solicitacao = resultado.get();
            solicitacao.setStatus("Em Andamento");
            sr.save(solicitacao);
            attributes.addFlashAttribute("mensagem", "Solicitação #" + id + " assumida e movida para Em Andamento!");
        } else {
            attributes.addFlashAttribute("erro", "Solicitação não encontrada para ser assumida.");
        }
        return "redirect:/painelTecnico";
    }

    // 3.2 CONCLUIR SOLICITAÇÃO (GET /concluirSolicitacao/{id})
    @GetMapping("/concluirSolicitacao/{id}")
    public String concluir(@PathVariable Long id, RedirectAttributes attributes) {
        Optional<Solicitacao> resultado = sr.findById(id);
        if (resultado.isPresent()) {
            Solicitacao solicitacao = resultado.get();
            solicitacao.setStatus("Concluída");
            sr.save(solicitacao);
            attributes.addFlashAttribute("mensagem", "Solicitação #" + id + " concluída com sucesso!");
        } else {
            attributes.addFlashAttribute("erro", "Solicitação não encontrada para ser concluída.");
        }
        return "redirect:/painelTecnico";
    }

    // =========================================================
    // 4. FLUXO DE EXCLUSÃO (APENAS O MÉTODO EXECUTOR)
    // =========================================================

    // 4.2 EXECUTAR A EXCLUSÃO (GET /excluirSolicitacao/{id})
    @GetMapping("/excluirSolicitacao/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            sr.deleteById(id);
            attributes.addFlashAttribute("mensagem", "Solicitação #" + id + " excluída com sucesso.");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao excluir a solicitação. Verifique se há Foreign Keys.");
        }
        return "redirect:/painelTecnico";
    }
}