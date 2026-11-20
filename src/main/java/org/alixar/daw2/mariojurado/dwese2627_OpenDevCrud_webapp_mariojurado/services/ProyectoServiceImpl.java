package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.services;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos.ProyectoDAO;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos.UsuarioDAO;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.*;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Proyecto;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Usuario;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions.DuplicateResourceException;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions.ResourceNotFoundException;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.mappers.ProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio del CRUD de proyectos.
 *
 * @author Mario
 * @version 1.0
 */
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    @Autowired
    private ProyectoDAO proyectoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Devuelve una página de proyectos ordenada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTO> listPage(int page, int size, String sortField, String sortDir) {
        return ProyectoMapper.toDTOList(proyectoDAO.listProyectosPage(page, size, sortField, sortDir));
    }

    /**
     * Devuelve el número total de proyectos.
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return proyectoDAO.countProyectos();
    }

    /**
     * Devuelve el detalle de un proyecto con su usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public ProyectoDetailDTO getDetail(Long id) {
        Proyecto proyecto = proyectoDAO.getProyectoById(id);
        if (proyecto == null) {
            throw new ResourceNotFoundException("proyecto", "id", id);
        }
        return ProyectoMapper.toDetailDTO(proyecto);
    }

    /**
     * Devuelve los datos de un proyecto para el formulario de edición.
     */
    @Override
    @Transactional(readOnly = true)
    public ProyectoUpdateDTO getForEdit(Long id) {
        Proyecto proyecto = proyectoDAO.getProyectoById(id);
        if (proyecto == null) {
            throw new ResourceNotFoundException("proyecto", "id", id);
        }
        return ProyectoMapper.toUpdateDTO(proyecto);
    }

    /**
     * Crea un proyecto nuevo. La fecha de creación se pone aquí.
     */
    @Override
    public void create(ProyectoCreateDTO dto) {
        if (proyectoDAO.existsProyectoByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("proyecto", "nombre", dto.getNombre());
        }

        Usuario usuario = findUsuario(dto.getUsuarioId());
        Proyecto proyecto = ProyectoMapper.toEntity(dto, usuario);
        proyecto.setFechaCreacion(LocalDateTime.now());

        proyectoDAO.insertProyecto(proyecto);
        logger.info("Proyecto {} insertado con éxito.", proyecto.getNombre());
    }

    /**
     * Actualiza un proyecto existente.
     */
    @Override
    public void update(ProyectoUpdateDTO dto) {
        if (proyectoDAO.existsProyectoByNombreAndNotId(dto.getNombre(), dto.getId())) {
            throw new DuplicateResourceException("proyecto", "nombre", dto.getNombre());
        }

        Proyecto proyecto = proyectoDAO.getProyectoById(dto.getId());
        if (proyecto == null) {
            throw new ResourceNotFoundException("proyecto", "id", dto.getId());
        }

        Usuario usuario = findUsuario(dto.getUsuarioId());
        ProyectoMapper.copyToExistingEntity(dto, proyecto, usuario);
        proyectoDAO.updateProyecto(proyecto);
        logger.info("Proyecto con ID {} actualizado con éxito.", proyecto.getId());
    }

    /**
     * Elimina un proyecto por su id.
     */
    @Override
    public void delete(Long id) {
        if (!proyectoDAO.existsProyectoById(id)) {
            throw new ResourceNotFoundException("proyecto", "id", id);
        }
        proyectoDAO.deleteProyecto(id);
    }

    /**
     * Busca el usuario elegido en el formulario. Lanza excepción si no existe.
     */
    private Usuario findUsuario(Long usuarioId) {
        Usuario usuario = usuarioDAO.getUsuarioById(usuarioId);
        if (usuario == null) {
            throw new ResourceNotFoundException("usuario", "id", usuarioId);
        }
        return usuario;
    }
}
