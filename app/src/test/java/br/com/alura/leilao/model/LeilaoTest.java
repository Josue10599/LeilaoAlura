package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exception.LancesSeguidosException;
import br.com.alura.leilao.exception.LimiteDeLancesException;
import br.com.alura.leilao.exception.ValorMenorQueUltimoDoLanceException;
import br.com.alura.leilao.model.builder.LeilaoBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private final Leilao COMPUTADOR = new Leilao("Computador");
    private final Usuario USUARIO_BOT = new Usuario("Bot");

    /**
     * Para padronizar e melhor descrever o que um método faz a comunidade Java criou alguns padrões
     * para boas práticas na implementação de teste, uma delas é a nomeação descritiva dos métodos,
     * onde há alguns padrões como:
     * <p>
     * - [nome do método][estado de teste][resultado esperado]
     * - [deve][resultado esperado][estado de teste]
     */

    @Test
    public void deve_DevolverDescricao_QuandoRecebeUmaDescricao() {
        // Criação de ambiente de Teste
        Leilao console = new Leilao("Console");
        // Executar ação esperada
        String descricaoRecebida = console.getDescricao();
        // Testar o resultado esperado
        assertEquals("Console", descricaoRecebida);
    }

    @Test
    public void deve_DevolverMaiorValor_QuandoRecebeUmLance() {
        // Criação de ambiente de Teste
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 200.0));
        // Executar ação esperada
        double maiorValorDevolvido = COMPUTADOR.getMaiorValor();
        // Testar o resultado esperado
        assertEquals(200.0, maiorValorDevolvido, DELTA);
        /*DELTA = o Delta é o valor utilizado para a comparação de números com pontos flutuantes
        com ele colocamos uma margem de diferença que pode haver entre os dois valores passados,
        para que o COMPUTADOR entenda como números iguais, ou seja, se o delta que passamos for
        maior que a diferença entre o dois números que comparamos significa que eles são equivalentes.*/
    }

    @Test
    public void deve_DevolverMaiorValor_QuandoRecebeLancesEmOrdemCrescente() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 10.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Pc"), 100.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Mac"), 1000.0));
        double maiorValorDevolvido = COMPUTADOR.getMaiorValor();
        assertThat(maiorValorDevolvido, is(closeTo(1000.0, DELTA)));
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeUmLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 100.0));
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertThat(menorValorDevolvido, is(closeTo(100.0, DELTA)));
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeLancesEmOrdemCrescente() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 10.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Pc"), 100.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Mac"), 1000.0));
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertThat(menorValorDevolvido, is(closeTo(10.0, DELTA)));
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeTresLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 750.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1000.0));
        List<Lance> maioresLancesDevolvidos = COMPUTADOR.getTresMaioresLances();
        assertThat(maioresLancesDevolvidos, both(Matchers.<Lance>hasSize(3)).and(contains(
                new Lance(USUARIO_BOT, 1000.0),
                new Lance(new Usuario("PC"), 750.0),
                new Lance(USUARIO_BOT, 500.0)
        )));
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeDoisLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 750.0));
        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR.getTresMaioresLances();
        assertThat(tresMaioresLancesDevolvidos, both(Matchers.<Lance>hasSize(2)).and(contains(
                new Lance(new Usuario("PC"), 750.0),
                new Lance(USUARIO_BOT, 500.0)
        )));
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeVariosLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 100.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 200.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 700.0));
        List<Lance> tresMaioresLancesDevolvidosComQuatroLances = COMPUTADOR.getTresMaioresLances();
        assertThat(tresMaioresLancesDevolvidosComQuatroLances, both(
                Matchers.<Lance>hasSize(equalTo(3))).and(contains(
                new Lance(new Usuario("PC"), 700.0),
                new Lance(USUARIO_BOT, 500.0),
                new Lance(new Usuario("PC"), 200.0)
        )));
    }

    @Test
    public void deve_DevolverZeroParaMaiorLance_QuandoNaoTiverLance() {
        double maiorValorDevolvido = COMPUTADOR.getMaiorValor();
        assertThat(maiorValorDevolvido, is(closeTo(0, DELTA)));
    }

    @Test
    public void deve_DevolverZeroParaMenorLance_QuandoNaoTiverLance() {
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertThat(menorValorDevolvido, is(closeTo(0, DELTA)));
    }

    @Test(expected = ValorMenorQueUltimoDoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueMaiorLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 500.0));
    }

    @Test(expected = LancesSeguidosException.class)
    public void naoDeve_AdicionarLance_QuandoForMesmoUsuarioDoUltimoLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 5000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 12000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Bot"), 15000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Bot"), 15100.0));
    }

    @Test(expected = LimiteDeLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioFezCincoLances() {
        final Usuario USUARIO_PC = new Usuario("PC");
        new LeilaoBuilder("Computador")
                .adicionaLance(USUARIO_BOT, 100.0)
                .adicionaLance(USUARIO_PC, 200.0)
                .adicionaLance(USUARIO_BOT, 300.0)
                .adicionaLance(USUARIO_PC, 400.0)
                .adicionaLance(USUARIO_BOT, 500.0)
                .adicionaLance(USUARIO_PC, 600.0)
                .adicionaLance(USUARIO_BOT, 700.0)
                .adicionaLance(USUARIO_PC, 800.0)
                .adicionaLance(USUARIO_BOT, 900.0)
                .adicionaLance(USUARIO_PC, 1000.0)
                .adicionaLance(USUARIO_BOT, 1100.0);
    }
}