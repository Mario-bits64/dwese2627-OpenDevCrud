package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.services;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.*;

import java.util.List;

/**
 * Servicio del CRUD de proyectos.
 *
 * @author Mario
 * @version 1.0
 */
public interface ProyectoService {

    List<ProyectoDTO> listPage(int page, int size, String sortField, String sortDir);

    long count();

    ProyectoDetailDTO getDetail(Long id);

    ProyectoUpdateDTO getForEdit(Long id);

    void create(ProyectoCreateDTO dto);

    void update(ProyectoUpdateDTO dto);

    void delete(Long id);
}
