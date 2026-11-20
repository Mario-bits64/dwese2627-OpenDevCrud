package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.mappers;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.UsuarioDTO;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.entities.Usuario;

import java.util.List;

/**
 * Mapper entre la entidad Usuario y sus DTOs.
 *
 * @author Mario
 * @version 1.0
 */
public final class UsuarioMapper {

    private UsuarioMapper() {}

    /**
     * Convierte un Usuario en UsuarioDTO.
     */
    public static UsuarioDTO toDTO(Usuario e) {
        if (e == null) return null;
        return new UsuarioDTO(e.getId(), e.getEmail(), e.getRolSistema(), e.getActivo(),
                e.getDobleFactorActivo(), e.getFechaRegistro());
    }

    /**
     * Convierte una lista de Usuario en una lista de UsuarioDTO.
     */
    public static List<UsuarioDTO> toDTOList(List<Usuario> es) {
        return es == null ? List.of() : es.stream().map(UsuarioMapper::toDTO).toList();
    }
}
