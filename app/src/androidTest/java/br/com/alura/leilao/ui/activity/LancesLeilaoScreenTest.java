package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
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
import br.com.alura.leilao.model.Usuario;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class LancesLeilaoScreenTest extends BaseTesteIntegracao {
    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activityTestRule =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);
    private FormatadorDeMoeda formataMoeda = new FormatadorDeMoeda();

    @Before
    public void setup() throws IOException {
        tentaLimparBancoDeDadosDaApi();
        limpaBancoDeDados();
    }

    @Test
    public void deve_AparecerLanceNoMaiorMenorEListaLance_QuandoUsuarioFizerOPrimeiroLance() throws IOException {
        tentaSalvarNaApi(new Leilao("Carro"));

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.lances_leilao_fab_adiciona), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.alertTitle), withText("Usuários não encontrados")))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.R.id.message),
                withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance.")))
                .check(matches(isDisplayed()));
        onView(allOf(withText("Cadastrar usuário"), isDisplayed())).perform(scrollTo(), click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.form_usuario_nome_editText), isDisplayed()))
                .perform(click(), typeText("Josue"), closeSoftKeyboard());

        onView(allOf(withText("Adicionar"), isDisplayed())).perform(click());

        pressBack();

        realizarLance(1, "Josue", "200");

        String valor = formataMoeda.formata(200.0);
        onView(withId(R.id.lances_leilao_maior_lance)).check(matches(allOf(withText(valor), isDisplayed())));
        onView(withId(R.id.lances_leilao_menor_lance)).check(matches(allOf(withText(valor), isDisplayed())));
        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(valor + " - (1) Josue\n"), isDisplayed())));
    }

    @Test
    public void deve_ApresentarSequenciaDeLance_AposReceberTresLances() throws IOException {
        tentaSalvarNaApi(new Leilao("Carro"));
        tentaSalvarUsuarioNoBanco(new Usuario("Josue"), new Usuario("Luana"));

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        realizarLance(1, "Josue", "200");
        realizarLance(2, "Luana", "300");
        realizarLance(1, "Josue", "500");

        FormatadorDeMoeda formataMoeda = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance)).check(matches(allOf(withText(formataMoeda.formata(500.0)), isDisplayed())));
        onView(withId(R.id.lances_leilao_menor_lance)).check(matches(allOf(withText(formataMoeda.formata(200.0)), isDisplayed())));
        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formataMoeda.formata(500) + " - (1) Josue\n" +
                        formataMoeda.formata(300) + " - (2) Luana\n" +
                        formataMoeda.formata(200) + " - (1) Josue\n"), isDisplayed())));
    }

    @Test
    public void deve_ApresentarValorCorreta_QuandoReceberUmLanceMuitoAlto() throws IOException {
        tentaSalvarNaApi(new Leilao("Carro"));
        tentaSalvarUsuarioNoBanco(new Usuario("Josue"));

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        realizarLance(1, "Josue", "2000000000");

        FormatadorDeMoeda formataMoeda = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance)).check(matches(allOf(withText(formataMoeda.formata(2000000000)), isDisplayed())));
        onView(withId(R.id.lances_leilao_menor_lance)).check(matches(allOf(withText(formataMoeda.formata(2000000000)), isDisplayed())));
        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formataMoeda.formata(2000000000) + " - (1) Josue\n"), isDisplayed())));
    }

    @After
    public void tearDown() throws IOException {
        tentaLimparBancoDeDadosDaApi();
        limpaBancoDeDados();
    }

    private void realizarLance(int id, String usuario, String valor) {
        onView(allOf(withId(R.id.lances_leilao_fab_adiciona), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.alertTitle), withText("Novo lance")))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.form_lance_valor_editText), isDisplayed()))
                .perform(click(), typeText(valor), closeSoftKeyboard());

        onView(allOf(withId(R.id.form_lance_usuario), isDisplayed())).perform(click());
        onData(allOf(is(instanceOf(Usuario.class)), is(new Usuario(id, usuario))))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(allOf(withText("Propor"), isDisplayed())).perform(click());
    }

}