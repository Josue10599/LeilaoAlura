package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.TesteLeilaoWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.fail;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activityTestRule = new ActivityTestRule<>
            (ListaLeilaoActivity.class, true, false);
    private TesteLeilaoWebClient client = new TesteLeilaoWebClient();

    @Test
    public void deve_MostrarUmLeilaoCadastrado_QuandoCarregarUmLeilaoDaApi() throws IOException {
        tentaLimparBanco();
        tentaSalvarNaApi(new Leilao("Computador"));
        activityTestRule.launchActivity(new Intent());
        onView(withText("Computador")).check(matches(isDisplayed()));
    }

    @Test
    public void deve_MostrarDoisLeiloesCadastrados_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        tentaLimparBanco();
        tentaSalvarNaApi(new Leilao("Carro"), new Leilao("Computador"));
        activityTestRule.launchActivity(new Intent());
        onView(withText("Carro")).check(matches(isDisplayed()));
        onView(withText("Computador")).check(matches(isDisplayed()));
    }

    private void tentaSalvarNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = client.salva(leilao);
            if (leilaoSalvo == null) fail("Falha ao salvar leilão");
        }
    }

    private void tentaLimparBanco() throws IOException {
        boolean bancoNaoFoiLimpo = !client.limpaBanco();
        if (bancoNaoFoiLimpo) fail("Falha ao limpar banco!");
    }

}