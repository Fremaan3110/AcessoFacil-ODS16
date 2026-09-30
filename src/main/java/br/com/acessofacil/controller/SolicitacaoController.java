package br.com.acessofacil.controller;

import br.com.acessofacil.model.Solicitacao;
import br.com.acessofacil.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/solicitacoes")
@CrossOrigin(origins = "*")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping
    public ResponseEntity<Solicitacao> criarSolicitacao(@RequestBody Map<String, String> payload) {
        String relato = payload.get("relato");
        if (relato == null || relato.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Solicitacao novaSolicitacao = solicitacaoService.processarERegistrar(relato);
        return ResponseEntity.ok(novaSolicitacao);
    }
}
