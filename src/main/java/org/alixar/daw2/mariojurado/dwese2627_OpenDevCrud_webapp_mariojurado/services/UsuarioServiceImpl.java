package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.services;

import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.daos.UsuarioDAO;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.UsuarioDTO;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.mappers.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de Usuario.
 *
 * @author Mario
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Devuelve todos los usuarios para el select del formulario de proyectos.
     */
    @Override
    public List<UsuarioDTO> listAll() {
        return UsuarioMapper.toDTOList(usuarioDAO.listAllUsuarios());
    }
}
