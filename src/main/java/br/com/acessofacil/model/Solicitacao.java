package br.com.acessofacil.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_solicitacao")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String relato;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToOne(mappedBy = "solicitacao", cascade = CascadeType.ALL)
    private AnaliseIA analiseIA;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRelato() { return relato; }
    public void setRelato(String relato) { this.relato = relato; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public AnaliseIA getAnaliseIA() { return analiseIA; }
    public void setAnaliseIA(AnaliseIA analiseIA) { 
        this.analiseIA = analiseIA; 
        if (analiseIA != null) analiseIA.setSolicitacao(this);
    }
}
