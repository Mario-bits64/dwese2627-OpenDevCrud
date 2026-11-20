package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Proyecto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del DAO de Proyecto con EntityManager.
 *
 * @author Mario
 * @version 1.0
 */
@Repository
@Transactional
public class ProyectoDAOImpl implements ProyectoDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Lista una página de proyectos ordenada por el campo y la dirección indicados.
     */
    @Override
    public List<Proyecto> listProyectosPage(int page, int size, String sortField, String sortDir) {
        logger.info("Listing proyectos page={}, size={}, sortField={}, sortDir={} from the database.",
                page, size, sortField, sortDir);
        int offset = page * size;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Proyecto> cq = cb.createQuery(Proyecto.class);
        Root<Proyecto> root = cq.from(Proyecto.class);
        root.fetch("usuario");

        Path<?> sortPath;
        switch (sortField) {
            case "id" -> sortPath = root.get("id");
            case "nombre" -> sortPath = root.get("nombre");
            case "fase" -> sortPath = root.get("fase");
            case "visibilidad" -> sortPath = root.get("visibilidad");
            case "fechaCreacion" -> sortPath = root.get("fechaCreacion");
            case "numVisitas" -> sortPath = root.get("numVisitas");
            case "usuario" -> sortPath = root.get("usuario").get("email");
            default -> {
                logger.warn("Unknown sortField '{}', defaulting to 'nombre'.", sortField);
                sortPath = root.get("nombre");
            }
        }

        boolean descending = "desc".equalsIgnoreCase(sortDir);
        Order order = descending ? cb.desc(sortPath) : cb.asc(sortPath);
        cq.select(root).orderBy(order);

        return entityManager.createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Devuelve el número total de proyectos.
     */
    @Override
    public long countProyectos() {
        String hql = "SELECT COUNT(p) FROM Proyecto p";
        Long total = entityManager.createQuery(hql, Long.class).getSingleResult();
        return (total != null) ? total : 0L;
    }

    /**
     * Busca un proyecto por su id junto con su usuario. Devuelve null si no existe.
     */
    @Override
    public Proyecto getProyectoById(Long id) {
        logger.info("Retrieving proyecto by id: {}", id);
        String hql = "SELECT p FROM Proyecto p JOIN FETCH p.usuario WHERE p.id = :id";
        List<Proyecto> proyectos = entityManager.createQuery(hql, Proyecto.class)
                .setParameter("id", id)
                .getResultList();
        if (proyectos.isEmpty()) {
            logger.warn("No proyecto found with id: {}", id);
            return null;
        }
        return proyectos.getFirst();
    }

    /**
     * Comprueba si existe un proyecto con ese id.
     */
    @Override
    public boolean existsProyectoById(Long id) {
        String hql = "SELECT COUNT(p) FROM Proyecto p WHERE p.id = :id";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count != null && count > 0;
    }

    /**
     * Comprueba si ya existe un proyecto con ese nombre.
     */
    @Override
    public boolean existsProyectoByNombre(String nombre) {
        String hql = "SELECT COUNT(p) FROM Proyecto p WHERE UPPER(p.nombre) = :nombre";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("nombre", nombre.trim().toUpperCase())
                .getSingleResult();
        return count != null && count > 0;
    }

    /**
     * Comprueba si existe otro proyecto con ese nombre, sin contar el del id indicado.
     */
    @Override
    public boolean existsProyectoByNombreAndNotId(String nombre, Long id) {
        String hql = "SELECT COUNT(p) FROM Proyecto p WHERE UPPER(p.nombre) = :nombre AND p.id != :id";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("nombre", nombre.trim().toUpperCase())
                .setParameter("id", id)
                .getSingleResult();
        return count != null && count > 0;
    }

    /**
     * Inserta un proyecto nuevo.
     */
    @Override
    public void insertProyecto(Proyecto proyecto) {
        logger.info("Inserting proyecto with nombre: {}", proyecto.getNombre());
        entityManager.persist(proyecto);
    }

    /**
     * Actualiza un proyecto existente.
     */
    @Override
    public void updateProyecto(Proyecto proyecto) {
        logger.info("Updating proyecto with id: {}", proyecto.getId());
        entityManager.merge(proyecto);
    }

    /**
     * Elimina un proyecto por su id.
     */
    @Override
    public void deleteProyecto(Long id) {
        logger.info("Deleting proyecto with id: {}", id);
        Proyecto proyecto = entityManager.find(Proyecto.class, id);
        if (proyecto != null) {
            entityManager.remove(proyecto);
        }
    }
}
