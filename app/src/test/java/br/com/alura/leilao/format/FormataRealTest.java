package br.com.alura.leilao.format;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FormataRealTest {

    @Test
    public void deve_DevolverUmTextoFormatadoEmReal_QuandoRecebeUmValorDouble() {
        String valorFormatadoDevolvido = FormataReal.formataValor(10000.0);
        assertThat(valorFormatadoDevolvido, is(equalTo("R$10.000,00")));
    }

}