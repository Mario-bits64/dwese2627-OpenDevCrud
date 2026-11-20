package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Proyecto;

import java.util.List;

/**
 * DAO de la entidad Proyecto.
 *
 * @author Mario
 * @version 1.0
 */
public interface ProyectoDAO {

    List<Proyecto> listProyectosPage(int page, int size, String sortField, String sortDir);

    long countProyectos();

    Proyecto getProyectoById(Long id);

    boolean existsProyectoById(Long id);

    boolean existsProyectoByNombre(String nombre);

    boolean existsProyectoByNombreAndNotId(String nombre, Long id);

    void insertProyecto(Proyecto proyecto);

    void updateProyecto(Proyecto proyecto);

    void deleteProyecto(Long id);
}
