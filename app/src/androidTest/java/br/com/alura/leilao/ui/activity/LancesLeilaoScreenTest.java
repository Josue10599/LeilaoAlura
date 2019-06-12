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

    @Before
    public void setup() throws IOException {
        tentaLimparBancoDeDadosDaApi();
        limpaBancoDeDados();
    }

    @Test
    public void deve_AparecerLanceNoMaiorMenorEListaLance_QuandoUsuarioFizerOPrimeiroLance() throws IOException {
        //Salvar lance
        tentaSalvarNaApi(new Leilao("Carro"));

        //Iniciarlizar Activity principal
        activityTestRule.launchActivity(new Intent());

        //Clicar no lance
        onView(allOf(withId(R.id.lista_leilao_recyclerview), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clicar no FloatingButton para adicionar lance
        onView(withId(R.id.lances_leilao_fab_adiciona)).perform(click());

        //Verificar se abriu o Dialog e clicar em cadastrar usuario
        onView(withText("Usuários não encontrados")).check(matches(isDisplayed()));
        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance.")).check(matches(isDisplayed()));
        onView(withText("Cadastrar usuário")).perform(scrollTo(), click());

        //Clicar no FloatingButton para adicionar um usuário
        onView(withId(R.id.lista_usuario_fab_adiciona)).perform(click());

        //Clicar no EditText e adicionar o texto "Josue"
        onView(withId(R.id.form_usuario_nome_editText))
                .perform(click(), typeText("Josue"), closeSoftKeyboard());

        //Clicar no botão adicionar
        onView(withText("Adicionar")).perform(click());

        //Clicar no retorno do Android para retornar a tela de lance
        pressBack();

        //Clicar no FloatingButton para adicionar um lance
        onView(withId(R.id.lances_leilao_fab_adiciona)).perform(click());

        //Clicar no EditText e adicionar o valor "200"
        onView(withId(R.id.form_lance_valor_editText))
                .perform(click(), typeText("200"), closeSoftKeyboard());

        //Clicar no Spinner e selecionar o usuário "Josue"
        onView(withId(R.id.form_lance_usuario)).perform(click());
        onData(allOf(is(instanceOf(Usuario.class)), is(new Usuario(1, "Josue"))))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //Clicar no botão propor
        onView(withText("Propor")).perform(click());

        //Verificar se o lance apareceu no maior lance, no menor lance e na lista maiores lances
        String valor = new FormatadorDeMoeda().formata(200.0);
        onView(withId(R.id.lances_leilao_maior_lance)).check(matches(allOf(withText(valor), isDisplayed())));
        onView(withId(R.id.lances_leilao_menor_lance)).check(matches(allOf(withText(valor), isDisplayed())));
        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText("200.0 - (1) Josue\n"), isDisplayed())));
    }

    @After
    public void tearDown() throws IOException {
        tentaLimparBancoDeDadosDaApi();
        limpaBancoDeDados();
    }

}