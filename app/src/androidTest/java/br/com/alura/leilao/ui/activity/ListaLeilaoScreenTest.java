package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.TesteLeilaoWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activityTestRule = new ActivityTestRule<>
            (ListaLeilaoActivity.class, true, false);

    @Test
    public void deve_MostrarUmLeilaoCadastrado_QuandoCarregarUmLeilaoDaApi() throws IOException {
        TesteLeilaoWebClient client = new TesteLeilaoWebClient();
        boolean bancoNaoFoiLimpo = !client.limpaBanco();
        if (bancoNaoFoiLimpo) Assert.fail("Falha ao limpar banco!");
        Leilao computador = client.salva(new Leilao("Computador"));
        if (computador == null) Assert.fail("Falha ao salvar leilão");
        activityTestRule.launchActivity(new Intent());
        onView(withText("Computador")).check(matches(isDisplayed()));
    }

    @Test
    public void deve_MostrarDoisLeiloesCadastrados_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        TesteLeilaoWebClient client = new TesteLeilaoWebClient();
        if (!client.limpaBanco()) Assert.fail("Falha ao limpar banco");
        Leilao carroSalvo = client.salva(new Leilao("Carro"));
        Leilao computadorSalvo = client.salva(new Leilao("Computador"));
        activityTestRule.launchActivity(new Intent());
        onView(withText("Carro")).check(matches(isDisplayed()));
        onView(withText("Computador")).check(matches(isDisplayed()));
    }

}