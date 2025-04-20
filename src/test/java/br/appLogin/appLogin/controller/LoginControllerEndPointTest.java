package br.appLogin.appLogin.controller;

import br.appLogin.appLogin.Repository.UsuarioRepository;
import br.appLogin.appLogin.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void deveExibirPaginaDeLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void deveRedirecionarParaTodolist_QuandoLoginValido() throws Exception {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("teste@email.com");
        usuarioMock.setSenha("123456");

        when(usuarioRepository.login("teste@email.com", "123456")).thenReturn(usuarioMock);

        mockMvc.perform(post("/logar")
                        .param("email", "teste@email.com")
                        .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todolist"));
    }

    @Test
    void deveRetornarLoginComErro_QuandoLoginInvalido() throws Exception {
        when(usuarioRepository.login("email@errado.com", "senhaerrada")).thenReturn(null);

        mockMvc.perform(post("/logar")
                        .param("email", "email@errado.com")
                        .param("senha", "senhaerrada"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("erro"))
                .andExpect(view().name("login"));
    }

    @Test
    void deveExibirPaginaDeCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("cadastro"));
    }

    @Test
    void deveCadastrarUsuarioERedirecionarParaLogin() throws Exception {
        mockMvc.perform(post("/cadastro")
                        .param("nome", "Usuário Teste") // adicione todos os campos obrigatórios
                        .param("email", "novo@usuario.com")
                        .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login")); // aqui espera o redirect certo


        // Verifica se salvou o usuário
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
