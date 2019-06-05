package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(1000.0, maiorValorDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeUmLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 100.0));
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertEquals(100.0, menorValorDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeLancesEmOrdemCrescente() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 10.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Pc"), 100.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Mac"), 1000.0));
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertEquals(10.0, menorValorDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeTresLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 750.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1000.0));
        List<Lance> maioresLancesDevolvidos = COMPUTADOR.getTresMaioresLances();
        assertEquals(3, maioresLancesDevolvidos.size());
        assertEquals(1000.0, maioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(750.0, maioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(500.0, maioresLancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeUmLance() {
        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR.getTresMaioresLances();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeDoisLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 750.0));
        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR.getTresMaioresLances();
        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(750.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresValores_QuandoRecebeVariosLances() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 100.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 200.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 700.0));

        List<Lance> tresMaioresLancesDevolvidosComQuatroLances = COMPUTADOR.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosComQuatroLances.size());
        assertEquals(700.0, tresMaioresLancesDevolvidosComQuatroLances.get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosComQuatroLances.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidosComQuatroLances.get(2).getValor(), DELTA);

        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 800.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 1000.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1200.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 1500.0));

        List<Lance> tresMaioresLancesDevolvidosComOitoLances = COMPUTADOR.getTresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosComOitoLances.size());
        assertEquals(1500.0, tresMaioresLancesDevolvidosComOitoLances.get(0).getValor(), DELTA);
        assertEquals(1200.0, tresMaioresLancesDevolvidosComOitoLances.get(1).getValor(), DELTA);
        assertEquals(1000.0, tresMaioresLancesDevolvidosComOitoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverZeroParaMaiorLance_QuandoNaoTiverLance() {
        double maiorValorDevolvido = COMPUTADOR.getMaiorValor();
        assertEquals(0, maiorValorDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverZeroParaMenorLance_QuandoNaoTiverLance() {
        double menorValorDevolvido = COMPUTADOR.getMenorValor();
        assertEquals(0, menorValorDevolvido, DELTA);
    }

    @Test
    public void naoDeve_AdicionarLance_QuandoForMenorQueMaiorLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 500.0));
        int quantidadeLancesDevolvida = COMPUTADOR.getQuantidadeLances();
        assertEquals(1, quantidadeLancesDevolvida);
    }

    @Test
    public void naoDeve_AdicionarLance_QuandoForMesmoUsuarioDoUltimoLance() {
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 5000.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 10000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("PC"), 12000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Bot"), 15000.0));
        COMPUTADOR.proporLance(new Lance(new Usuario("Bot"), 15100.0));
        int quantidadeLancesDevolvidos = COMPUTADOR.getQuantidadeLances();
        assertEquals(3, quantidadeLancesDevolvidos);
    }

    @Test
    public void naoDeve_AdicionarLance_QuandoUsuarioFezCincoLances() {
        final Usuario USUARIO_PC = new Usuario("PC");
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 100.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 200.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 300.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 400.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 500.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 600.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 700.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 800.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 900.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 1000.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_BOT, 1100.0));
        COMPUTADOR.proporLance(new Lance(USUARIO_PC, 1200.0));
        int quantidadeLancesDevolvidos = COMPUTADOR.getQuantidadeLances();
        assertEquals(10, quantidadeLancesDevolvidos);
    }
}