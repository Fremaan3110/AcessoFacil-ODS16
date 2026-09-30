package br.com.acessofacil.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_analise_ia")
public class AnaliseIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;
    private String nivelUrgencia;

    @Column(columnDefinition = "TEXT")
    private String orientacao;

    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    @JsonIgnore
    private Solicitacao solicitacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNivelUrgencia() { return nivelUrgencia; }
    public void setNivelUrgencia(String nivelUrgencia) { this.nivelUrgencia = nivelUrgencia; }

    public String getOrientacao() { return orientacao; }
    public void setOrientacao(String orientacao) { this.orientacao = orientacao; }

    public Solicitacao getSolicitacao() { return solicitacao; }
    public void setSolicitacao(Solicitacao solicitacao) { this.solicitacao = solicitacao; }
}
