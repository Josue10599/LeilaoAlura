package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ViewMatchers {

    public static Matcher<? super View> leilaoApresentado(final int posicaoDoLeilao,
                                                          final String nomeDoLeilao,
                                                          final double maiorLance) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private final Matcher<View> displayed = isDisplayed();
            private final FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
            private final String maiorLanceFormatado = formatadorDeMoeda.formata(maiorLance);

            @Override
            public void describeTo(Description description) {
                description.appendText("Dados do Leilão não encontrados na View: Posição informada do leilão ")
                        .appendValue(posicaoDoLeilao).appendText(", nome informado do leilão ")
                        .appendValue(nomeDoLeilao).appendText(", maior lance informado ")
                        .appendValue(maiorLanceFormatado).appendText(" ").appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolderDevolvido = item.findViewHolderForAdapterPosition(posicaoDoLeilao);
                if (viewHolderDevolvido == null) {
                    throw new IndexOutOfBoundsException
                            ("View da ViewHolder na posição " + posicaoDoLeilao + " não foi encontrada");
                }
                View viewHolder = viewHolderDevolvido.itemView;
                return verificaSeEhMesmoLeilao(viewHolder) && verificaSeEhMesmoMaiorLance(viewHolder)
                        && displayed.matches(viewHolder);
            }

            private boolean verificaSeEhMesmoMaiorLance(View viewHolder) {
                TextView textViewMaiorLance = viewHolder.findViewById(R.id.item_leilao_maior_lance);
                return textViewMaiorLance.getText().toString()
                        .equals(maiorLanceFormatado) && displayed.matches(textViewMaiorLance);
            }

            private boolean verificaSeEhMesmoLeilao(View viewHolder) {
                TextView textViewDescricao = viewHolder.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText().toString().equals(nomeDoLeilao)
                        && displayed.matches(textViewDescricao);
            }
        };
    }
}
