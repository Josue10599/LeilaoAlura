package br.com.alura.leilao.api.retrofit.service;

import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TesteLeilaoService {

    @POST("leilao")
    Call<Leilao> salvaLeilao(@Body Leilao leilao);

    @GET("reset")
    Call<Void> limpaBancoDeDados();
}
