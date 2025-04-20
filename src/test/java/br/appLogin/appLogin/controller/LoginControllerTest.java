package br.appLogin.appLogin.controller;

import br.appLogin.appLogin.Repository.UsuarioRepository;
import br.appLogin.appLogin.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void deveRedirecionarParaTodolist_QuandoLoginForValido() throws Exception {
        // simula um usuário encontrado
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("teste@email.com");
        usuarioMock.setSenha("123456");

        when(usuarioRepository.login("teste@email.com", "123456"))
                .thenReturn(usuarioMock);

        mockMvc.perform(post("/logar")
                        .param("email", "teste@email.com")
                        .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/todolist"));
    }

    @Test
    void deveRetornarLoginComErro_QuandoLoginForInvalido() throws Exception {
        when(usuarioRepository.login("errado@email.com", "senhaerrada"))
                .thenReturn(null);

        mockMvc.perform(post("/logar")
                        .param("email", "errado@email.com")
                        .param("senha", "senhaerrada"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("erro"))
                .andExpect(view().name("login"));
    }
}

