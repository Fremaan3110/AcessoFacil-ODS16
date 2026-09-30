package br.com.acessofacil.service;

import br.com.acessofacil.model.AnaliseIA;
import br.com.acessofacil.model.Solicitacao;
import br.com.acessofacil.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private GroqAIService groqAIService;

    public Solicitacao processarERegistrar(String relato) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setRelato(relato);

        AnaliseIA analise = groqAIService.analisarRelato(relato);
        solicitacao.setAnaliseIA(analise);

        return solicitacaoRepository.save(solicitacao);
    }
}
