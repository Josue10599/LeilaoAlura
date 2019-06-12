package br.com.alura.leilao.ui.activity;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListaUsuariosScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mActivityTestRule = new ActivityTestRule<>(ListaLeilaoActivity.class);

    @Before
    public void setup() {
        limpaBancoDeDados();
    }

    @Test
    public void listaUsuariosScreenTest() {
        onView(allOf(withId(R.id.lista_leilao_menu_usuarios), withContentDescription("Usuários"),
                isDescendantOfA(withId(R.id.action_bar)), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.form_usuario_nome_editText), isDisplayed()))
                .perform(click()).perform(replaceText("Josue"), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1), withText("Adicionar"), isDisplayed()))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.item_usuario_id_com_nome), isDisplayed()))
                .check(matches(withText("(1) Josue")));
    }

    @After
    public void tearDown() {
        limpaBancoDeDados();
    }

    private void limpaBancoDeDados() {
        Context context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase(BuildConfig.BANCO_DE_DADOS);
    }
}
