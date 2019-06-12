package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.alura.leilao.matchers.ViewMatchers.leilaoApresentado;
import static org.hamcrest.core.AllOf.allOf;

public class ListaLeilaoScreenTest extends BaseTesteIntegracao {

    private final FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activityTestRule = new ActivityTestRule<>
            (ListaLeilaoActivity.class, true, false);

    @Before
    public void setup() throws IOException {
        tentaLimparBancoDeDadosDaApi();
    }

    @After
    public void tearDown() throws IOException {
        tentaLimparBancoDeDadosDaApi();
    }

    @Test
    public void deve_MostrarUmLeilaoCadastrado_QuandoCarregarUmLeilaoDaApi() throws IOException {
        tentaSalvarNaApi(new Leilao("Computador"));

        activityTestRule.launchActivity(new Intent());
        String formatoEsperado = formatadorDeMoeda.formata(0.00);

        onView(allOf(withText("Computador"), withId(R.id.item_leilao_descricao)))
                .check(matches(isDisplayed()));
        onView(allOf(withText(formatoEsperado), withId(R.id.item_leilao_maior_lance)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_MostrarDoisLeiloesCadastrados_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        tentaSalvarNaApi(new Leilao("Carro"), new Leilao("Computador"));
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.lista_leilao_recyclerview)).check(matches(
                leilaoApresentado(0, "Carro", 0.00)));
        onView(withId(R.id.lista_leilao_recyclerview)).check(matches(
                leilaoApresentado(1, "Computador", 0.00)));
    }

    @Test
    public void deve_AparecerUltimoLeilao_QuandoCarregarDezLeiloesNaApi() throws IOException {
        tentaSalvarNaApi(new Leilao("Carro"),
                new Leilao("Computador"),
                new Leilao("Notebook"),
                new Leilao("Placa de vídeo"),
                new Leilao("Smartphone"),
                new Leilao("Monitor LED"),
                new Leilao("Ar condicionado"),
                new Leilao("Humano"),
                new Leilao("Tênis"),
                new Leilao("Calça Jeans"));
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(9))
                .check(matches(leilaoApresentado(
                        9, "Calça Jeans", 0.00)));
    }
}