package br.com.alura.leilao.ui.activity;

import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

public class AtualizardorDeListaLeilao {

    public void buscaListaLeilao(final ListaLeilaoAdapter adapter, LeilaoWebClient client,
                                 final FalhaAtualizarListaListener listener) {
        client.todos(new RespostaListener<List<Leilao>>() {
            @Override
            public void sucesso(List<Leilao> leiloes) {
                adapter.atualiza(leiloes);
            }

            @Override
            public void falha(String mensagem) {
                listener.falhaAtualizarLista(mensagem);
            }
        });
    }

    public interface FalhaAtualizarListaListener {
        void falhaAtualizarLista(String mensagem);
    }
}