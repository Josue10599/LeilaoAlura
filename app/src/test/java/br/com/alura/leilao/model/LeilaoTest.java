package br.com.alura.leilao.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    /**
     * Para padronizar e melhor descrever o que um método faz a comunidade Java criou alguns padrões
     * para boas práticas na implementação de teste, uma delas é a nomeação descritiva dos métodos,
     * onde há alguns padrões como:
     * <p>
     * - [nome do método][estado de teste][resultado esperado]
     * - [deve][resultado esperado][estado de teste]
     */

    @Test
    public void deve_DevolverDescrição_QuandoRecebeUmaDescricao() {
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
        Leilao console = new Leilao("Console");
        console.propoeLance(new Lance(new Usuario("Bot"), 200.0));
        // Executar ação esperada
        double maiorValorDevolvido = console.getMaiorValor();
        // Testar o resultado esperado
        assertEquals(200.0, maiorValorDevolvido, 0.0001);
        /**
         * DELTA = o Delta é o valor utilizado para a comparação de números com pontos flutuantes
         * com ele colocamos uma margem de diferença que pode haver entre os dois valores passados,
         * para que o computador entenda como números iguais, ou seja, se o delta que passamos for
         * maior que a diferença entre o dois números que comparamos significa que eles são equivalentes.
         * */

    }

    @Test
    public void deve_DevolverMaiorValor_QuandoRecebeLancesEmOrdemCrescente() {
        Leilao computador = new Leilao("Computador");
        computador.propoeLance(new Lance(new Usuario("Bot"), 10.0));
        computador.propoeLance(new Lance(new Usuario("Pc"), 100.0));
        computador.propoeLance(new Lance(new Usuario("Machine"), 1000.0));
        double maiorValorDevolvido = computador.getMaiorValor();
        assertEquals(1000.0, maiorValorDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMaiorValor_QuandoRecebeLancesEmOrdemDescrescente() {
        Leilao carro = new Leilao("Carro");
        carro.propoeLance(new Lance(new Usuario("Bot"), 1000.0));
        carro.propoeLance(new Lance(new Usuario("Pc"), 500.0));
        carro.propoeLance(new Lance(new Usuario("Mac"), 100.0));
        double maiorValorDevolvido = carro.getMaiorValor();
        assertEquals(1000.0, maiorValorDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeUmLance() {
        Leilao computador = new Leilao("Computador");
        computador.propoeLance(new Lance(new Usuario("Bot"), 100.0));
        double menorValorDevolvido = computador.getMenorValor();
        assertEquals(100.0, menorValorDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeLancesEmOrdemCrescente() {
        Leilao computador = new Leilao("Computador");
        computador.propoeLance(new Lance(new Usuario("Bot"), 10.0));
        computador.propoeLance(new Lance(new Usuario("Pc"), 100.0));
        computador.propoeLance(new Lance(new Usuario("Machine"), 1000.0));
        double menorValorDevolvido = computador.getMenorValor();
        assertEquals(10.0, menorValorDevolvido, 0.0001);
    }

    @Test
    public void deve_DevolverMenorValor_QuandoRecebeLancesEmOrdemDescrescente() {
        Leilao carro = new Leilao("Carro");
        carro.propoeLance(new Lance(new Usuario("Bot"), 1000.0));
        carro.propoeLance(new Lance(new Usuario("Pc"), 500.0));
        carro.propoeLance(new Lance(new Usuario("Mac"), 100.0));
        double menorValorDevolvido = carro.getMenorValor();
        assertEquals(100.0, menorValorDevolvido, 0.0001);
    }
}