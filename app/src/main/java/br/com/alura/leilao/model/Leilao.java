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
        if (lanceValido(lance))
            adicionaLance(lance);
    }

    private boolean lanceValido(Lance lance) {
        final Usuario usuario = lance.getUsuario();
        final double valorDoLance = lance.getValor();
        if (valorDoLance > maiorValor)
            if (!lances.isEmpty())
                return !lanceFeitoEmSequencia(usuario) && !ultrapassouLimiteDeLances(usuario);
            else return true;
        return false;
    }

    private boolean lanceFeitoEmSequencia(Usuario usuario) {
        final Usuario usuarioDoUltimoLance = lances.get(0).getUsuario();
        return usuario.equals(usuarioDoUltimoLance);
    }

    private boolean ultrapassouLimiteDeLances(Usuario usuario) {
        int quantidadeDeLancesRealizados = 1;
        for (Lance lance : lances) {
            if (usuario.equals(lance.getUsuario())) quantidadeDeLancesRealizados++;
            if (quantidadeDeLancesRealizados > 5) return true;
        }
        return false;
    }

    private void adicionaLance(Lance lance) {
        lances.add(lance);
        Collections.sort(lances);
        verificaMaiorLance(lance);
        verificaMenorLance(lance);
    }

    private void verificaMenorLance(Lance lance) {
        final double valorDoLance = lance.getValor();
        if (menorValor > valorDoLance) menorValor = valorDoLance;
    }

    private void verificaMaiorLance(Lance lance) {
        final double valorDoLance = lance.getValor();
        if (maiorValor < valorDoLance) maiorValor = valorDoLance;
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
