package br.com.alura.leilao.api.retrofit;

import br.com.alura.leilao.api.retrofit.service.TesteLeilaoService;

public class TesteRetrofitInicializador extends RetrofitInicializador {

    public TesteLeilaoService getTesteLeilaoService() {
        return retrofit.create(TesteLeilaoService.class);
    }
}
