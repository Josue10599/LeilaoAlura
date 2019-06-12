package br.com.alura.leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.TesteLeilaoWebClient;
import br.com.alura.leilao.model.Leilao;

import static junit.framework.Assert.fail;

public abstract class BaseTesteIntegracao {

    private final TesteLeilaoWebClient client = new TesteLeilaoWebClient();

    protected void limpaBancoDeDados() {
        Context context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase(BuildConfig.BANCO_DE_DADOS);
    }

    protected void tentaLimparBancoDeDadosDaApi() throws IOException {
        boolean bancoNaoFoiLimpo = !client.limpaBanco();
        if (bancoNaoFoiLimpo) fail("Falha ao limpar banco!");
    }

    protected void tentaSalvarNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = client.salva(leilao);
            if (leilaoSalvo == null) fail("Falha ao salvar leilão");
        }
    }
}
