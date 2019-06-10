package br.com.alura.leilao.ui.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(ListaLeilaoActivity.class,
            true, true);

    @Test
    public void deve_MostrarLeilaoCadastrado_QuandoCarregarLeilaoDaApi() {
        Espresso.onView(ViewMatchers.withText("Computador"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}