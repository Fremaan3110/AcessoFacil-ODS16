package br.com.acessofacil.service;

import br.com.acessofacil.model.AnaliseIA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GroqAIService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public AnaliseIA analisarRelato(String relato) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String prompt = "Analise o seguinte relato de um cidadão e classifique. Responda APENAS em texto simples com este formato exato:\n" +
                "Categoria: [Nome da Categoria]\n" +
                "Urgencia: [Alta/Media/Baixa]\n" +
                "Orientacao: [Orientação em 1 frase]\n\n" +
                "Relato: " + relato;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.3-70b-versatile");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, entity, Map.class);
            List choices = (List) response.getBody().get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");
            String content = (String) message.get("content");

            return parseRespostaIA(content);
        } catch (Exception e) {
            AnaliseIA erro = new AnaliseIA();
            erro.setCategoria("Geral");
            erro.setNivelUrgencia("Media");
            erro.setOrientacao("Solicitação registrada. Aguardando análise manual.");
            return erro;
        }
    }

    private AnaliseIA parseRespostaIA(String texto) {
        AnaliseIA analise = new AnaliseIA();
        String[] linhas = texto.split("\n");
        for (String linha : linhas) {
            if (linha.startsWith("Categoria:")) analise.setCategoria(linha.replace("Categoria:", "").trim());
            if (linha.startsWith("Urgencia:")) analise.setNivelUrgencia(linha.replace("Urgencia:", "").trim());
            if (linha.startsWith("Orientacao:")) analise.setOrientacao(linha.replace("Orientacao:", "").trim());
        }
        if (analise.getCategoria() == null) analise.setCategoria("Geral");
        if (analise.getNivelUrgencia() == null) analise.setNivelUrgencia("Media");
        if (analise.getOrientacao() == null) analise.setOrientacao(texto);
        return analise;
    }
}
