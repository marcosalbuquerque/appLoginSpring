package br.appLogin.appLogin.controller;


import br.appLogin.appLogin.Repository.UsuarioRepository;
import br.appLogin.appLogin.model.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) {
        Usuario usuarioLogado = this.ur.login(usuario.getEmail(), usuario.getSenha());

        if (usuarioLogado != null) {
            return "redirect:/todolist";
        }

        model.addAttribute("erro", "Usuário Inválido!");
        return "login";
    }

    @GetMapping("/todolist")
    public String mostrarTodolist() {
        return "todolist";  // nome do template correto
    }

    // Cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String cadastroUsuario(@Valid Usuario usuario, BindingResult result) {

        if(result.hasErrors()) {
            return "redirect:/cadastro";
        }

        ur.save(usuario);
        return "redirect:/login";
    }
}
