package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String fase;
    private String visibilidad;
    private LocalDateTime fechaCreacion;
    private Integer numVisitas;
    private Long usuarioId;
    private String usuarioEmail;
}
