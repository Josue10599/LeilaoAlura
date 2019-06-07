package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private RecyclerView recyclerView;
    @Mock
    private ListaUsuarioAdapter adapter;
    @Mock
    private UsuarioDAO dao;

    @Test
    public void deve_AtualizarListaUsuarios_QuandoSalvarUsuario() {
        AtualizadorDeUsuario atualizadorDeUsuario = new AtualizadorDeUsuario(dao, adapter, recyclerView);
        when(dao.salva(new Usuario("Josue"))).thenReturn(new Usuario(1, "Josue"));
        when(adapter.getItemCount()).thenReturn(1);

        atualizadorDeUsuario.salva(new Usuario("Josue"));

        verify(dao).salva(new Usuario("Josue"));
        verify(adapter).adiciona(new Usuario(1, "Josue"));
        verify(recyclerView).smoothScrollToPosition(0);
    }

}