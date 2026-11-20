package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del DAO de Usuario con EntityManager.
 *
 * @author Mario
 * @version 1.0
 */
@Repository
@Transactional
public class UsuarioDAOImpl implements UsuarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Lista todos los usuarios ordenados por email.
     */
    @Override
    public List<Usuario> listAllUsuarios() {
        logger.info("Listing all usuarios from the database.");
        String hql = "SELECT u FROM Usuario u ORDER BY u.email";
        List<Usuario> usuarios = entityManager.createQuery(hql, Usuario.class).getResultList();
        logger.info("Retrieved {} usuarios from the database.", usuarios.size());
        return usuarios;
    }

    /**
     * Busca un usuario por su id. Devuelve null si no existe.
     */
    @Override
    public Usuario getUsuarioById(Long id) {
        logger.info("Retrieving usuario by id: {}", id);
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario == null) {
            logger.warn("No usuario found with id: {}", id);
        }
        return usuario;
    }

    /**
     * Comprueba si existe un usuario con ese id.
     */
    @Override
    public boolean existsUsuarioById(Long id) {
        String hql = "SELECT COUNT(u) FROM Usuario u WHERE u.id = :id";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count != null && count > 0;
    }
}
