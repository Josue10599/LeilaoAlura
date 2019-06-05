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

    public void proporLance(Lance lance) {
        if (lance.getValor() > maiorValor) {
            if (!lances.isEmpty()) {
                if (!lance.getUsuario().equals(pegaUsuarioDoUltimoLance())) {
                    adicionaLance(lance);
                    Collections.sort(lances);
                }
            } else adicionaLance(lance);
        }
    }

    private Usuario pegaUsuarioDoUltimoLance() {
        return lances.get(0).getUsuario();
    }

    private void adicionaLance(Lance lance) {
        lances.add(lance);
        verificaMaiorLance(lance);
        verificaMenorLance(lance);
    }

    private void verificaMenorLance(Lance lance) {
        double menorLance = lance.getValor();
        if (menorValor > menorLance) menorValor = menorLance;
    }

    private void verificaMaiorLance(Lance lance) {
        double maiorLance = lance.getValor();
        if (maiorValor < maiorLance) maiorValor = maiorLance;
    }

    public double getMaiorValor() {
        if (maiorValor != Double.NEGATIVE_INFINITY) return this.maiorValor;
        return 0;
    }

    public double getMenorValor() {
        if (menorValor != Double.POSITIVE_INFINITY) return this.menorValor;
        return 0;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> getTresMaioresLances() {
        if (lances.size() > 3) return lances.subList(0, 3);
        return lances.subList(0, lances.size());
    }

    public int getQuantidadeLances() {
        return lances.size();
    }
}
