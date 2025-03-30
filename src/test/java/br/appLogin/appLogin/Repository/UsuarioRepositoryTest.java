package br.appLogin.appLogin.Repository;

import br.appLogin.appLogin.model.Usuario;
import br.appLogin.appLogin.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioTeste;

    @BeforeEach
    void setUp() {
        usuarioTeste = new Usuario();
        usuarioTeste.setEmail("teste@email.com");
        usuarioTeste.setSenha("123456");
    }

    @Test
    void testLogin_UsuarioExistente() {
        when(usuarioRepository.login("teste@email.com", "123456")).thenReturn(usuarioTeste);

        Usuario resultado = usuarioRepository.login("teste@email.com", "123456");

        assertNotNull(resultado);
        assertEquals("teste@email.com", resultado.getEmail());
        assertEquals("123456", resultado.getSenha());

        verify(usuarioRepository, times(1)).login("teste@email.com", "123456");
    }

    @Test
    void testLogin_UsuarioNaoEncontrado() {
        when(usuarioRepository.login("naoexiste@email.com", "senhaerrada")).thenReturn(null);

        Usuario resultado = usuarioRepository.login("naoexiste@email.com", "senhaerrada");

        assertNull(resultado);

        verify(usuarioRepository, times(1)).login("naoexiste@email.com", "senhaerrada");
    }

    @Test
    void testLogin_EmailCorretoSenhaErrada() {
        when(usuarioRepository.login("teste@email.com", "senhaErrada")).thenReturn(null);

        Usuario resultado = usuarioRepository.login("teste@email.com", "senhaErrada");

        assertNull(resultado);

        verify(usuarioRepository, times(1)).login("teste@email.com", "senhaErrada");
    }
}
