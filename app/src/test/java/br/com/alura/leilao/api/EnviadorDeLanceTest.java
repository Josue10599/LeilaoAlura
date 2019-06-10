package br.com.alura.leilao.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    Leilao computador;
    @Mock
    private LeilaoWebClient client;
    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;
    @Mock
    private AvisoDialogManager avisoDialogManager;

    @Test
    public void deve_ApresentarMensagemLanceMenorQueUltimo_QuandoFizerLanceMenor() {
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);
        doThrow(LanceMenorQueUltimoLanceException.class).when(computador).propoe(any(Lance.class));

        enviadorDeLance.envia(computador, new Lance(new Usuario("Josue"), 100.0));

        verify(avisoDialogManager).mostraAvisoLanceMenorQueUltimoLance();
        verify(client, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
    }

    @Test
    public void deve_MostrarMensagemDeErro_QuandoUsuarioFizerLancesSeguidos() {
        EnviadorDeLance enviadorDeLance = spy(new EnviadorDeLance(client, listener, avisoDialogManager));
        doThrow(LanceSeguidoDoMesmoUsuarioException.class).when(computador).propoe(any(Lance.class));

        enviadorDeLance.envia(computador, new Lance(new Usuario("Josue"), 100.0));

        verify(avisoDialogManager).mostraAvisoLanceSeguidoDoMesmoUsuario();
        verify(client, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
    }

    @Test
    public void deve_MostraMensagemDeErro_QuandoUsuarioFizerCincoLances() {
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);
        doThrow(UsuarioJaDeuCincoLancesException.class).when(computador).propoe(any(Lance.class));

        enviadorDeLance.envia(computador, new Lance(new Usuario("Josue"), 100.0));

        verify(avisoDialogManager).mostraAvisoUsuarioJaDeuCincoLances();
    }


    @Test
    public void deve_MostraMensagemDeFalha_QuandoFalharEnvioDeLanceParaAPI() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, avisoDialogManager);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<Void> argument = invocation.getArgument(2);
                argument.falha("");
                return null;
            }
        }).when(client).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

        enviador.envia(new Leilao("Computador"), new Lance(new Usuario("Josue"), 200));

        verify(avisoDialogManager).mostraToastFalhaNoEnvio();
        verify(listener, never()).processado(new Leilao("Computador"));
    }

    @Test
    public void deve_NotificarLanceProcessado_QuandoEnviarLanceParaApiComSucesso() {
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<Void> listener = invocation.getArgument(2);
                listener.sucesso(any(Void.class));
                return null;
            }
        }).when(client).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
        enviadorDeLance.envia(new Leilao("Computador"), new Lance(new Usuario("Josue"),
                10.0));
        verify(listener).processado(new Leilao("Computador"));
        verify(avisoDialogManager, never()).mostraToastFalhaNoEnvio();
    }
}