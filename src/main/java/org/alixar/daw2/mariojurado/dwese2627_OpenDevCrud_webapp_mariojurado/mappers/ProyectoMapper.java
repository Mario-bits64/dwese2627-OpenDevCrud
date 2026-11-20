package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.mappers;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.*;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Proyecto;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Usuario;

import java.util.List;

/**
 * Mapper entre la entidad Proyecto y sus DTOs.
 *
 * @author Mario
 * @version 1.0
 */
public final class ProyectoMapper {

    private ProyectoMapper() {}

    /**
     * Convierte un Proyecto en ProyectoDTO para el listado.
     */
    public static ProyectoDTO toDTO(Proyecto e) {
        if (e == null) return null;

        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setFase(e.getFase());
        dto.setVisibilidad(e.getVisibilidad());
        dto.setFechaCreacion(e.getFechaCreacion());
        dto.setNumVisitas(e.getNumVisitas());
        if (e.getUsuario() != null) {
            dto.setUsuarioId(e.getUsuario().getId());
            dto.setUsuarioEmail(e.getUsuario().getEmail());
        }
        return dto;
    }

    /**
     * Convierte una lista de Proyecto en una lista de ProyectoDTO.
     */
    public static List<ProyectoDTO> toDTOList(List<Proyecto> es) {
        return es == null ? List.of() : es.stream().map(ProyectoMapper::toDTO).toList();
    }

    /**
     * Convierte un Proyecto en ProyectoDetailDTO con los datos de su usuario.
     */
    public static ProyectoDetailDTO toDetailDTO(Proyecto e) {
        if (e == null) return null;

        ProyectoDetailDTO dto = new ProyectoDetailDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setPerfilesNecesarios(e.getPerfilesNecesarios());
        dto.setFase(e.getFase());
        dto.setVisibilidad(e.getVisibilidad());
        dto.setRepositorioUrl(e.getRepositorioUrl());
        dto.setDemoUrl(e.getDemoUrl());
        dto.setFechaCreacion(e.getFechaCreacion());
        dto.setFechaFinalizacion(e.getFechaFinalizacion());
        dto.setNumVisitas(e.getNumVisitas());
        dto.setUsuario(UsuarioMapper.toDTO(e.getUsuario()));
        return dto;
    }

    /**
     * Convierte un Proyecto en ProyectoUpdateDTO para cargar el formulario de edición.
     */
    public static ProyectoUpdateDTO toUpdateDTO(Proyecto e) {
        if (e == null) return null;

        ProyectoUpdateDTO dto = new ProyectoUpdateDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setDescripcion(e.getDescripcion());
        dto.setPerfilesNecesarios(e.getPerfilesNecesarios());
        dto.setFase(e.getFase());
        dto.setVisibilidad(e.getVisibilidad());
        dto.setRepositorioUrl(e.getRepositorioUrl());
        dto.setDemoUrl(e.getDemoUrl());
        dto.setFechaFinalizacion(e.getFechaFinalizacion());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getId() : null);
        return dto;
    }

    /**
     * Crea un Proyecto nuevo a partir del ProyectoCreateDTO y su usuario.
     */
    public static Proyecto toEntity(ProyectoCreateDTO dto, Usuario usuario) {
        if (dto == null) return null;

        Proyecto e = new Proyecto();
        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setPerfilesNecesarios(emptyToNull(dto.getPerfilesNecesarios()));
        e.setFase(dto.getFase());
        e.setVisibilidad(dto.getVisibilidad());
        e.setRepositorioUrl(emptyToNull(dto.getRepositorioUrl()));
        e.setDemoUrl(emptyToNull(dto.getDemoUrl()));
        e.setFechaFinalizacion(dto.getFechaFinalizacion());
        e.setNumVisitas(0);
        e.setUsuario(usuario);
        return e;
    }

    /**
     * Copia los datos del ProyectoUpdateDTO en un Proyecto existente.
     */
    public static void copyToExistingEntity(ProyectoUpdateDTO dto, Proyecto e, Usuario usuario) {
        if (dto == null || e == null) return;

        e.setNombre(dto.getNombre());
        e.setDescripcion(dto.getDescripcion());
        e.setPerfilesNecesarios(emptyToNull(dto.getPerfilesNecesarios()));
        e.setFase(dto.getFase());
        e.setVisibilidad(dto.getVisibilidad());
        e.setRepositorioUrl(emptyToNull(dto.getRepositorioUrl()));
        e.setDemoUrl(emptyToNull(dto.getDemoUrl()));
        e.setFechaFinalizacion(dto.getFechaFinalizacion());
        e.setUsuario(usuario);
    }

    /**
     * Devuelve null si el texto viene vacío del formulario.
     */
    private static String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}
