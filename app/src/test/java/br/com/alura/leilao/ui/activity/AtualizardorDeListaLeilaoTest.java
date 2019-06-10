package br.com.alura.leilao.ui.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AtualizardorDeListaLeilaoTest {
    @Mock
    private LeilaoWebClient client;
    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock
    private AtualizardorDeListaLeilao.FalhaAtualizarListaListener listener;


    @Test
    public void deve_AtualizarLista_QuandoRecebeDadosDaApi() {
        AtualizardorDeListaLeilao buscaLista = new AtualizardorDeListaLeilao();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
                listener.sucesso(new ArrayList<>(Arrays.asList(new Leilao("Computador"),
                        new Leilao("Televisão"))));
                return null;
            }
        }).when(client).todos(any(RespostaListener.class));
        buscaLista.buscaListaLeilao(adapter, client, listener);
        verify(adapter).atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Computador"),
                new Leilao("Televisão"))));
        verify(client).todos(any(RespostaListener.class));
    }

    @Test
    public void deve_MostrarMensagemDeErro_QuandoNaoReceberLista() {
        AtualizardorDeListaLeilao atualizardorDeListaLeilao = new AtualizardorDeListaLeilao();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
                listener.falha(anyString());
                return null;
            }
        }).when(client).todos(any(RespostaListener.class));
        atualizardorDeListaLeilao.buscaListaLeilao(adapter, client, listener);
        verify(listener).falhaAtualizarLista(anyString());
    }

}