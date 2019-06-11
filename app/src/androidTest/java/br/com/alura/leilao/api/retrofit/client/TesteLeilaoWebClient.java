package br.com.alura.leilao.api.retrofit.client;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.TesteRetrofitInicializador;
import br.com.alura.leilao.api.retrofit.service.TesteLeilaoService;
import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.Response;

public class TesteLeilaoWebClient extends WebClient {

    private final TesteLeilaoService service;

    public TesteLeilaoWebClient() {
        this.service = new TesteRetrofitInicializador().getTesteLeilaoService();
    }

    public Leilao salva(Leilao leilao) throws IOException {
        Call<Leilao> call = service.salvaLeilao(leilao);
        Response<Leilao> response = call.execute();
        if (temDados(response)) {
            return response.body();
        }
        return null;
    }

    public boolean limpaBanco() throws IOException {
        Call<Void> call = service.limpaBancoDeDados();
        Response<Void> response = call.execute();
        return response.isSuccessful();
    }
}
