package br.com.alura.leilao.adapter;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarLista_QuandoRecebeNovosValores() {
        ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(InstrumentationRegistry.getContext());
        adapter.atualiza(Arrays.asList(new Leilao("Computador"), new Leilao("CD")));
        int numeroDeItensNaLista = adapter.getItemCount();
        assertThat(numeroDeItensNaLista, is(equalTo(2)));
    }
}