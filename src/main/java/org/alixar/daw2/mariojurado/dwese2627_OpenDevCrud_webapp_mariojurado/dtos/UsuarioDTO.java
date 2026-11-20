package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String rolSistema;
    private Boolean activo;
    private Boolean dobleFactorActivo;
    private LocalDateTime fechaRegistro;
}
