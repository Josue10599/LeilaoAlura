package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

    public void propoeLance(Lance lance) {
        lances.add(lance);
        verificaMaiorLance(lance);
        verificaMenorLance(lance);
    }

    private void verificaMenorLance(Lance lance) {
        if (menorValor > lance.getValor()) menorValor = lance.getValor();
    }

    private void verificaMaiorLance(Lance lance) {
        if (maiorValor < lance.getValor()) maiorValor = lance.getValor();
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

    public List<Lance> getTresMaioresLances() {
        Collections.sort(lances);
        if (lances.size() > 3) return lances.subList(0, 3);
        return lances.subList(0, lances.size());
    }
}
