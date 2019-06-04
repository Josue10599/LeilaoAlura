package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorValor = Double.NEGATIVE_INFINITY;
    private double menorValor = Double.POSITIVE_INFINITY;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void setLance(Lance lance) {
        lances.add(lance);
        if (maiorValor < lance.getValor()) maiorValor = lance.getValor();
        if (menorValor > lance.getValor()) menorValor = lance.getValor();
    }

    public double getMaiorValor() {
        return this.maiorValor;
    }

    public double getMenorValor() {
        return this.menorValor;
    }

    public String getDescricao() {
        return descricao;
    }

}
