package br.com.suporte.suporte.controller;

import java.time.LocalDateTime; // Import para a data e hora
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.suporte.suporte.model.Solicitacao;
import br.com.suporte.suporte.repository.SolicitacaoRepository;

@Controller
public class SolicitacaoController {

    @Autowired
    private SolicitacaoRepository sr; // Injeção do Repositório

    // 1. EXIBIR O FORMULÁRIO (GET /solicitacao)
    @GetMapping("/solicitacao")
    public String exibirFormulario(Model model) {
        // Envia um novo objeto Solicitação vazio para o formulário (solicitacao.html)
        model.addAttribute("solicitacao", new Solicitacao());
        return "solicitacao"; // Retorna o template solicitacao.html
    }

    // 2. RECEBER E SALVAR A SOLICITAÇÃO (POST /solicitacao)
    @PostMapping("/solicitacao")
    public String salvarSolicitacao(Solicitacao solicitacao, RedirectAttributes attributes) {
        try {
            // Configura o status inicial (Pendente)
            solicitacao.setStatus("Pendente");

            // 🎯 Configura a data e hora da criação (ESSENCIAL para o PainelTecnico)
            solicitacao.setDataCriacao(LocalDateTime.now());

            // Salva a solicitação e obtém o objeto com o ID gerado pelo banco
            Solicitacao solicitacaoSalva = sr.save(solicitacao);

            // Adiciona o objeto salvo ao RedirectAttributes para que possa ser exibido na página de sucesso
            attributes.addFlashAttribute("solicitacaoSalva", solicitacaoSalva);
            attributes.addFlashAttribute("mensagem", "Sua solicitação foi enviada com sucesso!");

            // Redireciona para o método que exibe a página de sucesso
            return "redirect:/sucessoSolicitacao";
        } catch (Exception e) {
            // Em caso de erro (ex: falha no banco), retorna para o formulário com mensagem de erro
            attributes.addFlashAttribute("erro", "Erro ao enviar a solicitação. Tente novamente.");
            return "redirect:/solicitacao";
        }
    }

    // 3. PÁGINA DE SUCESSO (GET /sucessoSolicitacao)
    @GetMapping("/sucessoSolicitacao")
    public String exibirSucesso(Model model) {
        // Verifica se o objeto foi passado corretamente do POST (evita acesso direto à URL)
        if (!model.containsAttribute("solicitacaoSalva")) {
            return "redirect:/"; // Se não houver dados, volta para a home
        }
        return "sucessoSolicitacao"; // Retorna o template sucessoSolicitacao.html
    }
}