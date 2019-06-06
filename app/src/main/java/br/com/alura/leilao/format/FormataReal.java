package br.com.alura.leilao.format;

import java.text.DecimalFormat;
import java.util.Locale;

public class FormataReal {

    public static String formataValor(double valor) {
        Locale.setDefault(new Locale("pt", "BR"));
        final DecimalFormat decimalFormat = new DecimalFormat("R$#,##0.00");
        return decimalFormat.format(valor);
    }
}
