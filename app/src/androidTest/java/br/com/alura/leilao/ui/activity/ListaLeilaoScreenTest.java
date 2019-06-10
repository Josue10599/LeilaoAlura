package br.com.alura.leilao.ui.activity;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activityTestRule = new ActivityTestRule<>
            (ListaLeilaoActivity.class, true, true);

    @Test
    public void deve_MostrarLeilaoCadastrado_QuandoCarregarLeilaoDaApi() {
        onView(ViewMatchers.withText("Computador")).check(matches(isDisplayed()));
    }

}