package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.alura.leilao.R;
import br.com.alura.leilao.format.FormataReal;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());
            TextView maiorLance = findViewById(R.id.lances_leilao_maior_lance);
            maiorLance.setText(FormataReal.formataValor(leilao.getMaiorValor()));
            TextView menorLance = findViewById(R.id.lances_leilao_menor_lance);
            menorLance.setText(FormataReal.formataValor(leilao.getMenorValor()));
            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
            StringBuilder builder = new StringBuilder();
            for (Lance lance : leilao.getTresMaioresLances()) {
                builder.append(FormataReal.formataValor(lance.getValor())).append("\n");
            }
            String tresMaioresLances = builder.toString();
            maioresLances.setText(tresMaioresLances);
        }
    }
}
