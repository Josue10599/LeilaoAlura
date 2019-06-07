package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarLista_QuandoRecebeNovosValores() {
        Context context = Mockito.mock(Context.class);
        ListaLeilaoAdapter adapter = Mockito.spy(new ListaLeilaoAdapter(context));
        Mockito.doNothing().when(adapter).atualizaLista();
        adapter.atualiza(Arrays.asList(new Leilao("Computador"), new Leilao("CD")));
        int numeroDeItensNaLista = adapter.getItemCount();
        Mockito.verify(adapter).atualizaLista();
        assertThat(numeroDeItensNaLista, is(equalTo(2)));
    }
}