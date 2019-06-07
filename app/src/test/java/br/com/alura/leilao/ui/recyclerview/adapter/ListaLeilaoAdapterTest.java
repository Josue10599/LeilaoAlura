package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    @Mock
    Context context;
    @Spy
    ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(context);

    @Test
    public void deve_AtualizarLista_QuandoRecebeNovosValores() {
        doNothing().when(adapter).atualizaLista();
        adapter.atualiza(Arrays.asList(new Leilao("Computador"), new Leilao("CD")));
        int numeroDeItensNaLista = adapter.getItemCount();
        verify(adapter).atualizaLista();
        assertThat(numeroDeItensNaLista, is(equalTo(2)));
    }
}