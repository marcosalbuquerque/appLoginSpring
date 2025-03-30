package br.appLogin.appLogin.Repository;

import br.appLogin.appLogin.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Usuario findById(long id);

    @Query(value = "SELECT * FROM loginapp.usuario WHERE email = :email AND :senha", nativeQuery = true)
    public Usuario login(String email, String senha);
}
