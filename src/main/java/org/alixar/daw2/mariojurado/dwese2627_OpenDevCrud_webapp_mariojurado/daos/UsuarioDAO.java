package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Usuario;

import java.util.List;

/**
 * DAO de la entidad Usuario.
 *
 * @author Mario
 * @version 1.0
 */
public interface UsuarioDAO {

    List<Usuario> listAllUsuarios();

    Usuario getUsuarioById(Long id);

    boolean existsUsuarioById(Long id);
}
