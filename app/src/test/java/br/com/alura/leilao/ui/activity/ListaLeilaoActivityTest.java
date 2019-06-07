package br.com.alura.leilao.ui.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoActivityTest {

    @Mock
    private LeilaoWebClient client;
    @Mock
    private ListaLeilaoAdapter adapter;

    @Test
    public void deve_AtualizarLista_QuandoRecebeDadosDaApi() {
        ListaLeilaoActivity activity = new ListaLeilaoActivity();
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
                listener.sucesso(new ArrayList<>(Arrays.asList(new Leilao("Computador"),
                        new Leilao("Televisão"))));
                return null;
            }
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));
        activity.buscaListaLeilao(adapter, client);
        Mockito.verify(adapter).atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Computador"),
                new Leilao("Televisão"))));
        Mockito.verify(client).todos(ArgumentMatchers.any(RespostaListener.class));
    }

}